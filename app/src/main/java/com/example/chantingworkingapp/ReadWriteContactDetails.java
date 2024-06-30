package com.example.chantingworkingapp;

import java.io.Serializable;

public class ReadWriteContactDetails implements Serializable{
     public String name,time,round,total_hearing_count,speed;
    public ReadWriteContactDetails(){};
    public ReadWriteContactDetails(  String  name , String time, String round,String total_hearing_count,String speed ){
        this.name = name;
        this.time = time;
        this.round = round;
        this.total_hearing_count = total_hearing_count;
        this.speed = speed;

    }

}
