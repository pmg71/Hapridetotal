package com.cadrac.hap.responses;

public class DigCashResponse {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getGi_id() {
        return gi_id;
    }

    public void setGi_id(String gi_id) {
        this.gi_id = gi_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    String gi_id;
    String s_id;
}
