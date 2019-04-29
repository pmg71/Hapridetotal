package com.cadrac.hap.responses;

public class RideInCostDetailsResponse {

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    Data[] data;



    public class Data {
        String cabcost;
        String autocost;
        String id;

        public String getEstimatedTime() {
            return estimatedTime;
        }

        public void setEstimatedTime(String estimatedTime) {
            this.estimatedTime = estimatedTime;
        }

        String estimatedTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getCabcost() {
            return cabcost;
        }

        public void setCabcost(String cabcost) {
            this.cabcost = cabcost;
        }

        public String getAutocost() {
            return autocost;
        }

        public void setAutocost(String autocost) {
            this.autocost = autocost;
        }

    }
    }
