package com.veryclone.common.model;

import com.jfinal.plugin.activerecord.Model;

public class PlVideo extends Model<PlVideo> {
    public static final PlVideo dao = new PlVideo();

    public boolean save() {
        return super.save();
    }

    public void validate(boolean isCreate) {

    }
}
