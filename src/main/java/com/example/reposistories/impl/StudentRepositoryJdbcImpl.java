package com.example.reposistories.impl;

import annotations.MysqlConn;
import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.mapping.mappers.StudentMapper;
import com.example.domain.model.Student;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@RequestScoped
@Named("Student")
public class StudentRepositoryJdbcImpl implements Repository<StudentDto> {
    @Inject
    @MysqlConn
    private Connection conn;
    private StudentDto createStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setIdStu(rs.getLong("idStu"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setSemester(rs.getString("semester"));
        return StudentMapper.mapFrom(student);
    }
    @Override
    public List<StudentDto> list(){
        List<StudentDto> studentList = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from student")) {
            while (rs.next()) {
                StudentDto ps = createStudent(rs);
                studentList.add(ps);
            }
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to list info");
        }
        return studentList;
    }


    @Override
    public StudentDto byId(Long id) {
        StudentDto student = null;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student WHERE idStu=?")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    student = createStudent(rs);
                }
            }
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to find info");
        }
        return student;
    }

    @Override
    public void update(StudentDto student) {
        String sql;
        if (student.idStu() != null && student.idStu() > 0) {
            sql = "UPDATE student SET name=?, email=?, semester=? WHERE idStu=?";
        } else {
            sql = "INSERT INTO student (name, email, semester) VALUES(?,?,?)";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.name());
            pstmt.setString(2, student.email());
            pstmt.setString(3, student.semester());

            if (student.idStu() != null && student.idStu() > 0) {
                pstmt.setLong(4, student.idStu());
            }
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM student WHERE idStu = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to delete info");
        }
    }


}