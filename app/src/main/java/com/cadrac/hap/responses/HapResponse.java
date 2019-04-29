package com.cadrac.hap.responses;

public class HapResponse {
    public String getStatus() {
        return status;
    }

    String status;

    public Data[] getData() {
        return data;
    }

    Data[] data;
    public class Data{
        public String getVeh_names() {
            return Veh_names;
        }

        String Veh_names;

        public String getCount() {
            return count;
        }

        String count;

    }
}
