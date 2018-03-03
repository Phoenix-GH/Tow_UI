package com.noname.akio.named.clientAPI.Response.Tows;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")


public class Tows {
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

    private TowData data;
    /**
     *
     * @return
     * The data
     */
    public TowData getData()
    {
        return data;
    }
    /**
     *
     * @param data
     * The data
     */
    public void setData(TowData data) {
        this.data = data;
    }
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
