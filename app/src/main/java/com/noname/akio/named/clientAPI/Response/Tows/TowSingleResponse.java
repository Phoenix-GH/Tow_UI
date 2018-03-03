package com.noname.akio.named.clientAPI.Response.Tows;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")


public class TowSingleResponse {
    private boolean success;
    /**
     *
     * @return
     * The success
     */
    public boolean getSuccess()
    {
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

    private TowSingleData data;
    /**
     *
     * @return
     * The data
     */
    public TowSingleData getData()
    {
        return data;
    }
    /**
     *
     * @param data
     * The data
     */
    public void setData(TowSingleData data) {
        this.data = data;
    }



}
