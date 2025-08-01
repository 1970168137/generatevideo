package com.veryclone.common.model;

import com.jfinal.plugin.activerecord.Model;

public class PlResource extends Model<PlResource> {
    public static final PlResource dao = new PlResource();

    public boolean save() {
        return super.save();
    }

    public void validate(boolean isCreate) {

    }
}
