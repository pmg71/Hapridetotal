package com.cadrac.hap.responses;

public class Rideout_HistoryResponse {

    String message,status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Rideout_HistoryResponse.data[] data;

    public Rideout_HistoryResponse.data[] getData() {
        return data;
    }

    public void setData(Rideout_HistoryResponse.data[] data) {
        this.data = data;
    }

    public class data
    {
        String source,destination,agent_id,Driver_id,driver_name,vech_no,passengerCount,date,time,vehicleNumber,totalFare,edate,etime;

        public String getEdate() {
            return edate;
        }

        public void setEdate(String edate) {
            this.edate = edate;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getSource() {
            return source;
        }



        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public String getTotalFare() {
            return totalFare;
        }

        public void setTotalFare(String totalFare) {
            this.totalFare = totalFare;
        }

        public String getPassengerCount() {
            return passengerCount;
        }

        public void setPassengerCount(String passengerCount) {
            this.passengerCount = passengerCount;
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

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getDriver_id() {
            return Driver_id;
        }

        public void setDriver_id(String driver_id) {
            Driver_id = driver_id;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getVech_no() {
            return vech_no;
        }

        public void setVech_no(String vech_no) {
            this.vech_no = vech_no;
        }

      /*  public String getPassengers_count() {
            return passengers_count;
        }

        public void setPassengers_count(String passengers_count) {
            this.passengers_count = passengers_count;
        }*/

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
