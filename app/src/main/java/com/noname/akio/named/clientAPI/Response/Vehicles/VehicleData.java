package com.noname.akio.named.clientAPI.Response.Vehicles;

/**
 * Created by andy on 11/21/16.
 */
import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")


public class VehicleData
{
    private ArrayList<VehicleList> vehicle_lists;
    /**
     *
     * @return
     * The vehicle_lists
     */
    public ArrayList<VehicleList> getVehicle_lists() {
        return vehicle_lists;
    }

    /**
     *
     * @param vehicle_lists
     * The vehicle_lists
     */
    public void setVehicle_lists(ArrayList<VehicleList> vehicle_lists) {
        this.vehicle_lists = vehicle_lists;
    }
}