package com.example.reposistories.impl;

import annotations.MysqlConn;
import com.example.domain.mapping.dto.GradesDto;
import com.example.domain.mapping.mappers.GradesMapper;
import com.example.domain.model.Grades;
import com.example.domain.model.Student;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.reposistories.Repository;
import connection.ConnectionDB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@RequestScoped
@Named("Grade")
public class GradesRepositoryImpl implements Repository<GradesDto> {
    @Inject
    @MysqlConn
    private Connection conn;

    private Grades buildObject(ResultSet resultSet) throws SQLException {
        Grades grades = new Grades();
        grades.setIdGra(resultSet.getLong("idGra"));

        Student student = new Student();
        student.setIdStu(resultSet.getLong("idStu"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));
        student.setSemester(resultSet.getString("semester"));
        grades.setStudent(student);

        Subject subject = new Subject();
        subject.setIdSub(resultSet.getLong("idSub"));
        subject.setName(resultSet.getString("name"));

        Teacher teacher = new Teacher();
        teacher.setIdTea(resultSet.getLong("idTea"));
        teacher.setName(resultSet.getString("name"));
        teacher.setEmail(resultSet.getString("email"));
        subject.setTeacher(teacher);

        grades.setSubject(subject);

        return grades;
    }

    @Override
    public List<GradesDto> list() {
        List<Grades> gradesList = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT student.idStu ,student.name, student.email" +
                     ", student.semester, subject.name, teacher.name, teacher.email " +
                     "FROM grades INNER JOIN student ON grades.student=student.idStu INNER JOIN subject " +
                     "ON grades.subject=subject.idSub INNER JOIN teacher ON subject.teacher=teacher.idTea;")) {
            while (resultSet.next()) {
                Grades grades = buildObject(resultSet);
                gradesList.add(grades);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GradesMapper.mapFrom(gradesList);
    }

    @Override
    public GradesDto byId(Long id) {
        Grades grades = null;
        try (PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT student.idStu ,student.name, student.email, " +
                        "student.semester, subject.name, teacher.name, teacher.email, grades.corte FROM grades " +
                        "INNER JOIN student on grades.idStu=student.idStu INNER JOIN subject on " +
                        "grades.idSub=subject.idSub inner join teacher on " +
                        "subject.idTea=teachers.idTea WHERE grades.idGra = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                grades = buildObject(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GradesMapper.mapFrom(grades);
    }


    @Override
    public void update(GradesDto grades) {
        String sql;
        if (grades.idGra() != null && grades.idGra() > 0) {
            sql = "UPDATE grades SET idStu=?, idSub=? , grade=?  WHERE idGra=?";
        } else {
            sql = "INSERT INTO grades (idStu, idSub, grade) VALUES(?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, grades.student().getIdStu());
            stmt.setLong(2, grades.subject().getIdSub());

            if (grades.idGra() != null && grades.idGra() > 0) {
                stmt.setLong(3, grades.idGra());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM grades WHERE idGra =?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}