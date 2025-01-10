package com.irctc.IRCTC_Demo.Services;

import com.irctc.IRCTC_Demo.DTO.Request.TrainRouteModifyDto;
import com.irctc.IRCTC_Demo.DTO.Response.GeneralResponse;
import com.irctc.IRCTC_Demo.Models.Train;
import com.irctc.IRCTC_Demo.Repository.TrainRepository;
import com.irctc.IRCTC_Demo.Repository.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class TrainService {
    @Autowired
    TrainRepository trainRepository;

    @Autowired
    UserProfileRepo userProfileRepo;


    public GeneralResponse addTrain(Train train){
        Train exist = trainRepository.getByTrainNumber(train.getTrainNumber());
        Train tr = new Train();
        if (exist == null){
            tr.setTrainName(train.getTrainName());
            tr.setRoute(train.getRoute());
            tr.setTrainNumber(train.getTrainNumber());
            tr.setTiming(train.getTiming());
            tr.setDate(train.getDate());
            tr.setCreatedAt(new Date().getTime());
            tr.setUpdatedAt(new Date().getTime());
            tr.setTotalSeats(train.getTotalSeats());
            tr.setSeatsAvailable(true);
            trainRepository.save(tr);
            return new GeneralResponse("SUCCESS",tr.getTrainNumber()+" is added Successfully!", exist);
        }
        return new GeneralResponse("ERROR",exist.getTrainName()+" is already exist!", exist);
    }

    public List<Train> getAllTrain(){
        return trainRepository.findAll();
    }

    public GeneralResponse getByTrainNumber(Long trainNumber) {
        Train exist = trainRepository.getByTrainNumber(trainNumber);
        if (exist != null && exist.getTrainNumber().equals(trainNumber)){
            return new GeneralResponse("Success","Train is found", exist);
        }
        else {
            return new GeneralResponse("Error","trainNumber "+trainNumber+" is not found", null);
        }
    }

    public GeneralResponse updateTrain(Train tr){
        Train tn = trainRepository.getByTrainNumber(tr.getTrainNumber());
        if (tn != null){
            if (tr.getTiming() != null) {
                tn.setTiming(tr.getTiming());
            }
            if (tr.getDate() != null){
                tn.setDate(tr.getDate());
            }
            if (tr.getRoute() != null){
                tn.setRoute(tr.getRoute());
            }
            if (tr.getTotalSeats() != null){
                tn.setTotalSeats(tr.getTotalSeats());
            }
            tn.setUpdatedAt(new Date().getTime());
            trainRepository.save(tn);
            return new GeneralResponse("SUCCESS","Train Updated Successfully", tn);
        }
        return new GeneralResponse("ERROR","Train Number is not found or valid", null);
    }

    public GeneralResponse updateRoute(TrainRouteModifyDto trainRouteModifyDto){
        Train tr = trainRepository.getByTrainNumber(trainRouteModifyDto.trainNumber);
        if (tr != null){
            LinkedList<String> linkedList = tr.getRoute();
            if(trainRouteModifyDto.index >=0 && trainRouteModifyDto.index < linkedList.size()){
                linkedList.set(trainRouteModifyDto.index, trainRouteModifyDto.newStation);
                tr.setRoute(linkedList);
                tr.setUpdatedAt(new Date().getTime());
                trainRepository.save(tr);
                return new GeneralResponse("SUCCESS","Train's particular route is updated successfully", tr);
            }
            else {
                return new GeneralResponse("ERROR","Train route Index is Not valid or out of bound", null);
            }
        }
        return new GeneralResponse("FAILED","Train Number is Not valid", null);
    }

    public GeneralResponse addNewRoute(TrainRouteModifyDto trainRouteModifyDto){
        Train train = trainRepository.getByTrainNumber(trainRouteModifyDto.trainNumber);
        if (train != null){
            LinkedList<String> linkedList = train.getRoute();
            linkedList.add(trainRouteModifyDto.index, trainRouteModifyDto.newStation);
            train.setRoute(linkedList);
            train.setUpdatedAt(new Date().getTime());
            trainRepository.save(train);
            return new GeneralResponse("SUCCESS","New route is added successfully!", train);
        }
        return new GeneralResponse("ERROR","Train number is not valid!", null);
    }

    public GeneralResponse deleteRoute(TrainRouteModifyDto trainRouteModifyDto){
        Train train = trainRepository.getByTrainNumber(trainRouteModifyDto.trainNumber);
        if (train != null){
            LinkedList<String> linkedList = train.getRoute();
            System.out.println(linkedList);
            if(trainRouteModifyDto.index >=0 && trainRouteModifyDto.index < linkedList.size()){
                linkedList.remove(trainRouteModifyDto.index);
                System.out.println(linkedList);
                train.setRoute(linkedList);
                train.setUpdatedAt(new Date().getTime());
                trainRepository.save(train);
                return new GeneralResponse("SUCCESS","Train's particular route is Deleted Successfully", train);
            }
            else {
                return new GeneralResponse("ERROR","Train route Index is Not valid or out of bound", train);
            }
        }
        return new GeneralResponse("ERROR","Train Number is Not valid", train);
    }

    public GeneralResponse deleteTrain(String trainId){
        Train train = trainRepository.getByTrainId(trainId);
        if ( train != null && train.getTrainId().equals(trainId)) {
            trainRepository.deleteByTrainId(trainId);
            return new GeneralResponse("SUCCESS","The Train Deleted Successfully! The Deleted Train is "+train.getTrainName(), train);
        }
        return new GeneralResponse("ERROR","Give correct trainId. "+trainId+" is not a valid Id", train);
    }
}
