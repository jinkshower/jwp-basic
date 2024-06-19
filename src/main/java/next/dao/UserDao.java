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
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        new JdbcTemplate().execute(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            return pstmt;
        });
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userid = ?";
        new JdbcTemplate().execute(sql, pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            return pstmt;
        });
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS";
        List<Object> query = new JdbcTemplate().query(sql, pstmt -> pstmt,
            rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email")));

        List<User> users = new ArrayList<>();
        for (Object obj : query) {
            if (obj instanceof User) {
                users.add((User) obj);
            }
        }
        return users;
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) new JdbcTemplate().queryForObject(sql,
            pstmt -> {
            pstmt.setString(1, userId);
            return pstmt;
        }, rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
            rs.getString("email")));
    }
}
