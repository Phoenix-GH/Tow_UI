package com.noname.akio.named.clientAPI.Response.Prices;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")


public class Prices {
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

    private PriceData data;

    /**
     *
     * @return
     * The data
     */
    public PriceData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(PriceData data) {
        this.data = data;
    }
}