package com.dmk.practice.exercise_everyday.repositories;

import com.dmk.practice.exercise_everyday.dto.BookingRequest;
import com.dmk.practice.exercise_everyday.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("""
    SELECT b FROM Booking b 
    WHERE LOWER(b.guestName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(CAST(b.bookingDate AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    ORDER BY id DESC  """)
    List<BookingRequest> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
    SELECT b FROM Booking b 
    WHERE LOWER(b.guestName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(CAST(b.bookingDate AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    ORDER BY id DESC  """)
    Page<BookingRequest> filterFun(@Param("keyword") String keyword, Pageable pageable);

    Page<BookingRequest> findAllByOrderByBookingDateDesc(Pageable pageable);
}
