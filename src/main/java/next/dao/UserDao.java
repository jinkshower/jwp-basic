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
        new JdbcTemplate() {
            @Override
            public void setValues(final PreparedStatement pstmt)
                throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            @Override
            public Object mapRow(final ResultSet rs) throws SQLException {
                return null;
            }
        }.execute(sql);
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userid = ?";
        new JdbcTemplate() {
            @Override
            public void setValues(final PreparedStatement pstmt)
                throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

            @Override
            public Object mapRow(final ResultSet rs) throws SQLException {
                return null;
            }
        }.execute(sql);
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS";
        List<Object> query = new JdbcTemplate() {
            @Override
            public void setValues(final PreparedStatement pstmt) throws SQLException {

            }

            @Override
            public User mapRow(final ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
            }
        }.query(sql);
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
        return (User) new JdbcTemplate() {
            @Override
            public void setValues(final PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            public Object mapRow(final ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
            }
        }.queryForObject(sql);
    }
}
