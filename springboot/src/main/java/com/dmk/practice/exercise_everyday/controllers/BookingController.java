package com.dmk.practice.exercise_everyday.controllers;

import com.dmk.practice.exercise_everyday.dto.BookingRequest;
import com.dmk.practice.exercise_everyday.dto.MessageResponse;
import com.dmk.practice.exercise_everyday.models.Booking;
import com.dmk.practice.exercise_everyday.repositories.BookingRepository;
import com.dmk.practice.exercise_everyday.serviceImpls.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking){
        return ResponseEntity.ok(bookingService.createData(booking));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterData(@RequestParam("value") String value){
        return ResponseEntity.ok(bookingService.filter(value));
    }

    @GetMapping("/todo")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page,size);
        Page<BookingRequest> allByOrderByBookingDateDesc = bookingRepository.findAllByOrderByBookingDateDesc(pageable);
        if(allByOrderByBookingDateDesc.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("Data is empty."));
        }
        return ResponseEntity.ok(allByOrderByBookingDateDesc);

    }

    @GetMapping("/todo1")
    public ResponseEntity<?> getByFilter(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size)
    {

        Pageable pageable = PageRequest.of(page,size);
        Page<BookingRequest> bookingRequests = bookingRepository.filterFun(keyword, pageable);
        if(bookingRequests.isEmpty()){
            return ResponseEntity.ok(new MessageResponse("Data is empty."));
        }
        return ResponseEntity.ok(bookingRequests);

    }


}
