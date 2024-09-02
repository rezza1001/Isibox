package com.rzc.isibox.presentation.component.option;

import com.rzc.isibox.master.MySerializable;

import java.io.Serializable;

public class OptionData extends MySerializable {

    private String id;
    private String value;
    private String info = "";
    private int icon = 0;
    private String iconName ;
    private boolean isSelected = false;

    public OptionData(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public OptionData(String id, String value, int icon) {
        this.id = id;
        this.value = value;
        this.icon = icon;
    }

    public OptionData() {
    }

    public OptionData(String id, String value, boolean isSelected) {
        this.id = id;
        this.value = value;
        this.isSelected = isSelected;
    }
    public OptionData(String id, String value, String info) {
        this.id = id;
        this.value = value;
        this.info = info;
    }

    public OptionData(String value) {
        this.value = value;
        this.id = value;
    }
    public OptionData(String value, int icon) {
        this.value = value;
        this.id = value;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }
}
