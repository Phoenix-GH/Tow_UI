package com.noname.akio.named.clientAPI.Response.Vehicles;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")

public class VehicleLists {
    private boolean success;
    /**
     *
     * @return
     * The success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    private VehicleData data;

    /**
     *
     * @return
     * The data
     */
    public VehicleData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(VehicleData data) {
        this.data = data;
    }

}
