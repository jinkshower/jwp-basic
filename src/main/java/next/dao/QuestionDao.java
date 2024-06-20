package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import java.sql.Timestamp;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    public void insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, new Timestamp(question.getCreatedDate().getTime()));
            pstmt.setInt(5, question.getCountOfAnswer());
        });
    }

    public Question findByQuestionId(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
            rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
            rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));

        return jdbcTemplate.query(sql, rm);
    }

    public void update(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS set writer = ?, title = ?, contents = ?, createdDate = ?, countOfAnswer = ? WHERE questionId = ?";
        jdbcTemplate.update(sql, question.getWriter(), question.getTitle(), question.getContents(),
            question.getCreatedDate(), question.getCountOfAnswer(), question.getQuestionId());
    }
}
