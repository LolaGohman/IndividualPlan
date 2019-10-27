package com.pitchbook.bootcamp.repository;

import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.exception.MappingNotSupportedException;

import java.sql.SQLException;
import java.util.List;

public interface ImageRepository {

    List<Image> getAll() throws IllegalAccessException, MappingNotSupportedException, InstantiationException, SQLException;

    void delete(String url) throws MappingNotSupportedException, SQLException;

    void insert(Image image) throws MappingNotSupportedException, SQLException;

    boolean checkIfImageAlreadyExists(Image image) throws MappingNotSupportedException, SQLException;
}
