package com.cadrac.hap.responses;
public class request_response {



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;


    public data[] getData() {
        return data;
    }

    data[] data;

    public request_response.data1[] getData1() {
        return data1;
    }

    data1[] data1;

    public  class data
    {


        public String getPassengerNumber() {
            return passengerNumber;
        }

        public void setPassengerNumber(String passengerNumber) {
            this.passengerNumber = passengerNumber;
        }

        public String getSeats() {
            return seats;
        }

        public void setSeats(String seats) {
            this.seats = seats;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String destination) {
            Destination = destination;
        }

        public String getPassengerName() {
            return passengerName;
        }

        public void setPassengerName(String passengerName) {
            this.passengerName = passengerName;
        }

        String passengerName;
        String passengerNumber;
        String seats;
        String Destination;

        public String getId() {
            return id;
        }

        String id;

        public String getVeh_type() {
            return veh_type;
        }

        String veh_type;

        public String getFare() {
            return fare;
        }

        String fare;



    }
    public class data1{
        public String getCount() {
            return count;
        }

        String count;
    }

}
