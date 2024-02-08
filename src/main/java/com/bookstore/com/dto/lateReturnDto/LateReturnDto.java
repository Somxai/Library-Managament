package com.bookstore.com.dto.lateReturnDto;


import com.bookstore.com.dto.bookDto.BookDto;
import com.bookstore.com.dto.borrowDto.BorrowDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LateReturnDto {


    private Long lateReturn_id;

    private Date dueDate;

    private Double feeCharge;

    private BorrowDto borrowReturn;

    private BookDto bookLateReturn;

    @Override
    public String toString() {
        return "LateReturnDto{" +
                "lateReturn_id=" + lateReturn_id +
                ", dueDate=" + dueDate +
                ", feeCharge=" + feeCharge +
                ", borrowReturn=" + borrowReturn +
                ", bookLateReturn=" + bookLateReturn +
                '}';
    }
}
