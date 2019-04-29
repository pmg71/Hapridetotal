package com.cadrac.hap.responses;

public class Drivers_Availabilty_Response {


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    Drivers_Availabilty_Response.data[] data;

    public Drivers_Availabilty_Response.data[] getData() {
        return data;
    }

    public void setData(Drivers_Availabilty_Response.data[] data) {
        this.data = data;
    }

    public class data
    {

        String driver_name;
        String vech_no;
        String driver_no;

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        String vehicleType;

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        String destination;





        public String getVechile_no() {
            return vech_no;
        }


        public String getDriver_no() {
            return driver_no;
        }

        public void setDriver_no(String driver_no) {
            this.driver_no = driver_no;
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
    }

}

