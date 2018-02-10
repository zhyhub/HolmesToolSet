package com.zhy.holmestoolset;

import com.yalantis.beamazingtoday.interfaces.BatModel;

/**
 * Created by zhy on 2018/2/10 0010.
 * email: 760982661@qq.com
 * dec:
 */

public class Goal implements BatModel {

    private String name;

    private boolean isChecked;

    public Goal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public String getText() {
        return getName();
    }
}
