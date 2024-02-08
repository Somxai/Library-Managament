package com.bookstore.com.controller.borrowController;

import com.bookstore.com.domain.borrow.Borrow;
import com.bookstore.com.dto.borrowDto.BorrowDto;
import com.bookstore.com.services.borrowService.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "borrow")
public class borrowController {


    private final BorrowService borrowService;

    @Autowired
    public borrowController(BorrowService borrowService)
    {
        this.borrowService = borrowService;
    }


    @GetMapping()
    public ResponseEntity<List<Borrow>> checkCharge(){
        return ResponseEntity.ok(borrowService.checkOverDue());
    }

    @GetMapping(path = "charging")
    public ResponseEntity<Double> charged(){

     return ResponseEntity.ok(borrowService.chargingOverdue());
    }


    @GetMapping( path = "/fetchAll")
    public ResponseEntity<List<BorrowDto>> fetchAll(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                                          @RequestParam(value = "sort",defaultValue = "fines") String sort){

            return ResponseEntity.ok(borrowService.fetchAll(pageNo, pageSize, sort));
        }


    @GetMapping(path = "/fetchById/{id}")
    public ResponseEntity<BorrowDto> fetchById(@PathVariable Long id){
        BorrowDto borrowDto = borrowService.fetchById(id);
        return ResponseEntity.ok(borrowDto);
    }


    @DeleteMapping(path = "removeBorrow")
    public ResponseEntity<Boolean> removeAuthor(@RequestBody BorrowDto borrowDto){

            boolean remove = borrowService.removeBorrow(borrowDto);

            return ResponseEntity.ok(remove);

    }

    @PostMapping(path = "createBorrow")
    public ResponseEntity<Borrow> createBorrow(@RequestBody BorrowDto borrowDto){

        return ResponseEntity.ok(borrowService.borrowing(borrowDto));
    }


}






