package com.noname.akio.named.clientAPI.Response.Login;
import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")

public class LoginResponse {
    private boolean success;
    /**
     *
     * @return
     * The success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    private LoginData data;

    /**
     *
     * @return
     * The data
     */
    public LoginData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LoginData data) {
        this.data = data;
    }
}
