package next.dao;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.model.User;

public class UpdateJdbcTemplate {

    public void update(User user, UserDao userDao) throws SQLException {
        // TODO 구현 필요함.
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionManager.getConnection();
            String sql = userDao.createQueryForUpdate();
            pstmt = conn.prepareStatement(sql);
            userDao.setValuesForUpdate(user, pstmt);

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
}
