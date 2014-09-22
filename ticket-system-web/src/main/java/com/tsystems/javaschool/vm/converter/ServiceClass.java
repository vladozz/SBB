package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.helper.UserHelper;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

public class ServiceClass {
    public static void main(String[] args) {
        System.out.println(new DateTime().toString("HH:mm"));
        UserHelper hasher = new UserHelper();

        System.out.println(hasher.sha256("admin"));
        int minutes = Minutes.minutesBetween(new DateTime(), new DateTime().plusHours(1)).getMinutes();
        String routeTime = String.format("%d:%02d", minutes / 60, minutes % 60);
        System.out.println(routeTime);
    }


}
