package com.vidyarthi.UserService.services;

import com.vidyarthi.UserService.dtos.UserRequest;
import com.vidyarthi.UserService.dtos.UserResponse;
import com.vidyarthi.UserService.models.User;
import com.vidyarthi.UserService.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public void createUser(UserRequest userRequest){

        User user=User.builder()
                .username(userRequest.getUsername())
                .branch(userRequest.getBranch())
                .email(userRequest.getEmail())
                .first_name(userRequest.getFirst_name())
                .last_name(userRequest.getLast_name())
                .study_year(userRequest.getStudy_year())
                .profile_img_url(userRequest.getProfile_img_url())
                .last_seen(Timestamp.from(Instant.now()))
                .created_at(Timestamp.from(Instant.now()))
                .uid(userRequest.getUid())
                .build();

            this.userRepository.save(user);
            log.info("Created user with email:{} successfully..",userRequest.getEmail());



    }
    public void deleteUserById(String uid){

        userRepository.deleteById(uid);
    }
    public void updateUserById(String uid,UserRequest userRequest){
        Optional<User> user=userRepository.findById(uid);
        if(user.isPresent()){
            User updatedUser=user.get();
            updatedUser.setUsername(userRequest.getUsername());
            updatedUser.setFirst_name(userRequest.getFirst_name());
            updatedUser.setLast_name(userRequest.getLast_name());
            updatedUser.setBranch(userRequest.getBranch());
            updatedUser.setEmail(userRequest.getEmail());
            updatedUser.setStudy_year(userRequest.getStudy_year());
            updatedUser.setProfile_img_url(userRequest.getProfile_img_url());
            updatedUser.setLast_seen(Timestamp.from(Instant.now()));
            updatedUser.setUid(userRequest.getUid());
            userRepository.save(updatedUser);
            log.info("Updated user with email:{} successfully..",userRequest.getEmail());

        }

    }
    public UserResponse getUserById(String uid){
        Optional<User> user=userRepository.findById(uid);
        return user.map(this::mapUserToUserResponse).orElse(null);
        //Similar to:
//        if(user.isPresent()){
//            return mapUserToUserResponse(user.get());
//        }
//        return null;
    }

    public List<UserResponse> getAllUsers(){
        List<User> users= userRepository.findAll();

        return users.stream().map((e)->mapUserToUserResponse(e)).toList();

    }

    public UserResponse mapUserToUserResponse(User user){
       return  UserResponse.builder()
                .username(user.getUsername())
                .branch(user.getBranch())
                .email(user.getEmail())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .study_year(user.getStudy_year())
                .profile_img_url(user.getProfile_img_url())
                .last_seen(user.getLast_seen())
                .created_at(user.getCreated_at())
                .uid(user.getUid())
                .build();

    }
}
