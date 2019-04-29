package com.cadrac.hap.responses;

public class SeatsResponse {
    public Data[] getData() {
        return data;
    }

    Data[] data;


    public String getStatus() {
        return status;
    }


    String status;




    public class Data{

        public String getSeats() {
            return seats;
        }

        String seats;
    }
}
