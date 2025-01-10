package com.irctc.IRCTC_Demo.Repository;

import com.irctc.IRCTC_Demo.Models.TicketBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketBookingRepository extends MongoRepository<TicketBooking, String> {
    TicketBooking getByBookingNumber(Long bookingNumber);
    void deleteByBookingNumber(Long bookingNumber);
    TicketBooking getByTrainNumber(Long trainNumber);

}
