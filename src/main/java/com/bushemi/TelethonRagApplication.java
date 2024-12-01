package com.bushemi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TelethonRagApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelethonRagApplication.class, args);
        System.out.println("TelethonRagApplication.main");
    }

}
