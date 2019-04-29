package com.cadrac.hap.responses;


public class DriverHistoryResponse {

    String status;


    public String getStatus() {
        return status;
    }


    public DriverHistoryResponse.data[] getData() {
        return data;
    }

    data[] data;



    public  class data{




        String id;
        String gi_id;
        String go_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGi_id() {
            return gi_id;
        }

        public void setGi_id(String gi_id) {
            this.gi_id = gi_id;
        }

        public String getGo_id() {
            return go_id;
        }

        public void setGo_id(String go_id) {
            this.go_id = go_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getPassengerCount() {
            return passengerCount;
        }

        public void setPassengerCount(String passengerCount) {
            this.passengerCount = passengerCount;
        }

        public String getTotalFare() {
            return totalFare;
        }

        public void setTotalFare(String totalFare) {
            this.totalFare = totalFare;
        }

        String source;
        String destination;
        String passengerCount;
        String totalFare;
    }
}
