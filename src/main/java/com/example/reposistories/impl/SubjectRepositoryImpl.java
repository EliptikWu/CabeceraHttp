package com.example.reposistories.impl;

import annotations.MysqlConn;
import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.mappers.SubjectMapper;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@RequestScoped
@Named("Subject")
public class SubjectRepositoryImpl implements Repository<SubjectDto> {
    @Inject
    @MysqlConn
    private Connection conn;

    private Subject buildObject(ResultSet resultSet) throws
            SQLException {
        Subject subject = new Subject();
        subject.setIdSub(resultSet.getLong("idSub"));
        subject.setName(resultSet.getString("name"));
        Teacher teacher = new Teacher();
        teacher.setIdTea(resultSet.getLong("idTea"));
        teacher.setName(resultSet.getString("name"));
        teacher.setEmail(resultSet.getString("email"));
        subject.setTeacher(teacher);

        return subject;
    }

    @Override
    public List<SubjectDto> list() {
        List<Subject> SubjectList = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT subject.idSub, subject.name, teacher.name AS teacher, teacher.idTea,  teacher.email " +
                     "FROM subject INNER JOIN teacher on subject.teacher=teacher.idTea;")) {
            while (resultSet.next()) {
                Subject Subject = buildObject(resultSet);
                SubjectList.add(Subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SubjectMapper.mapFrom(SubjectList);
    }

    @Override
    public SubjectDto byId(Long id) {
        Subject Subject = null;
        try (PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT subject.name, teacher.name, teacher.email FROM subject INNER JOIN " +
                        "teacher on subject.idTea=teacher.idTea WHERE subject.idSub = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Subject = buildObject(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to search info");
        }
        return SubjectMapper.mapFrom(Subject);
    }

    @Override
    public void update(SubjectDto Subject) {
        String sql;
        if (Subject.idSub() != null && Subject.idSub() > 0) {
            sql = "UPDATE subject SET name=?, teacher=? WHERE idSub=?";
        } else {
            sql = "INSERT INTO subject (name, teacher) VALUES(?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Subject.name());
            stmt.setLong(2, Long.valueOf(Subject.teacher().getIdTea()));

            if (Subject.idSub() != null && Subject.idSub() > 0) {
                stmt.setLong(3, Subject.idSub());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to save info");

        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM subject WHERE idSub =?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables ){
            throw new ServiceJdbcException("Unable to delete info");
        }
    }
}