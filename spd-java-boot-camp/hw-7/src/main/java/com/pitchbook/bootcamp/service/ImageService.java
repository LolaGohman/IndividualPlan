package com.pitchbook.bootcamp.service;

import com.pitchbook.bootcamp.entity.Image;

import java.util.List;

public interface ImageService {

    List<Image> getAll();

    void delete(String url);

    void insert(Image image);
}
