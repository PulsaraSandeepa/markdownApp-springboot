package com.markdown.auth.controllers;


import com.markdown.auth.dtos.UserInfoDTO;
import com.markdown.auth.dtos.UserLoginDTO;
import com.markdown.auth.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    //create user
    @PostMapping("/create")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO) {
        checkNotNull(userInfoDTO);

        System.out.println(userInfoDTO.getDisplayName());
        System.out.println(userInfoDTO.getRoles());

        // TODO : add service to handle logic
        userService.createUser(userInfoDTO);

        return userInfoDTO;

    }

    @GetMapping("/info/{userID}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserInfoDTO getUSerInfo(@PathVariable String userID) {

        System.out.println("Get info of user" + userID);

        // TODO : add service to handle logic
        return userService.retrieveUserInfo(userID);
    }

    //login user
    @PostMapping("/login")
    public UserInfoDTO loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        checkNotNull(userLoginDTO);

        System.out.println(userLoginDTO.getUsername());
        System.out.println(userLoginDTO.getPassword());


        // TODO : add service to handle logic
        return userService.loginUser(userLoginDTO);

    }


//delete a user

    //modify a user


}
