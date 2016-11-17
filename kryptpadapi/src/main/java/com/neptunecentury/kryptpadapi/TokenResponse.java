package com.neptunecentury.kryptpadapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neptu on 11/16/2016.
 */

public class TokenResponse {
    public String access_token;
    public String token_type;
    public String expires_in;
    public String refresh_token;
    public String userName;
    @SerializedName(".issued")
    public String issued;
    @SerializedName(".expires")
    public String expires;
}
