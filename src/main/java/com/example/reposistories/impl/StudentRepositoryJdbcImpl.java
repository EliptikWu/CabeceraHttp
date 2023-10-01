package com.example.reposistories.impl;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.mapping.mappers.StudentMapper;
import com.example.domain.model.Student;
import com.example.exceptions.ServiceJdbcException;
import com.example.reposistories.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryJdbcImpl implements Repository<StudentDto> {
    private Connection conn;
    public StudentRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    private Student createStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setName(rs.getString("nombre"));
        student.setEmail(rs.getString("email"));
        student.setSemester(rs.getString("semester"));
        return student;
    }
    @Override
    public List<StudentDto> list(){
        List<Student> studentList = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * from students")) {
            while (rs.next()) {
                Student ps= createStudent(rs);
                studentList.add(ps);
            }
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to list info");
        }
        return StudentMapper.mapFrom(studentList);
    }


    @Override
    public StudentDto byId(Long id) {
        Student student = null;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student WHERE id_student=?")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    student = createStudent(rs);
                }
            }
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to find info");
        }
        return StudentMapper.mapFrom(student);
    }

    @Override
    public void update(StudentDto student) {
        String sql;
        if (student.idStudent() != null && student.idStudent() > 0) {
            sql = "UPDATE student SET name=?, career=?, email=?, semester=? WHERE id_student=?";
        } else {
            sql = "INSERT INTO student (name, career, email, semester) VALUES(?,?,?,?)";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.name());
            pstmt.setString(2, student.email());

            if (student.idStudent() != null && student.idStudent() > 0) {
                pstmt.setLong(4, student.idStudent());
            }
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException("Unable to save info");
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM student WHERE id_student = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceJdbcException("Unable to delete info");
        }
    }


}