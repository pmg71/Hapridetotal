package com.cadrac.hap.responses;
public class SettlementResponse {
    String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    SettlementResponse.data[] data;

    public SettlementResponse.data[] getData() {
        return data;
    }

    public void setData(SettlementResponse.data[] data) {
        this.data = data;
    }

    public class data
    {

        String AgentId;
        String TotalRides;
        String TotalPaid;
        String TotalUnpaid;
        String TotalAmount;
        String Status;
        String Date;

        public String getPaidDate() {
            return paidDate;
        }

        public void setPaidDate(String paidDate) {
            this.paidDate = paidDate;
        }

        String paidDate;

        public String getSet_id() {
            return set_id;
        }

        public void setSet_id(String set_id) {
            this.set_id = set_id;
        }

        String set_id;

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }



        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            TotalAmount = totalAmount;
        }


        public String getAgentId() {
            return AgentId;
        }

        public void setAgentId(String agentId) {
            AgentId = agentId;
        }

        public String getTotalRides() {
            return TotalRides;
        }

        public void setTotalRides(String totalRides) {
            TotalRides = totalRides;
        }

        public String getTotalPaid() {
            return TotalPaid;
        }

        public void setTotalPaid(String totalPaid) {
            TotalPaid = totalPaid;
        }

        public String getTotalUnpaid() {
            return TotalUnpaid;
        }

        public void setTotalUnpaid(String totalUnpaid) {
            TotalUnpaid = totalUnpaid;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }
    }

}
