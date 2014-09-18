package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.service.UserHelper;
import org.joda.time.DateTime;

public class ServiceClass {
    public static void main(String[] args) {
        System.out.println(new DateTime().toString("HH:mm"));
        UserHelper hasher = new UserHelper();

        System.out.println(hasher.sha256("admin"));
    }



}
