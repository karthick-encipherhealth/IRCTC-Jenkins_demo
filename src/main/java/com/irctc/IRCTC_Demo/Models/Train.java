package com.irctc.IRCTC_Demo.Models;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Document(collection = "train_details")
public class Train {
    @Id
    private String trainId;

    private String trainName;
    @Column(unique = true)
    private Long trainNumber;
    private String date;
    private Integer totalSeats;
    private Boolean isSeatsAvailable;
    private LinkedList<String> route;
    private String timing;
    private Long createdAt;
    private Long updatedAt;

    public Train(){

    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getSeatsAvailable() {
        return isSeatsAvailable;
    }

    public void setSeatsAvailable(Boolean seatsAvailable) {
        isSeatsAvailable = seatsAvailable;
    }

    public Long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Long trainNumber) {
        this.trainNumber = trainNumber;
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

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public LinkedList<String> getRoute() {
        return route;
    }

    public void setRoute(LinkedList<String> route) {
        this.route = route;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
