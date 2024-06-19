package next.dao;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.model.User;

public abstract class UpdateJdbcTemplate {

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionManager.getConnection();
            String sql = createQueryForUpdate();
            pstmt = conn.prepareStatement(sql);
            setValuesForUpdate(user, pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public abstract void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;
    public abstract String createQueryForUpdate();
}
