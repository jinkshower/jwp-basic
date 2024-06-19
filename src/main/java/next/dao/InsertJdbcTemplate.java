package next.dao;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.model.User;

public class InsertJdbcTemplate {

    public void insert(User user, UserDao userDao) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = userDao.createQueryForInsert();
            pstmt = con.prepareStatement(sql);
            userDao.setValuesForInsert(user, pstmt);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}
