package com.neptunecentury.kryptpadapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eric on 3/3/2017.
 */

public class ApiProfileResult {
    @SerializedName("Profiles")
    public ApiProfile[] profiles;
}
