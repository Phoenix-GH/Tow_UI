package com.noname.akio.named.clientAPI.Response.Prices;

/**
 * Created by andy on 11/21/16.
 */
public class Price {
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

    private String name;
    /**
     *
     * @return
     * The name
     */
    public String getName()
    {
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

    private String amount;
    /**
     *
     * @return
     * The amount
     */
    public String getAmount()
    {
        return amount;
    }
    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }


}
