package com.noname.akio.named.clientAPI.Response.TowImage;

import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")
public class TowImageResponse {
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

    private TowImageData data;
    /**
     *
     * @return
     * The data
     */
    public TowImageData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(TowImageData data) {
        this.data = data;
    }

}
