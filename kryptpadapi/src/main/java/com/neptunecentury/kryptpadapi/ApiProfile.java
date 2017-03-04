package com.neptunecentury.kryptpadapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eric on 3/3/2017.
 */

public class ApiProfile {
    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
