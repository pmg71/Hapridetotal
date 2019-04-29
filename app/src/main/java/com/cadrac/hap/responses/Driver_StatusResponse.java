package com.cadrac.hap.responses;

public class Driver_StatusResponse {

    String driver_status;// get driver status
    String status;//to gewt the connection status
    String ride_id;//driver ride id


    public String getDriver_status() {
        return driver_status;
    }

    public void setDriver_status(String driver_status) {
        this.driver_status = driver_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRide_id() {
        return ride_id;
    }

    public void setRide_id(String ride_id) {
        this.ride_id = ride_id;
    }
}