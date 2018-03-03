package com.noname.akio.named.clientAPI.Response.Vehicles;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")

public class Vehicles {
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
    private VehicleItemData data;

    /**
     *
     * @return
     * The data
     */
    public VehicleItemData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(VehicleItemData data) {
        this.data = data;
    }

}
