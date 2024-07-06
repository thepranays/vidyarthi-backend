package com.vidyarthi.UserService.repos;

import com.vidyarthi.UserService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,String> { //type of row and its id type

}
