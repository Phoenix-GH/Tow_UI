package com.noname.akio.named.clientAPI.Response.Vehicles;

/**
 * Created by andy on 11/20/16.
 */
public class Vehicle {
    private int id;
    /**
     *
     * @return
     * The id
     */
    public int getId()
    {
        return id;
    }
    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }
    private int list_id;
    /**
     *
     * @return
     * The contract_id
     */
    public int getList_id()
    {
        return list_id;
    }
    /**
     *
     * @param list_id
     * The list_id
     */
    public void setList_id(int list_id) {
        this.list_id = list_id;
    }
    private String make;

    /**
     *
     * @return
     * The make
     */
    public String getMake()
    {
        return make;
    }
    /**
     *
     * @param make
     * The make
     */
    public void setMake(String make) {
        this.make = make;
    }

    private String color;

    /**
     *
     * @return
     * The color
     */
    public String getColor()
    {
        return color;
    }
    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    private String license;

    /**
     *
     * @return
     * The license
     */
    public String getLicense()
    {
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

    private String reasons;

    /**
     *
     * @return
     * The reasons
     */
    public String getReasons()
    {
        return reasons;
    }
    /**
     *
     * @param reasons
     * The reasons
     */
    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    private String model;

    /**
     *
     * @return
     * The model
     */
    public String getModel()
    {
        return model;
    }
    /**
     *
     * @param model
     * The model
     */
    public void setModel(String model) {
        this.model = model;
    }

    private String comments;

    /**
     *
     * @return
     * The comments
     */
    public String getComments()
    {
        return comments;
    }
    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
