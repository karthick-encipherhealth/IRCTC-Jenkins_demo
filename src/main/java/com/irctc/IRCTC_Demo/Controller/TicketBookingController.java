package com.irctc.IRCTC_Demo.Controller;

import com.irctc.IRCTC_Demo.DTO.Request.Destination;
import com.irctc.IRCTC_Demo.Models.TicketBooking;
import com.irctc.IRCTC_Demo.Services.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ticketBooking")
public class TicketBookingController {
    @Autowired
    TicketBookingService ticketBookingService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/createBooking")
    public ResponseEntity bookingProcess(@RequestBody TicketBooking request){
        if(request.getStatus() == null || request.getSeatNumbers() == null || request.getTravelDate() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("status or seatNumbers or travelDate Should not be null");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketBookingService.bookingProcess(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllTicketsBooked")
    public ResponseEntity getAllTicketsBooked(){
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.getAllBookedTickets());
    }

    @CrossOrigin(origins = "/*")
    @GetMapping(value = "/findTheTrainForYourDestination")
    public ResponseEntity findTheTrainForYourDestination(@RequestBody Destination request){
        if (request.from == null || request.to == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("from and to should not be null!");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketBookingService.findTheRoute(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getByBookingNumber")
    public ResponseEntity getByBookingId(@RequestParam Long request){
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.getBookingTickets(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAvailableTickets")
    public ResponseEntity getAvailableTickets(@RequestParam Long request){
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.getAvailableSeats(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getRoutes")
    public ResponseEntity getRoute(@RequestParam Long request){
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.getRoutes(request));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/ticketConfirmationProcess")
    public ResponseEntity ticketConfirmationProcess(@RequestBody TicketBooking request){
        if (request.getStatus() == null || request.getBookingNumber() == null || request.getUserName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userName or bookingNumber or status should not be null!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketBookingService.ticketConfirmationProcess(request));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/changeCompartmentType")
    public ResponseEntity changeCompartmentType(@RequestBody TicketBooking request){
        if (request.getUserName() == null || request.getBookingNumber() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userName or bookingNumber or status should not be null!");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketBookingService.changeCompartmentType(request));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/deleteBookedTickets")
    public ResponseEntity deleteBookedTickets(@RequestBody TicketBooking request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketBookingService.deleteBookedTickets(request));
    }
}
