package com.noname.akio.named.clientAPI.Response.Storages;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")


public class Storages {
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

    private StorageData data;

    /**
     *
     * @return
     * The data
     */
    public StorageData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(StorageData data) {
        this.data = data;
    }
}