package com.pitchbook.bootcamp.repository;

import com.pitchbook.bootcamp.exception.MappingNotSupportedException;
import com.pitchbook.bootcamp.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectRepositoryImpl implements ObjectRepository {

    private Logger logger = LoggerFactory.getLogger(ObjectRepositoryImpl.class);

    private final DataSource dataSource;

    public ObjectRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> void insert(T object) throws MappingNotSupportedException, SQLException, IllegalAccessException {
        String insert = Mapper.generateInsertStatement(object);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            prepareStatement(preparedStatement, object).execute();
        }
    }


    public <T, K> boolean checkIfPrimaryKeyAlreadyExists(K primaryKey, Class<T> c) throws SQLException, IllegalAccessException, InstantiationException, MappingNotSupportedException {
        boolean exists;
        T object = c.newInstance();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(Mapper.generateFindByPrimaryKeyStatement(object))) {
            statement.setObject(1, primaryKey);
            try (ResultSet resultSet = statement.executeQuery()) {
                exists = resultSet.next();
            }
        }
        return exists;
    }


    private <T> PreparedStatement prepareStatement(PreparedStatement preparedStatement, T object) throws IllegalAccessException {
        int index = 1;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (Mapper.isFieldNotAutoIncremented(field)) {
                try {
                    if (field.getType().getSimpleName().equals("String")) {
                        preparedStatement.setString(index, String.valueOf(field.get(object)));
                    } else if (field.getType().getSimpleName().equals("int")) {
                        preparedStatement.setInt(index, (Integer) field.get(object));
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
                index++;
            }
        }
        return preparedStatement;
    }

}
