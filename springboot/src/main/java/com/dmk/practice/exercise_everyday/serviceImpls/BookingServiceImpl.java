package com.dmk.practice.exercise_everyday.serviceImpls;

import com.dmk.practice.exercise_everyday.dto.BookingRequest;
import com.dmk.practice.exercise_everyday.dto.BookingResponse;
import com.dmk.practice.exercise_everyday.models.Booking;
import com.dmk.practice.exercise_everyday.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createData(Booking booking){
        return bookingRepository.save(booking);
    }

    public BookingResponse filter(String val){
        Pageable pageable = PageRequest.of(0,5);
        List<BookingRequest> filterData = bookingRepository.search(val,pageable);
        if(!filterData.isEmpty()){
            BookingResponse br = new BookingResponse();
            br.setData(filterData);
            return br;
        }
        throw new RuntimeException("Search Not Found");
    }
}
