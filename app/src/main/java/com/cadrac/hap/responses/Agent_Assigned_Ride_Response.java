package com.cadrac.hap.responses;

import com.cadrac.hap.Adapters.Agent_AssignedRides_Adapter;

public class Agent_Assigned_Ride_Response {

    String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Agent_Assigned_Ride_Response.data[] data;

    public Agent_Assigned_Ride_Response.data[] getData() {
        return data;
    }

    public void setData(Agent_Assigned_Ride_Response.data[] data) {
        this.data = data;
    }

    public class data {

        String Ride_id;
        String Passenger_Name;
        String Pass_Number;

        public String getPassenger_Name() {
            return Passenger_Name;
        }

        public void setPassenger_Name(String passenger_Name) {
            Passenger_Name = passenger_Name;
        }

        public String getPass_Number() {
            return Pass_Number;
        }

        public void setPass_Number(String pass_Number) {
            Pass_Number = pass_Number;
        }



        public String getRide_id() {
            return Ride_id;
        }

        public void setRide_id(String ride_id) {
            Ride_id = ride_id;
        }






    }
}
