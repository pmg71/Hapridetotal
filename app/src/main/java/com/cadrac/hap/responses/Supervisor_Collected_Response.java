package com.cadrac.hap.responses;

public class Supervisor_Collected_Response {
    data[] data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status;
    }

    String status;

    public Supervisor_Collected_Response.data[] getData() {
        return data;
    }

    public void setData(Supervisor_Collected_Response.data[] data) {
        this.data = data;
    }

    public class data
    {
        String date;

        public String getGo_id() {
            return go_id;
        }

        String go_id;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTotalRides() {
            return totalRides;
        }

        public void setTotalRides(String totalRides) {
            this.totalRides = totalRides;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getDigital() {
            return digital;
        }

        public void setDigital(String digital) {
            this.digital = digital;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTotalPaid() {
            return totalPaid;
        }

        public void setTotalPaid(String totalPaid) {
            this.totalPaid = totalPaid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        String totalRides;
        String cash;
        String digital;
        String totalAmount;
        String totalPaid;
        String time;


    }
}