package com.noname.akio.named.clientAPI.Response.Me;

/**
 * Created by andy on 11/22/16.
 */
public class Me {
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

    private String username;
    /**
     *
     * @return
     * The name
     */
    public String getUsername()
    {
        return username;
    }
    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private String role;
    /**
     *
     * @return
     * The role
     */
    public String getRole()
    {
        return role;
    }
    /**
     *
     * @param role
     * The role
     */
    public void setRole(String role) {
        this.role = role;
    }

}
