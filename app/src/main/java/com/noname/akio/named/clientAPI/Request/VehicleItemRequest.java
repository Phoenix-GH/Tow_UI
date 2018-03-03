package com.noname.akio.named.clientAPI.Request;

/**
 * Created by andy on 11/21/16.
 */
public class VehicleItemRequest {
    private String make;
    public String getMake()
    {
        return make;
    }
    public void setMake(String make)
    {
        this.make = make;
    }

    private String model;
    public String getModel()
    {
        return model;
    }
    public void setModel(String model)
    {
        this.model = model;
    }

    private String color;
    public String getColor()
    {
        return color;
    }
    public void setColor(String color)
    {
        this.color = color;
    }

    private String license;
    public String getLicense()
    {
        return license;
    }
    public void setLicense(String license)
    {
        this.license = license;
    }

    private String reasons;
    public String getReasons()
    {
        return reasons;
    }
    public void setReasons(String reasons)
    {
        this.reasons = reasons;
    }

    private String comments;
    public String getComments()
    {
        return comments;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
    }
}
