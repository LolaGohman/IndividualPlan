package com.pitchbook.bootcamp.mapper;

import com.pitchbook.bootcamp.annotation.Column;
import com.pitchbook.bootcamp.annotation.Entity;
import com.pitchbook.bootcamp.annotation.PrimaryKey;
import com.pitchbook.bootcamp.annotation.Table;
import com.pitchbook.bootcamp.exception.MappingNotSupportedException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static <T> String generateInsertStatement(T object) throws MappingNotSupportedException {
        StringBuilder firstPart = new StringBuilder("insert into ").append(getTableName(object.getClass())).append("(");
        StringBuilder secondPart = new StringBuilder(" values(");
        List<String> fieldNamesToInsert = new ArrayList<>();
        List<String> fieldValuesToInsert = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (isFieldNotAutoIncremented(field)) {
                fieldNamesToInsert.add(getTableColumnNameByField(field));
                fieldValuesToInsert.add("?");
            }
        }
        return firstPart.append(String.join(",", fieldNamesToInsert)).append(")")
                .append(
                        secondPart.append(String.join(",", fieldValuesToInsert)).append(");")).toString();
    }

    public static <T> String generateFindByPrimaryKeyStatement(T object) throws MappingNotSupportedException {
        StringBuilder find = new StringBuilder("select * from ").append(getTableName(object.getClass())).append(" where ");
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                find.append(field.getName()).append(" = ?");
            }
        }
        return find.toString();
    }

    public static  <T> String getTableName(Class<T> objectClass) throws MappingNotSupportedException {
        if (objectClass.isAnnotationPresent(Entity.class)) {
            return objectClass.isAnnotationPresent(Table.class) ?
                    objectClass.getAnnotation(Table.class).name() :
                    objectClass.getSimpleName();
        } else {
            throw new MappingNotSupportedException("Class " + objectClass + " does not have 'Entity' annotation!");
        }
    }

    private static String getTableColumnNameByField(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            return column.name().isBlank() ? field.getName() : column.name();
        }
        return "";
    }


    private static boolean isFieldNotAutoIncremented(Field field) {
        return !field.isAnnotationPresent(PrimaryKey.class)
                || !field.getAnnotation(PrimaryKey.class).autoIncremented();
    }

    public static String generateGetAllStatement() {
        return "select * from %s";
    }

    public static String generateDeleteStatement() {
        return "delete from %s where %s = ?";
    }


}
