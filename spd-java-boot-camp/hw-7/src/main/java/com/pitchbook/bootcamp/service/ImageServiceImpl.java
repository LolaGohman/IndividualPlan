package com.pitchbook.bootcamp.service;

import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.exception.MappingNotSupportedException;
import com.pitchbook.bootcamp.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> getAll() {
        try {
            return imageRepository.getAll();
        } catch (IllegalAccessException | MappingNotSupportedException | InstantiationException | SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String url) {
        try {
            imageRepository.delete(url);
        } catch (MappingNotSupportedException | SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Image image) {
        try {
            if (!imageRepository.checkIfImageAlreadyExists(image)) {
                imageRepository.insert(image);
            }
        } catch (MappingNotSupportedException | SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
