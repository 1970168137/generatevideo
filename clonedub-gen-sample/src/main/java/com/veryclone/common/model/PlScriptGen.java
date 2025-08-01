package com.veryclone.common.model;

import com.jfinal.plugin.activerecord.Model;

public class PlScriptGen extends Model<PlScriptGen> {
    public static final PlScriptGen dao = new PlScriptGen();

    public boolean save() {
        return super.save();
    }

    public void validate(boolean isCreate) {

    }
}
