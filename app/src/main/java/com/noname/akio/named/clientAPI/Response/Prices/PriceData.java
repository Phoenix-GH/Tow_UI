package com.noname.akio.named.clientAPI.Response.Prices;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")

public class PriceData
{
    private ArrayList<Price> prices;
    /**
     *
     * @return
     * The prices
     */
    public ArrayList<Price> getPrices() {
        return prices;
    }

    /**
     *
     * @param prices
     * The prices
     */
    public void setPrices(ArrayList<Price> prices) {
        this.prices = prices;
    }
}