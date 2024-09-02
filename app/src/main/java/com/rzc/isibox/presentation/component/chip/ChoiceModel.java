package com.rzc.isibox.presentation.component.chip;

import com.rzc.isibox.master.MySerializable;

public class ChoiceModel extends MySerializable {
    private boolean selected = false;
    private String name = "";
    private String key = "";

    public ChoiceModel(boolean selected, String name, String key) {
        this.selected = selected;
        this.name = name;
        this.key = key;
    }

    public ChoiceModel(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public ChoiceModel(String value) {
        this.name = value;
        this.key = value;
    }

    public ChoiceModel() {

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
