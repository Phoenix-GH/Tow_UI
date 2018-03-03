package com.noname.akio.named.clientAPI.Response.Contracts;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")


public class Contracts {
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

    private ContractData data;

    /**
     *
     * @return
     * The contracts
     */
    public ContractData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ContractData data) {
        this.data = data;
    }
}