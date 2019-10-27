package com.pitchbook.bootcamp.entity;

import com.pitchbook.bootcamp.annotation.Column;
import com.pitchbook.bootcamp.annotation.Entity;
import com.pitchbook.bootcamp.annotation.PrimaryKey;
import com.pitchbook.bootcamp.annotation.Table;

@Entity
@Table(name = "images")
public class Image implements Comparable<Image>{

    @Column(name = "url")
    @PrimaryKey
    private String url;

    public Image(String url) {
        this.url = url;
    }

    public Image(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(Image o) {
        return this.url.compareTo(o.url);
    }
}
