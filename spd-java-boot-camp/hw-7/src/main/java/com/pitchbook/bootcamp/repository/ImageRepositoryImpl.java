package com.pitchbook.bootcamp.repository;

import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.exception.MappingNotSupportedException;
import com.pitchbook.bootcamp.mapper.Mapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final DataSource dataSource;

    public ImageRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Image image) throws MappingNotSupportedException, SQLException {
        String insert = Mapper.generateInsertStatement(image);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, image.getUrl());
            preparedStatement.execute();
        }
    }

    @Override
    public boolean checkIfImageAlreadyExists(Image image) throws MappingNotSupportedException, SQLException {
        boolean exists;
        String findBy = Mapper.generateFindByPrimaryKeyStatement(image);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findBy)) {
            statement.setString(1, image.getUrl());
            try (ResultSet resultSet = statement.executeQuery()) {
                exists = resultSet.next();
            }
        }
        return exists;
    }

    @Override
    public List<Image> getAll() throws MappingNotSupportedException, SQLException {
        List<Image> images = new ArrayList<>();
        String getAll = String.format(Mapper.generateGetAllStatement(), Mapper.getTableName(Image.class));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAll)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    images.add(new Image(resultSet.getString("url")));
                }
            }
        }
        return images;
    }

    @Override
    public void delete(String url) throws MappingNotSupportedException, SQLException {
        String delete = String.format(Mapper.generateDeleteStatement(),
                Mapper.getTableName(Image.class), "url");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setString(1, url);
            preparedStatement.execute();
        }
    }

}
