package com.noname.akio.named.clientAPI.Request;

/**
 * Created by andy on 11/21/16.
 */
public class VehicleRequest {
    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    private String created_at;
    public String getCreated_at()
    {
        return created_at;
    }
    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }
}
