package com.pitchbook.bootcamp.repository;

import com.pitchbook.bootcamp.exception.MappingNotSupportedException;

import java.sql.SQLException;

public interface ObjectRepository {

    <T> void insert(T object) throws MappingNotSupportedException, SQLException, IllegalAccessException;

}
