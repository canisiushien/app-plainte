package com.sawdev.cnss.plainte;

import com.sawdev.cnss.plainte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CnssPlainteApiApplication implements CommandLineRunner {

    @Autowired
    private UserService userservice;

    public static void main(String[] args) {
        SpringApplication.run(CnssPlainteApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userservice.initUsers();
    }

}
