package com.irctc.IRCTC_Demo.Controller;

import com.irctc.IRCTC_Demo.DTO.Request.UserResetPasswordDto;
import com.irctc.IRCTC_Demo.Models.UserProfile;
import com.irctc.IRCTC_Demo.Services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/user")

public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signUp")
    public ResponseEntity signUp(@RequestBody UserProfile request) {
        if(request.getUserName() == null || request.getPassword() == null || request.getMobileNo() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName, password, mobile number should not be null");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.signUp(request));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signIn")
    public ResponseEntity signIn(@RequestBody UserProfile request){
        if(request.getUserName() == null || request.getPassword() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile number or Password shoud not be null");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userProfileService.signIn(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getByUserName")
    public ResponseEntity getByUserName(@RequestParam String userName){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userProfileService.getByUserName(userName));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllUsers")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getAllUsers());
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/updateUser")
    public ResponseEntity updateUserProfile(@RequestBody UserProfile request){
        if(request.getUserName() == null || request.getMobileNo() == null || request.getEmailId() == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All 3 fields must be filled");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userProfileService.updateUser(request));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/deleteUser/{id}")
    public  ResponseEntity deleteByUserId(@PathVariable String id){
        if(id == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Id should not be null");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userProfileService.deleteUser(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/resetPassword")
    public ResponseEntity resetPassword(@RequestBody UserResetPasswordDto request){
        if(request.newPassword == null || request.userName == null ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userName or newPassword shpuld not be null!");
        }
        return ResponseEntity.status(HttpStatus.IM_USED).body(userProfileService.resetPassword(request));
    }
}
