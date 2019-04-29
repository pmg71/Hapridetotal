package com.cadrac.hap.responses;

public class RideInDriverDetailsResponse {

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    Data[] data;


    public class Data {


        String veh_type;
        String driver_id;

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getVeh_type() {
            return veh_type;
        }

        public void setVeh_type(String veh_type) {
            this.veh_type = veh_type;
        }

        public String getVeh_num() {
            return veh_num;
        }

        public void setVeh_num(String veh_num) {
            this.veh_num = veh_num;
        }

        public String getContact_num() {
            return contact_num;
        }

        public void setContact_num(String contact_num) {
            this.contact_num = contact_num;
        }

        public String getNo_of_pass() {
            return no_of_pass;
        }

        public void setNo_of_pass(String no_of_pass) {
            this.no_of_pass = no_of_pass;
        }

        String veh_num;
        String contact_num;
        String no_of_pass;

    }
}
