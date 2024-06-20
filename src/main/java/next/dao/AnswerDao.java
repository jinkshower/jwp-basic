package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import java.util.List;
import next.model.Answer;

public class AnswerDao {

    public List<Answer> findAllByQuestionId(final Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId=?";

        RowMapper<Answer> rm = rs -> new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
            rs.getDate("createdDate"), rs.getLong("questionId"));

        return jdbcTemplate.query(sql, rm, questionId);
    }
}
