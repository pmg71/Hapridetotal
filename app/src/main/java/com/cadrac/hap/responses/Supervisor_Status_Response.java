package com.cadrac.hap.responses;

public class Supervisor_Status_Response {

    String message,status,go_otp;

    public String getGo_otp() {
        return go_otp;
    }

    public void setGo_otp(String go_otp) {
        this.go_otp = go_otp;
    }

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

    public Supervisor_Status_Response.data[] getData() {
        return data;
    }

    public void setData(Supervisor_Status_Response.data[] data) {
        this.data = data;
    }

    public class data
    {

        String agent_id;
        String agent_name;
        String agent_number;
        String total_rides;
        String total_amount;
        String Area;
        String digital;
        String cash;
        String go_otp;
        String paidDate;
        String s_id;
        String set_id;
        String pending;
        String settlementStatus;

        public String getSettlementStatus() {
            return settlementStatus;
        }

        public void setSettlementStatus(String settlementStatus) {
            this.settlementStatus = settlementStatus;
        }

        public String getPending() {
            return pending;
        }

        public void setPending(String pending) {
            this.pending = pending;
        }

        String totalPaid;
        String totalUnpaid;
        String mobileNumber;

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getSet_id() {
            return set_id;
        }

        public void setSet_id(String set_id) {
            this.set_id = set_id;
        }

        public String getTotalPaid() {
            return totalPaid;
        }

        public void setTotalPaid(String totalPaid) {
            this.totalPaid = totalPaid;
        }

        public String getTotalUnpaid() {
            return totalUnpaid;
        }

        public void setTotalUnpaid(String totalUnpaid) {
            this.totalUnpaid = totalUnpaid;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getPaidDate() {
            return paidDate;
        }

        public void setPaidDate(String paidDate) {
            this.paidDate = paidDate;
        }

        public String getGo_otp() {
            return go_otp;
        }

        public void setGo_otp(String go_otp) {
            this.go_otp = go_otp;
        }

        String s_otp;


        public String getS_otp() {
            return s_otp;
        }

        public void setS_otp(String s_otp) {
            this.s_otp = s_otp;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public String getAgent_number() {
            return agent_number;
        }

        public void setAgent_number(String agent_number) {
            this.agent_number = agent_number;
        }

        public String getTotal_rides() {
            return total_rides;
        }

        public void setTotal_rides(String total_rides) {
            this.total_rides = total_rides;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }

        public String getDigital() {
            return digital;
        }

        public void setDigital(String digital) {
            this.digital = digital;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

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



        public String getR_id() {
            return r_id;
        }

        public void setR_id(String r_id) {
            this.r_id = r_id;
        }

        String vehicleNumber;
        String  gi_id;
        String r_id;
        String passengerCount;
        String totalFare;
        String d_id;
        String latest_go_id;
        String driverName;

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
