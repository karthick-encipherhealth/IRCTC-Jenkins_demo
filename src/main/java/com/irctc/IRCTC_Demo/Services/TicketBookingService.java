package com.irctc.IRCTC_Demo.Services;
import com.irctc.IRCTC_Demo.DTO.Request.Destination;
import com.irctc.IRCTC_Demo.DTO.Response.GeneralResponse;
import com.irctc.IRCTC_Demo.Models.TicketBooking;
import com.irctc.IRCTC_Demo.Models.Train;
import com.irctc.IRCTC_Demo.Models.UserProfile;
import com.irctc.IRCTC_Demo.Repository.TicketBookingRepository;
import com.irctc.IRCTC_Demo.Repository.TrainRepository;
import com.irctc.IRCTC_Demo.Repository.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketBookingService {

    public AtomicLong counter = new AtomicLong(1);

    @Autowired
    TicketBookingRepository ticketBookingRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    UserProfileRepo userProfileRepo;

    //req: userName, trainNumber, travelDate, passengerDetails, seatNumbers, compartmentType, status
    public GeneralResponse bookingProcess(TicketBooking tb){
        TicketBooking ticket = new TicketBooking();
        UserProfile user = userProfileRepo.getByUserName(tb.getUserName());
        Train train = trainRepository.getByTrainNumber(tb.getTrainNumber());

        if (user == null){
            return new GeneralResponse("FAILED", "userName is not matches",null);
        }

        if (train == null){
            return new GeneralResponse("FAILED", "trainNumber is not matches",null);
        }

        if (!train.getSeatsAvailable()){
            return new GeneralResponse("FAILED", "Ticket is not available", null);
        }

        if (!train.getDate().equals(tb.getTravelDate())){
            return new GeneralResponse("FAILED", "For this date "+tb.getTravelDate()+" train is not available",null);
        }

        Random random = new Random();
        Long bookNo = counter.getAndIncrement() + random.nextLong(1000);

        ticket.setBookingNumber(bookNo);
        ticket.setUserId(user.getUserId());
        ticket.setUserName(tb.getUserName());
        ticket.setTrainId(train.getTrainId());
        ticket.setTrainNumber(tb.getTrainNumber());
        ticket.setTravelDate(tb.getTravelDate());
        ticket.setPassengerDetails(tb.getPassengerDetails());
        ticket.setSeatNumbers(tb.getSeatNumbers());

        double farePerPassenger = 600.00;
        double totalFee = farePerPassenger * tb.getPassengerDetails().size();
        ticket.setTotalFare(totalFee);

        int seats = train.getTotalSeats() - tb.getPassengerDetails().size();
        if (tb.getPassengerDetails().size() > train.getTotalSeats()) {
            train.setSeatsAvailable(false);
            train.setUpdatedAt(new Date().getTime());
            trainRepository.save(train);
            return new GeneralResponse( "FAILED", "Seats are not available", null);
        }
        train.setSeatsAvailable(true);
        ticket.setCompartmentType(tb.getCompartmentType());
        ticket.setStatus(tb.getStatus());
        train.setTotalSeats(seats);
        trainRepository.save(train);

        ticket.setCreatedAt(new Date().getTime());
        ticket.setUpdatedAt(new Date().getTime());
        ticketBookingRepository.save(ticket);
        return new GeneralResponse("SUCCESS", "Train Booked successfully!",ticket);
    }

    public List<TicketBooking> getAllBookedTickets(){
        return ticketBookingRepository.findAll();
    }

    // req bookingNumber
    public GeneralResponse getBookingTickets(Long bookingNumber){
        TicketBooking exist = ticketBookingRepository.getByBookingNumber(bookingNumber);
        if (exist != null){
            return new GeneralResponse("SUCCESS","Found",ticketBookingRepository.getByBookingNumber(bookingNumber));
        }
        return new GeneralResponse("FAILED","Not found",null);
    }

    //req: bookingNumber, trainNumber
    public GeneralResponse deleteBookedTickets(Long bookingNumber, Long trainNumber){
        TicketBooking ticketBooking = ticketBookingRepository.getByBookingNumber(bookingNumber);
        Train train = trainRepository.getByTrainNumber(trainNumber);
        if (ticketBooking != null){
            if (ticketBooking.getTrainNumber() != null){
                if (ticketBooking.getBookingId() != null) {
                    int seats = train.getTotalSeats() + ticketBooking.getPassengerDetails().size();
                    train.setTotalSeats(seats);
                    train.setUpdatedAt(new Date().getTime());
                    trainRepository.save(train);
                    ticketBookingRepository.deleteByBookingNumber(bookingNumber);
                    return new GeneralResponse("SUCCESS", "bookingNumber "+bookingNumber+" is deleted Successfully Train name is "+train.getTrainName(), null);
                }
                else {
                    return new GeneralResponse("FAILED", "bookingNumber "+bookingNumber+" is not valid", null);
                }
            }
            return new GeneralResponse("FAILED", "trainNumber "+trainNumber+" is not valid", null);
        }
        return new GeneralResponse("FAILED", "bookingNumber "+bookingNumber+" is not found", null);
    }

    //req: bookingNumber, trainNumber
    public GeneralResponse deleteBookedTickets(TicketBooking tb){
        TicketBooking ticketBooking = ticketBookingRepository.getByBookingNumber(tb.getBookingNumber());
        Train train = trainRepository.getByTrainNumber(tb.getTrainNumber());
        if ( ticketBooking != null){
            if (ticketBooking.getBookingId() != null){
                if (ticketBooking.getTrainNumber() != null) {
                    int seats = train.getTotalSeats() + ticketBooking.getPassengerDetails().size();
                    train.setTotalSeats(seats);
                    train.setUpdatedAt(new Date().getTime());
                    trainRepository.save(train);
                    ticketBookingRepository.deleteByBookingNumber(tb.getBookingNumber());
                    return new GeneralResponse("SUCCESS", "bookingNumber "+tb.getBookingNumber()+" is deleted Successfully. Train name is "+train.getTrainName(), null);
                }
                else {
                    return new GeneralResponse("FAILED", "bookingNumber "+tb.getBookingNumber()+" is not valid", null);
                }
            }
            return new GeneralResponse("FAILED", "trainNumber "+tb.getTrainNumber()+" is not valid", null);
        }
        return new GeneralResponse("FAILED", "bookingNumber "+tb.getBookingNumber()+" is not found", null);
    }

    //need to check api  req: train
    public GeneralResponse getAvailableSeats(Long trainNumber){
        Train tn = trainRepository.getByTrainNumber(trainNumber);
        TicketBooking tb = ticketBookingRepository.getByTrainNumber(trainNumber);
        if (tn == null){
            return new GeneralResponse("FAILED", "trainNumber :"+trainNumber+" is not Valid!", null);
        }
        if (tb.getPassengerDetails().size() <= tn.getTotalSeats()){
            tn.setSeatsAvailable(true);
            tn.setUpdatedAt(new Date().getTime());
            trainRepository.save(tn);
            ticketBookingRepository.save(tb);
            return new GeneralResponse("SUCCESS", "Seats are available!. Available tickets are "+tn.getTotalSeats(), null);
        }

        tn.setSeatsAvailable(false);
        tn.setUpdatedAt(new Date().getTime());
        trainRepository.save(tn);
        ticketBookingRepository.save(tb);
        return new GeneralResponse("FAILED", "Seats are not available!. Available tickets are "+tn.getTotalSeats(),null);

    }

    //userName, bookingNumber, status
    public GeneralResponse ticketConfirmationProcess(TicketBooking tb){
        TicketBooking user  = ticketBookingRepository.getByBookingNumber(tb.getBookingNumber());
        if (user != null ){
            if (user.getUserName().equals(tb.getUserName())) {
                if (user.getStatus().toString().equals("PENDING")) {
                    if (tb.getStatus().toString().equals("CANCEL")) {
                        return deleteBookedTickets(user.getBookingNumber(), user.getTrainNumber());
                    }
                    user.setStatus(tb.getStatus());
                    user.setUpdatedAt(new Date().getTime());
                    ticketBookingRepository.save(user);
                    return new GeneralResponse("SUCCESS", "Ticket Confirmation process is done successfully!", user);
                }
                return new GeneralResponse("FAILED","status is already Confirmed. This process is only allowed for status which is PENDING", user);
            }
            return new GeneralResponse("FAILED","UserName is not valid and this process only allowed to the user who's status is"+user.getStatus(), null);
        }
        return new GeneralResponse("FAILED","BookigNumber is not valid",null);
    }

    //req: trainNumber
    public GeneralResponse getRoutes(Long trainNumber){
        Train train = trainRepository.getByTrainNumber(trainNumber);
        if (train != null){
            LinkedList<String> route = train.getRoute();
            return new GeneralResponse("SUCCESS", "", route);
        }
        return new GeneralResponse( "ERROR", "",null);
    }

    // compartment, username, bookingNumber,
    public GeneralResponse changeCompartmentType(TicketBooking tb){
        TicketBooking ticketBooking = ticketBookingRepository.getByBookingNumber(tb.getBookingNumber());
        if (ticketBooking != null){
            if (ticketBooking.getBookingNumber() != null && ticketBooking.getUserName() != null) {
                if (ticketBooking.getStatus().toString().equals("PENDING")){
                    if ( !( ticketBooking.getCompartmentType().toString().equals(tb.getCompartmentType().toString() ) ) ){
                        Double extraForChanging = ticketBooking.getTotalFare()+ 500;
                        ticketBooking.setTotalFare(extraForChanging);
                        ticketBooking.setCompartmentType(tb.getCompartmentType());
                        ticketBooking.setUpdatedAt(new Date().getTime());
                        ticketBookingRepository.save(ticketBooking);
                        return new GeneralResponse("SUCCESS", "Updated successfully!", ticketBooking);
                    }
                    return new GeneralResponse( "ERROR", "Compartment type should not be same",null);
                }
                return new GeneralResponse( "ERROR", "Username is not valid or You already Confirmed the booking status. Its only allowed for Tickets which status is Pending",null);
            }
        }
        return new GeneralResponse( "ERROR", "bookingNumber or userName is not valid",null);
    }

    //from(source) , to(Destination)
    public GeneralResponse findTheRoute(Destination destination){
        List<Train> train = trainRepository.findAll();
        if (!train.isEmpty()) {
            for (Train node : train) {
                List<String> route = node.getRoute();
                if (route != null) {
                    int from = route.indexOf(destination.from);
                    int to = route.indexOf(destination.to);
                    if (from != -1 && to != -1 && from < to) {
                        return new GeneralResponse("SUCCESS", node.getTrainName() + " is available for your search!", route);
                    }
                }
                else
                    return new GeneralResponse("ERROR", "No Train is available for your search!", null);
            }
            return new GeneralResponse("ERROR", "Route is not available", null);
        }
        return new GeneralResponse("FAILED", "Train details is empty!", null);
    }
}
