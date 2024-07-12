package com.vidyarthi.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableAutoConfiguration //enables Spring Boot to auto-configure the application context. Therefore, it automatically creates and registers beans based on both the included jar files in the classpath and the beans defined by us
@SpringBootApplication
@EnableDiscoveryClient
//Eureka version 4.0.0 onwards, which is being used in Spring Cloud 2022.0.0, you do not need to explicitly register using the annotation @EnableEurekaClient It automatically gets registered as client if spring-cloud-starter-netflix-eureka-client is on the class path. As per the - documentation
public class UserServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceApplication.class, args);

		/*
CREATE TABLE `vidyarthi_user_db`.`user` (`uid` VARCHAR(64) NOT NULL , `username` VARCHAR(64) NOT NULL , `first_name` VARCHAR(64) NOT NULL , `last_name` INT(64) NOT NULL , `branch` VARCHAR(64) NOT NULL , `email` VARCHAR(64) NOT NULL , `study_year` INT(1) NOT NULL , `profile_img_url` VARCHAR(64) NULL , `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) , `last_seen` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) , PRIMARY KEY (`uid`)) ENGINE = InnoDB;
	*/

		/*
		INSERT INTO `user`(`uid`, `username`, `first_name`, `last_name`, `branch`, `email`, `study_year`) VALUES ('test1','test1','test','subject','ece','@gmail.com','3')
		 */
	}


}
