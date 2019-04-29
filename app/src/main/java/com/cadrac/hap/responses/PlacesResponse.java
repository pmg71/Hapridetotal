package com.cadrac.hap.responses;

public class PlacesResponse {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public  class  Data{

        String places;

        public String getPlaces() {
            return places;
        }

        public void setPlaces(String places) {
            this.places = places;
        }


    }
}
