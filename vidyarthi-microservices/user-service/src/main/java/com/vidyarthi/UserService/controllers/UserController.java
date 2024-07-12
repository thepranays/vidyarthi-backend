package com.vidyarthi.UserService.controllers;

import com.vidyarthi.UserService.dtos.UserRequest;
import com.vidyarthi.UserService.dtos.UserResponse;
import com.vidyarthi.UserService.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; //injected through required args constructor

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody final UserRequest createUserRequest ){
        userService.createUser(createUserRequest);
    }

    @GetMapping("/get/all")
    @ResponseStatus(HttpStatus.OK)
   // @ResponseBody //The controller is annotated with the @RestController annotation; therefore, the @ResponseBody isn't required.
    public List<UserResponse> getAllUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("/get/{uid}")
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody //The controller is annotated with the @RestController annotation; therefore, the @ResponseBody isn't required.
    public UserResponse getUserById(@PathVariable(value="uid") final String uid){

        return userService.getUserById(uid);
    }

    @PatchMapping("/update/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserById(@PathVariable(value="uid") final String uid,@RequestBody final UserRequest userRequest){
        userService.updateUserById(uid,userRequest);
    }

    @DeleteMapping("/delete/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable(value="uid") final String uid){
        userService.deleteUserById(uid);
    }
}
