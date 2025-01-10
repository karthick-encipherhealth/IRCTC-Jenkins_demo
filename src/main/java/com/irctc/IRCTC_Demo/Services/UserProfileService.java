package com.irctc.IRCTC_Demo.Services;

import com.irctc.IRCTC_Demo.DTO.Request.UserResetPasswordDto;
import com.irctc.IRCTC_Demo.DTO.Response.GeneralResponse;
import com.irctc.IRCTC_Demo.Models.UserProfile;
import com.irctc.IRCTC_Demo.Repository.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserProfileService {
    @Autowired
    UserProfileRepo userProfileRepo;

    public GeneralResponse signUp(UserProfile up){
        UserProfile exist = userProfileRepo.getByUserName(up.getUserName());
        if(exist == null ){
            UserProfile us = new UserProfile();
            us.setUserName(up.getUserName());
            us.setFirstName(up.getFirstName());
            us.setLastName(up.getLastName());
            us.setRole(up.getRole());
            us.setMobileNo(up.getMobileNo());
            us.setEmailId(up.getEmailId());
            us.setGender(up.getGender());
            us.setDob(up.getDob());
            us.setPassword(up.getPassword());
            us.setCreatedAt(new Date().getTime());
            us.setUpdatedAt(new Date().getTime());
            userProfileRepo.save(us);
            return new GeneralResponse( "SUCCESS", "SignUp process is Completed", us);
        }
        return new GeneralResponse( "FAILED", "User Name is Already exist!", null);
    }

    public GeneralResponse signIn(UserProfile userProfile){
        UserProfile user = userProfileRepo.getByUserName(userProfile.getUserName());
        if(user != null){
            if (user.getPassword().equals(userProfile.getPassword())){
                return new GeneralResponse( "SUCCESS", "SignIn Process Verified Successfully", user);
            }
            else {
                return new GeneralResponse( "FAILED", "Incorrect Password", null);
            }
        }
        else {
            return new GeneralResponse( "FAILED", "UserName is not valid", null);
        }
    }

    public UserProfile getByUserName(String userName ){
        return userProfileRepo.getByUserName(userName);
    }

    public GeneralResponse updateUser(UserProfile us){
        UserProfile userProfile = userProfileRepo.getByUserName(us.getUserName());
        if (userProfile != null) {
            if (us.getEmailId() != null) {
                userProfile.setEmailId(us.getEmailId());
            }
            if (us.getMobileNo() != null){
                userProfile.setMobileNo(us.getMobileNo());
            }
            userProfile.setUpdatedAt(new Date().getTime());
            userProfileRepo.save(userProfile);
            return new GeneralResponse( "SUCCESS", "Update process is done successfully!", null);
        }
        return new GeneralResponse( "FAILED", "UserName is not valid", null);
    }

    public List<UserProfile> getAllUsers(){
        return userProfileRepo.findAll();
    }
    public GeneralResponse deleteUser(String id){
        UserProfile exist = userProfileRepo.getByUserId(id);
        if (exist != null && exist.getUserId().equals(id)) {
            userProfileRepo.deleteByUserId(id);
            return new GeneralResponse( "Success", "Id number "+id+" is successfully deleted", null);
        }
        return new GeneralResponse( "FAILED", "Id number "+id+" is not valid userId!. Give proper userId", null);
    }

    public GeneralResponse resetPassword(UserResetPasswordDto resetPassword){
        UserProfile user = userProfileRepo.getByUserName(resetPassword.userName);
        if (user != null){
            if (user.getPassword().equals(resetPassword.oldPassword)){
                user.setPassword(resetPassword.newPassword);
                user.setUpdatedAt(new Date().getTime());
                userProfileRepo.save(user);
                return new GeneralResponse( "SUCCESS", "Password Reseted successfully!", user);
            }
            else {
                return new GeneralResponse( "FAILED", "Invalid Password", null);
            }
        }
        else {
            return new GeneralResponse( "FAILED", "Uesr name not Available!", null);
        }
    }
}
