package com.dmk.practice.exercise_everyday.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class BookingRequest {

    private final String guestName;
    private final LocalDate bookingDate;
}
