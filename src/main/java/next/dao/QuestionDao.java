package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import java.util.List;
import next.model.Question;

public class QuestionDao {
    public void insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, question.getWriter(), question.getTitle(), question.getContents(),
            question.getCreatedDate());
    }

    public Question findByQuestionId(String questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
            rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS";

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
