package com.veryclone.common.model;

import com.jfinal.plugin.activerecord.Model;

public class PlUser extends Model<PlUser> {
    public static final PlUser dao = new PlUser();

    public boolean save() {
        return super.save();
    }

    public void validate(boolean isCreate) {

    }
}
