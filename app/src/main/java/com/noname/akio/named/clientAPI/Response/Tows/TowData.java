package com.noname.akio.named.clientAPI.Response.Tows;

import java.util.ArrayList;

import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")
public class TowData
{
    public ArrayList<Tow> tow;
    /**
     *
     * @return
     * The tow
     */
    public ArrayList<Tow> getTow()
    {
        return tow;
    }
    /**
     *
     * @param tow
     * The tow
     */
    public void setData(ArrayList<Tow> tow) {
        this.tow = tow;
    }
}