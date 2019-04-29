package com.cadrac.hap.responses;

public class Ridein_Response {

    String to,from;
    String no_of_passengers;
    String vech_no;
    String driver_no;
    String driver_name;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNo_of_passengers() {
        return no_of_passengers;
    }

    public void setNo_of_passengers(String no_of_passengers) {
        this.no_of_passengers = no_of_passengers;
    }

    public String getVech_no() {
        return vech_no;
    }

    public void setVech_no(String vech_no) {
        this.vech_no = vech_no;
    }

    public String getDriver_no() {
        return driver_no;
    }

    public void setDriver_no(String driver_no) {
        this.driver_no = driver_no;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }
}
