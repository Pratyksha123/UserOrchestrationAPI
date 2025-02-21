package com.example.userorchestrationapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesDTO {
    private double lat;
    private double lng;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
