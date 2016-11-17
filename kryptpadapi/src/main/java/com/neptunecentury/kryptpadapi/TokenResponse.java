package com.neptunecentury.kryptpadapi;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the token response from oauth
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
