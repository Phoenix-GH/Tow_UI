package com.noname.akio.named.clientAPI.Response;

import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")
public class Truck {
    private int id;
    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setid(int id) {
        this.id = id;
    }
    private String name;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String license;

    /**
     *
     * @return
     * The license
     */
    public String getLicense() {
        return license;
    }

    /**
     *
     * @param license
     * The license
     */
    public void setLicense(String license) {
        this.license = license;
    }

    private String driver_id;

    /**
     *
     * @return
     * The driver_id
     */
    public String getDriver_id() {
        return driver_id;
    }

    /**
     *
     * @param driver_id
     * The driver_id
     */
    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }
}
