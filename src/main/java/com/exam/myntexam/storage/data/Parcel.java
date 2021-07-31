package com.exam.myntexam.storage.data;

/**
 *
 * @author lorenzomalafo
 */
public class Parcel {
    private long weight;
    private long width;
    private long height;
    private long length;

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
    
    public long getVolume() {
        return length * width * height;
    }
}
