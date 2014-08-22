package com.tsystems.javaschool.vm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DefTripDTO implements Serializable {
    String arriveStation;
    String departureStation;
    Timestamp departureTime;
    Timestamp arriveTime;
}
