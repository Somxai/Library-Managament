package com.bookstore.com.services.borrowService;

import com.bookstore.com.configPackage.ModelMappers;
import com.bookstore.com.dao.BorrowRepository;
import com.bookstore.com.domain.book.Book;
import com.bookstore.com.domain.borrow.Borrow;
import com.bookstore.com.domain.student.Student;
import com.bookstore.com.dto.borrowDto.BorrowDto;
import com.bookstore.com.exception.borrowException.BorrowAlreadyExistException;
import com.bookstore.com.exception.borrowException.NoSuchBorrowExistException;
import com.bookstore.com.services.bookService.BookService;
import com.bookstore.com.services.studentService.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowService {

    private final ModelMappers modelMappers;

    private static final Logger logger = LoggerFactory.getLogger(BorrowService.class);

    private final BorrowRepository borrowRepository;

    private final BookService bookService;

    private final StudentService studentService;


    @Autowired
    public BorrowService(ModelMappers modelMappers, BorrowRepository borrowRepository, BookService bookService,
                          StudentService studentService) {
        this.modelMappers = modelMappers;
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.studentService = studentService;
    }

    @Transactional
//    @Cacheable(value = "borrows" )
    public List<BorrowDto> fetchAll(int pageNo, int pageSize, String sort ){

        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
            Page<Borrow> borrowPage = borrowRepository.findAll(pageable);
            if (borrowPage.getContent().isEmpty()){
                throw new NoSuchBorrowExistException("borrowPage is empty");
            }

            List<BorrowDto> borrowDtos = borrowPage.stream().filter(br -> br.getBooks().getTitle() != null
            && (br.getStudents().getFirstName() != null || br.getStudents().getLastName() != null)).map(bdt ->
                    new BorrowDto(bdt.getBorrow_id(), bdt.getDate_borrow(), bdt.getDate_return(), bdt.isOverdue(),
                            bdt.getFines(),bdt.getBooks().getTitle(),bdt.getStudents().getFirstName() + " " + bdt.getStudents().getLastName())).collect(Collectors.toList());

            borrowDtos.forEach(System.out::println);
            logger.info("completed loop");
            return borrowDtos;

        }catch (NoSuchBorrowExistException e){
            throw new NoSuchBorrowExistException("no book borrowed.");
        }
    }
    @Transactional
//    @Cacheable(value = "borrows" )
    public List<Borrow> fetchAllNormal(int pageNo, int pageSize, String sort ){

        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
            Page<Borrow> borrowPage = borrowRepository.findAll(pageable);
            List<Borrow> borrow1 = new ArrayList<>();
            borrowPage.stream().map(borrow1::add);
            return borrow1;
        }catch (NoSuchBorrowExistException e){
            throw new NoSuchBorrowExistException("no book borrowed.");
        }
    }
    @Transactional()
//   @CachePut(value = "borrows", key = "#borrowDto")
    public Borrow borrowing(BorrowDto borrowDto){
        try {
            if (borrowDto == null){
                throw new NoSuchBorrowExistException("borrow is null");
            }

            Borrow borrow = new Borrow();
            borrow = modelMappers.DtoToBorrow(borrowDto);
            if (borrow == null){
                throw new NoSuchBorrowExistException("borrow mapping error");

            }
            LocalDate dateBorrow = LocalDate.now();
            borrow.setDate_borrow(dateBorrow);
            LocalDate dateReturn = LocalDate.now().plusDays(7);
            borrow.setDate_return(dateReturn);
            if (borrowDto.getBooksTitle() != null && borrowDto.getStudentsName() != null){
                Book book = bookService.fetchByTitleNormal(borrowDto.getBooksTitle());
                logger.info("book: " + book);
                book.addBorrow(borrow);

                Student student = studentService.fetchByNameNormal(borrowDto.getStudentsName());
                logger.info("student: " + student);
                student.addBorrow(borrow);

            }

            return borrowRepository.save(borrow);

        }catch (BorrowAlreadyExistException e){
            throw new BorrowAlreadyExistException("borrow already exist.");
        }
    }

    @Transactional
    public boolean isOverDate(Borrow borrow){
        LocalDate currentDate = LocalDate.now();
        LocalDate lateDate = borrow.getDate_return();
        int compareDate = currentDate.compareTo(lateDate);
        if (compareDate < 0){
            return true;
        } else if (compareDate > 0) {
            return false;
        }else {
            return false;
        }
    }

    @Transactional
    public List<Borrow> checkOverDue(){
        List<Borrow> borrows = borrowRepository.findAll().stream().filter(b -> !isOverDate(b)).toList();;
        logger.info("list after filtered overdue:");
        borrows.forEach(b -> b.setOverdue(true));
        borrows.forEach(borrowRepository::save);
        borrows.forEach(System.out::println);
        return borrows;
    }
    @Transactional
    public double chargingOverdue(){
        List<Borrow> borrows = checkOverDue();
        borrows.forEach(System.out::println);
        LocalDate toDay = LocalDate.now();
        final double CHARGE_PER_DAY = 1.5;
        List<Borrow> chargedUpdated = borrows.stream().peek(b -> {
            if (toDay.isAfter(b.getDate_return())){
            long daysLate = ChronoUnit.DAYS.between(b.getDate_return(),toDay);

                double charging = daysLate * CHARGE_PER_DAY;
                b.setFines(charging);
            }else {
                b.setFines(0);
            }
        }).toList();

        chargedUpdated.forEach(borrowRepository::save);
        return chargedUpdated.stream().mapToDouble(Borrow::getFines).sum();

    }




    @Transactional
//    @Cacheable(value = "borrow", key = "#id")
    public BorrowDto fetchById(Long id) {

        try {
            logger.info("fetching borrow from id...");
            Optional<Borrow> borrowOptional = borrowRepository.fetchById(id);

            Borrow borrow = borrowOptional.orElseThrow(() ->
                    new NoSuchBorrowExistException(" borrow is not available in database."));

            logger.info("fetching borrow" + borrow);
            BorrowDto borrowDto = new BorrowDto();
            if (borrow != null){
                borrowDto.setBorrow_id(borrow.getBorrow_id());
                borrowDto.setDate_borrow(borrow.getDate_borrow());
                borrowDto.setDate_return(borrow.getDate_return());
                borrowDto.setOverdue(borrow.isOverdue());
                borrowDto.setFines(borrow.getFines());
                borrowDto.setBooksTitle(borrow.getBooks().getTitle());
                borrowDto.setStudentsName(borrow.getStudents().getFirstName());

            }

            logger.info("borrow DTO after mapped: " + borrowDto);
            return borrowDto;

        } catch (NoSuchBorrowExistException ex) {
            logger.error("no borrow exist");
            throw new NoSuchBorrowExistException("something went wrong");
        }
    }

        @Transactional
//        @Cacheable(value = "borrow", key = "#id")
        public Borrow fetchByID(Long id){

            try {
                logger.info("fetching borrow from id...");
                Optional<Borrow> borrowOptional = borrowRepository.fetchById(id);

                Borrow borrow = borrowOptional.orElseThrow(()->
                        new NoSuchBorrowExistException( " borrow is not available in database."));
                logger.info("fetching borrow" +borrow);

                return borrow;

            }catch (NoSuchBorrowExistException ex){
                logger.error("no borrow exist");
                throw new NoSuchBorrowExistException("something went wrong");
            }

    }

    @Transactional
    public boolean removeBorrow(BorrowDto borrowDto){

        try {
            if (borrowDto == null){
                throw new NoSuchBorrowExistException("the borrow Obj is null.");
            }
            Optional<Borrow> borrowToDelete = borrowRepository.fetchById(borrowDto.getBorrow_id());
            if (borrowToDelete.isPresent()){
                logger.info("performing remove borrow" + borrowDto.getBorrow_id() + "...");
                Borrow borrow = borrowToDelete.get();
                logger.info("perform clear borrow data from book...");
                borrow.getBooks().removeBorrow(borrow);
                logger.info("perform clear borrow data from student...");
                borrow.getStudents().removeBorrow(borrow);

                borrowRepository.delete(borrow);
                logger.info("remove completed.");
                return true;

            }else {

                return false;
            }

        }catch (NoSuchBorrowExistException ex){
            throw new NoSuchBorrowExistException("there is no borrow to delete in database.");
        }

    }



}









