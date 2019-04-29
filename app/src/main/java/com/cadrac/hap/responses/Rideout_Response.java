package com.cadrac.hap.responses;

public class Rideout_Response {


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

    data[] data;

    public Rideout_Response.data[] getData() {
        return data;
    }

    public void setData(Rideout_Response.data[] data) {
        this.data = data;
    }


    public class data
    {
        String first_name;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        String last_name;

        public String getEstimatedTime() {
            return EstimatedTime;
        }

        public void setEstimatedTime(String estimatedTime) {
            EstimatedTime = estimatedTime;
        }

        String EstimatedTime;
        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        String s_id;


        public String getA_id() {
            return a_id;
        }

        public void setA_id(String a_id) {
            this.a_id = a_id;
        }

        //satya
        String a_id;
        public String getLatest_go_id() {
            return latest_go_id;
        }

        public void setLatest_go_id(String latest_go_id) {
            this.latest_go_id = latest_go_id;
        }


        String latest_go_id;
        String driverName;

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        String vehicleNumber;
        String  gi_id;

        public String getMob_num() {
            return mob_num;
        }

        public void setMob_num(String mob_num) {
            mob_num = mob_num;
        }

        String mob_num;

        public String getGi_id() {
            return gi_id;
        }

        public void setGi_id(String gi_id) {
            this.gi_id = gi_id;
        }

        public String getD_id() {
            return d_id;
        }

        public void setD_id(String d_id) {
            this.d_id = d_id;
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

        String d_id;

        public String getR_id() {
            return r_id;
        }

        public void setR_id(String r_id) {
            this.r_id = r_id;
        }

        String r_id;
        String passengerCount;
        String totalFare;
        //satya

        String amount,vech_no,driver_no,no_of_passengers,from,to,id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getVechile_no() {
            return vech_no;
        }

        public void setVechile_no(String vechile_no) {
            this.vech_no = vech_no;
        }

        public String getDriver_no() {
            return driver_no;
        }

        public void setDriver_no(String driver_no) {
            this.driver_no = driver_no;
        }

        public String getNo_of_passengers() {
            return no_of_passengers;
        }

        public void setNo_of_passengers(String no_of_passengers) {
            this.no_of_passengers = no_of_passengers;
        }
    }
}
