package com.rzc.isibox.presentation.component.slider;

import com.rzc.isibox.master.MySerializable;

public class ImageModel extends MySerializable {

    private String imageUrl;

    public ImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
