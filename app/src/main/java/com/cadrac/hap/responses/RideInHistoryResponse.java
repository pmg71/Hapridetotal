package com.cadrac.hap.responses;

public class RideInHistoryResponse {

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

    RideInHistoryResponse.data[] data;

    public RideInHistoryResponse.data[] getData() {
        return data;
    }

    public void setData(RideInHistoryResponse.data[] data) {
        this.data = data;
    }

    public class data
    {
        String source,destination,passengerCount,rideStatus,startDate,endDate,date,time,vehicleNumber;

        //added by mahesh
        String addonpassengerCount;
        String addonFare;
        String totalPassCount;

        public String getTotalPassCount() {
            return totalPassCount;
        }

        public void setTotalPassCount(String totalPassCount) {
            this.totalPassCount = totalPassCount;
        }


        public String getAddonpassengerCount() {
            return addonpassengerCount;
        }

        public void setAddonpassengerCount(String addonpassengerCount) {
            this.addonpassengerCount = addonpassengerCount;
        }

        public String getAddonFare() {
            return addonFare;
        }

        public void setAddonFare(String addonFare) {
            this.addonFare = addonFare;
        }

        //closed by mahesh

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

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

        public String getRideStatus() {
            return rideStatus;
        }

        public void setRideStatus(String rideStatus) {
            this.rideStatus = rideStatus;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}