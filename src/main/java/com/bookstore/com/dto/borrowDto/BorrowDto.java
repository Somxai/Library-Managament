package com.bookstore.com.dto.borrowDto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowDto implements Serializable {

    private Long borrow_id;


    private LocalDate date_borrow;

    private LocalDate date_return;

    private boolean overdue;

    private double fines;

    private String booksTitle;

    private String studentsName;

    @Override
    public String toString() {
        return "BorrowDto{" +
                "borrow_id=" + borrow_id +
                ", date_borrow=" + date_borrow +
                ", date_return=" + date_return +
                ", overdue=" + overdue +
                ", fines=" + fines +
                ", books=" + booksTitle +
                ", students=" + studentsName +
               '}';
    }



}
