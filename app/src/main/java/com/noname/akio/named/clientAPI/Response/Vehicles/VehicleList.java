package com.noname.akio.named.clientAPI.Response.Vehicles;

/**
 * Created by andy on 11/20/16.
 */
public class VehicleList {
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
    private int contract_id;
    /**
     *
     * @return
     * The contract_id
     */
    public int getContract_id()
    {
        return contract_id;
    }
    /**
     *
     * @param contract_id
     * The contract_id
     */
    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
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
    private String created_at;

    /**
     *
     * @return
     * The created_at
     */
    public String getCreated_at()
    {
        return created_at;
    }
    /**
     *
     * @param created_at
     * The created_at
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    private int user_id;

    /**
     *
     * @return
     * The user_id
     */
    public int getUser_id()
    {
        return user_id;
    }
    /**
     *
     * @param user_id
     * The user_id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private String status;
    /**
     *
     * @return
     * The created_at
     */
    public String getStatus()
    {
        return status;
    }
    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
