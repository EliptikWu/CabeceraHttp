package com.example.reposistories.impl;

import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.mappers.SubjectMapper;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;
import connection.ConnectionDB;
import lombok.NoArgsConstructor;

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
        subject.setIdSub(resultSet.getLong("idsubject"));
        subject.setName(resultSet.getString("name"));
        Teacher teacher = new Teacher();
        teacher.setIdTea(resultSet.getLong("idteacher"));
        teacher.setName(resultSet.getString("name"));
        subject.setTeacher(String.valueOf(teacher));

        return subject;
    }

    @Override
    public List<SubjectDto> list() {
        List<Subject> SubjectList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT subject.name, teachers.name, teachers.email " +
                     "FROM subject INNER JOIN teachers on subject.idTea=teachers.idTea;")) {
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
                        "teachers on subject.idTea=teachers.idTea WHERE subject.idSub = ?")) {
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
        if (Subject.idSub() != null && Subject.idSub() > 0) {
            sql = "UPDATE subjects SET name=?, idTea=? WHERE idSub=?";
        } else {
            sql = "INSERT INTO subjects (name, idTea) VALUES(?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, Subject.name());
            stmt.setLong(2, Long.parseLong(Subject.teacher()));

            if (Subject.idSub() != null && Subject.idSub() > 0) {
                stmt.setLong(3, Subject.idSub());
            }
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceJdbcException("Unable to save info");

        }
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM subjects WHERE idSub =?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables ){
            throw new ServiceJdbcException("Unable to delete info");
        }
    }
}