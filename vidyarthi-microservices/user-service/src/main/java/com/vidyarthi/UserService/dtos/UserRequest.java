package com.vidyarthi.UserService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String uid;
    private String username;
    private String first_name;
    private String last_name;
    private String branch;
    private String email;
    private String study_year;
    private String profile_img_url;

}
