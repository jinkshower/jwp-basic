package next.dao;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void execute(String sql, PreparedStatementSetter pstst) {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstst.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void execute(String sql, Object... parameters) {
        try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pstst, RowMapper<T> rm) {
        try (Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstst.setValues(pstmt);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rm.mapRow(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> List<T> query(String sql, PreparedStatementSetter pstst, RowMapper<T> rm) {
        List<T> objects = new ArrayList<>();

        try (Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    objects.add(rm.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return new ArrayList<>(objects);
    }
}
