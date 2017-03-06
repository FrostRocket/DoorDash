package com.frostrocket.doordash.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("is_catering")
    @Expose
    private Boolean isCatering;

    @SerializedName("subtitle")
    @Expose
    private String subtitle;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    public Boolean getCatering() {
        return isCatering;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
