package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.execute(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userid = ?";
        jdbcTemplate.execute(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS";
        List<User> query = jdbcTemplate.query(sql, pstmt -> pstmt,
            rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email")));
        return query;
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return jdbcTemplate.queryForObject(sql,
            pstmt -> {
            pstmt.setString(1, userId);
            return pstmt;
        }, rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
            rs.getString("email")));
    }
}
