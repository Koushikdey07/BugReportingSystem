package com.major.project.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassGen {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("ad11234"));
        System.out.println(encoder.encode("tl11234"));
        System.out.println(encoder.encode("ad21234"));
        System.out.println(encoder.encode("tl21234"));
        System.out.println(encoder.encode("12345678"));
    }

}
