package com.irctc.IRCTC_Demo.Controller;

import com.irctc.IRCTC_Demo.DTO.Request.TrainRouteModifyDto;
import com.irctc.IRCTC_Demo.Models.Train;
import com.irctc.IRCTC_Demo.Services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/train")
public class TrainController {

    @Autowired
    TrainService trainService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/addTrainDetails")
    public ResponseEntity addTrainDetails(@RequestBody Train request){
        if (request.getTrainName() == null || request.getTiming() == null || request.getTrainNumber() == null || request.getTotalSeats() == null|| request.getRoute() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("trainName or totalSeats or timing or route or trainNumber should not be null");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(trainService.addTrain(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllTrainDetails")
    public ResponseEntity getAllTrainDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(trainService.getAllTrain());
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getByTrainNumber")
    public ResponseEntity getByTrainNumber(@RequestParam Long trainNumber){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainService.getByTrainNumber(trainNumber));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/updateTrain")
    public ResponseEntity updateTrain(@RequestBody Train request){
        if (request.getTiming() == null || request.getRoute() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Route or Timing should not be null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(trainService.updateTrain(request));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/updateRoute")
    public ResponseEntity updateRoute(@RequestBody TrainRouteModifyDto request){
        if (request.index == -1 || request.trainNumber == null || request.newStation == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index or trainNumber or newStation should not be null");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainService.updateRoute(request));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/addingNewRoute")
    public ResponseEntity addNewRoute(@RequestBody TrainRouteModifyDto request){
        if (request.index == -1 || request.trainNumber == null || request.newStation == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index or trainNumber or newStation should not be null");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainService.addNewRoute(request));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/deleteRoute")
    public ResponseEntity deleteRoute(@RequestBody TrainRouteModifyDto request){
        if (request.trainNumber == null || request.index == -1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("index or trainNumber should not be null");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainService.deleteRoute(request));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/deleteTrain/{trainId}")
    public ResponseEntity deleteTrain(@PathVariable String trainId){
        if (trainId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Train number should not be null");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(trainService.deleteTrain(trainId));
    }
}
