package com.example.reposistories.impl;

import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.mappers.SubjectMapper;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;
import connection.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryImpl implements Repository<SubjectDto> {
    private Connection conn;
    public SubjectRepositoryImpl(Connection conn) {
        this.conn = conn;
    }
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return ConnectionDB.getInstance();
    }

    private Subject buildObject(ResultSet resultSet) throws
            SQLException {
        Subject subject = new Subject();
        subject.setId(resultSet.getLong("id_subject"));
        subject.setName(resultSet.getString("name"));
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getLong("id_teacher"));
        teacher.setName(resultSet.getString("name"));
        subject.setTeacher(String.valueOf(teacher));

        return subject;
    }

    @Override
    public List<SubjectDto> list() {
        List<Subject> SubjectList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT subject.name, teachers.name, teachers.email " +
                     "FROM subject INNER JOIN teachers on subject.id_teacher=teachers.id_teacher;")) {
            while (resultSet.next()) {
                Subject Subject = buildObject(resultSet);
                SubjectList.add(Subject);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to list info");
        }
        return SubjectMapper.mapFrom(SubjectList);
    }

    @Override
    public SubjectDto byId(Long id) {
        Subject Subject = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT subject.name, teachers.name, teachers.email FROM subject INNER JOIN " +
                        "teachers on subject.id_teacher=teachers.id_teacher WHERE subject.id_subject = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Subject = buildObject(resultSet);
            }
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to search info");
        }
        return SubjectMapper.mapFrom(Subject);
    }

    @Override
    public void update(SubjectDto Subject) {
        String sql;
        if (Subject.idSubject() != null && Subject.idSubject() > 0) {
            sql = "UPDATE subjects SET name=?, id_teacher=? WHERE id_subject=?";
        } else {
            sql = "INSERT INTO subjects (name, id_teacher) VALUES(?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, Subject.name());
            stmt.setLong(2, Subject.teacher().get());

            if (Subject.idSubject() != null && Subject.idSubject() > 0) {
                stmt.setLong(3, Subject.idSubject());
            }
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to save info");

        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM subjects WHERE id_subject =?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables ){
            throw new ServiceJdbcException("Unable to delete info");
        }
    }
}