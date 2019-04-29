package com.cadrac.hap.responses;

public class Pass_Driver_Availability_List {
    public String getStatus() {
        return status;
    }

    String status;

    public Pass_Driver_Availability_List.data[] getData() {
        return data;
    }

    data[] data;

public class data{
    public String getDriver_name() {
        return driver_name;
    }

    String driver_name;

    }
}
