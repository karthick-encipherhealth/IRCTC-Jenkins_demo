package com.irctc.IRCTC_Demo.Repository;


import com.irctc.IRCTC_Demo.Models.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepo extends MongoRepository<UserProfile, Integer> {
    UserProfile getByUserName(String userName);
    UserProfile getByUserId(String userId);
    void deleteByUserId(String userId);
}
