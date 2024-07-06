package com.vidyarthi.UserService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String uid;
    private String username;
    private String first_name;
    private String last_name;
    private String branch;
    private String email;
    private String study_year;
    private String profile_img_url;
    private Timestamp created_at;
    private Timestamp last_seen;

}
