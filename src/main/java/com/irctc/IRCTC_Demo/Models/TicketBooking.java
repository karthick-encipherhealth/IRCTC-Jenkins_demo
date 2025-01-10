package com.irctc.IRCTC_Demo.Models;

import com.irctc.IRCTC_Demo.DTO.Request.Passenger;
import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.Random;

@Document(collection = "booking_details")
public class TicketBooking {
    @Id
    private String bookingId;

    @Column(unique = true)
    private Long bookingNumber;
    private String userId;
    private String userName;
    private String trainId;
    private Long trainNumber;
    private String travelDate;
    private CompartmentType compartmentType;
    private LinkedList<Passenger> passengerDetails;
    private LinkedList<String> seatNumbers;
    private Double totalFare;
    private TicketBookingStatus status;

    private Long createdAt;
    private Long updatedAt;

    public TicketBooking() {
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Long bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public CompartmentType getCompartmentType() {
        return compartmentType;
    }

    public void setCompartmentType(CompartmentType compartmentType) {
        this.compartmentType = compartmentType;
    }

    public LinkedList<Passenger> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(LinkedList<Passenger> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

    public LinkedList<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(LinkedList<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public TicketBookingStatus getStatus() {
        return status;
    }

    public void setStatus(TicketBookingStatus status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
