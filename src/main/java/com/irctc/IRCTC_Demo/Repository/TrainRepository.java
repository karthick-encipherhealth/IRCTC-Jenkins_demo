package com.irctc.IRCTC_Demo.Repository;

import com.irctc.IRCTC_Demo.Models.Train;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainRepository extends MongoRepository<Train, Integer> {
    void deleteByTrainId(String trainId);
    Train getByTrainId(String trainId);
    Train getByTrainNumber(Long trainNumber);
}
