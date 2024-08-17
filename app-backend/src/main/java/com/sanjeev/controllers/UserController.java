package com.sanjeev.controllers;


import com.sanjeev.dto.DtoUser;
import com.sanjeev.services.UserServiceImpl;
import com.sanjeev.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl appUserService;

    public UserController(UserServiceImpl appUserService) {
        this.appUserService = appUserService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<Object> signup( @Valid @RequestBody DtoUser body){
//
////        appUserService.create(body);
//        return AppUtils.createResponse(body
//                , HttpStatus.CREATED);
//
//    };

//    @PostMapping("/signup")
//    public ResponseEntity<String> signup( @Valid @RequestBody DtoUser body){
//        System.out.println(body);
//        appUserService.create(body);
//        return AppUtils.createResponse("User successfully registered", HttpStatus.CREATED);
//
//    };


}
