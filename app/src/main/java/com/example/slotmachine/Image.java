package com.example.slotmachine;

import android.graphics.drawable.Drawable;

public class Image {
    private int image;
    private int value;

    public Image(int image, int value) {
        this.image = image;
        this.value = value;
    }

    public int getImage() {
        return image;
    }

    public int getValue() {
        return value;
    }
}
