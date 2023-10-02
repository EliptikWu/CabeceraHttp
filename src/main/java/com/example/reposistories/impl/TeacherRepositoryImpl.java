package com.example.reposistories.impl;

import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.mapping.mappers.TeacherMapper;
import com.example.domain.model.Teacher;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;
import connection.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl implements Repository<TeacherDto> {
    private Connection conn;
    public TeacherRepositoryImpl(Connection conn) {
        this.conn = conn;
    }
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return ConnectionDB.getInstance();
    }

    private Teacher buildObject(ResultSet resultSet) throws
            SQLException {
        Teacher teacher = new Teacher();
        teacher.setIdTea(resultSet.getLong("idTea"));
        teacher.setName(resultSet.getString("name"));
        teacher.setEmail(resultSet.getString("email"));

        return teacher;
    }

    @Override
    public List<TeacherDto> list() {
        List<Teacher> teacherList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from teachers")) {
            while (resultSet.next()) {
                Teacher teacher = buildObject(resultSet);
                teacherList.add(teacher);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to list info");
        }
        return TeacherMapper.mapFrom(teacherList);
    }

    @Override
    public TeacherDto byId(Long id) {
        Teacher teacher = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM teachers WHERE idTea =?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacher = buildObject(resultSet);
            }
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to find info");
        }
        return TeacherMapper.mapFrom(teacher);
    }

    @Override
    public void update(TeacherDto teacher) {
        String sql;
        if (teacher.idTeacher() != null && teacher.idTeacher() > 0) {
            sql = "UPDATE teachers SET name=?, email=? WHERE idTea=?";
        } else {
            sql = "INSERT INTO teachers (name, email) VALUES(?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, teacher.name());
            stmt.setString(2, teacher.email());

            if (teacher.idTeacher() != null && teacher.idTeacher() > 0) {
                stmt.setLong(3, teacher.idTeacher());
            }
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to save info");
        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM teachers WHERE idTea =?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables){
            throw new ServiceJdbcException("Unable to delete info");
        }
    }
}
