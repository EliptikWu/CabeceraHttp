package com.example.reposistories.impl;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;
import com.example.reposistories.Repository;
import com.sun.jdi.connect.spi.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryJdbcImpl implements Repository<Student> {
    private Connection conn;
    public StudentRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<StudentDto> list() throws SQLException {
        List<StudentDto> students = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT s.* order by s.id ASC")) {
            while (rs.next()) {Student ps = getStudent(rs);
                students.add(s);
            }
        }
        return mapper.mapFrom(students);
    }

    @Override
    public List<Student> listar() {
        return null;
    }

    @Override
    public Student porId(Long id) {
        return null;
    }

    @Override
    public void guardar(Student student) {

    }

    @Override
    public void eliminar(Long id) {

    }
}