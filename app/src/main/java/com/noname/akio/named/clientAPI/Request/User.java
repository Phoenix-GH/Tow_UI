package com.noname.akio.named.clientAPI.Request;

/**
 * Created by andy on 11/21/16.
 */
public class User {
    private String username;
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUsername()
    {
        return username;
    }

    private String password;
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return password;
    }
}
