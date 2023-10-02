package com.example.reposistories.impl;

import com.example.domain.mapping.dto.GradesDto;
import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.mappers.GradesMapper;
import com.example.domain.model.Grades;
import com.example.domain.model.Student;
import com.example.domain.model.Subject;
import com.example.domain.model.Teacher;
import com.example.reposistories.Repository;
import connection.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradesRepositoryImpl implements Repository<GradesDto> {
    private Connection conn;
    public GradesRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        return ConnectionDB.getInstance();
    }


    private Grades buildObject(ResultSet resultSet) throws SQLException {
        Grades grades = new Grades();
        grades.setIdGra(resultSet.getLong("id_grades"));

        Student student = new Student();
        student.setIdStu(resultSet.getLong("id_student"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));
        student.setSemester(resultSet.getString("semester"));
        grades.setStudent(student);

        Subject subject = new Subject();
        subject.setIdSub(resultSet.getLong("id_subject"));
        subject.setName(resultSet.getString("name"));

        Teacher teacher = new Teacher();
        teacher.setIdTea(resultSet.getLong("id_teacher"));
        teacher.setName(resultSet.getString("name"));
        teacher.setEmail(resultSet.getString("email"));
        subject.setTeacher(String.valueOf(teacher));

        grades.setSubject(subject);

        return grades;
    }

    @Override
    public List<GradesDto> list() {
        List<Grades> gradesList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT student.id_student ,student.name, student.email," +
                     " student.career, student.semester, subject.name, teachers.name, teachers.email, grades.corte FROM" +
                     " grades INNER JOIN student on grades.id_student=student.id_student INNER JOIN subject on " +
                     "grades.id_subject=subject.id_subject inner join teachers on " +
                     "subject.id_teacher=teachers.id_teacher;")) {
            while (resultSet.next()) {
                Grades grades = buildObject(resultSet);
                gradesList.add(grades);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return GradesMapper.mapFrom(gradesList);
    }

    @Override
    public GradesDto byId(Long id) {
        Grades grades = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT student.id_student ,student.name, student.email, student.career, " +
                        "student.semester, subject.name, teachers.name, teachers.email, grades.corte FROM grades " +
                        "INNER JOIN student on grades.id_student=student.id_student INNER JOIN subject on " +
                        "grades.id_subject=subject.id_subject inner join teachers on " +
                        "subject.id_teacher=teachers.id_teacher WHERE grades.id_grades = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                grades = buildObject(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return GradesMapper.mapFrom(grades);
    }


    @Override
    public void update(GradesDto grades) {
        String sql;
        if (grades.idGra() != null && grades.idGra() > 0) {
            sql = "UPDATE grades SET idStu=?, idSub=? , corte=?  WHERE idGra=?";
        } else {
            sql = "INSERT INTO grades (idStu, id,subject, grade) VALUES(?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, grades.student().getIdStu());
            stmt.setLong(2, grades.subject().getIdSub());

            if (grades.idGra() != null && grades.idGra() > 0) {
                stmt.setLong(3, grades.idGra());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM gradess WHERE id_grades =?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}