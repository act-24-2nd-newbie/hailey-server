package com.sds.todo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;



@Service
public class TaskService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void init() {
        // Tasks 테이블 생성
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Tasks ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                + "TITLE VARCHAR(255) NOT NULL, "
                + "DESCRIPTION VARCHAR(255) NOT NULL)";
        jdbcTemplate.execute(createTableSQL);
    }
}
