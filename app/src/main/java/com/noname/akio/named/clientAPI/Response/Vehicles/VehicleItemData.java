package com.noname.akio.named.clientAPI.Response.Vehicles;

/**
 * Created by andy on 11/21/16.
 */
import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")


public class VehicleItemData
{
    private ArrayList<Vehicle> vehicles;
    /**
     *
     * @return
     * The vehicles
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     *
     * @param vehicles
     * The vehicles
     */
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}