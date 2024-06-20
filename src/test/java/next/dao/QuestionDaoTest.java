package next.dao;

import static org.junit.Assert.assertEquals;

import core.jdbc.ConnectionManager;
import java.util.List;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class QuestionDaoTest {

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() throws Exception {
        Question expected = new Question("writer", "title", "contents");
        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(expected);
        Question actual = questionDao.findByQuestionId(expected.getQuestionId());
        assertEquals(expected, actual);

        expected.update(new Question("writer2", "title2", "contents2"));
        questionDao.update(expected);
        actual = questionDao.findByQuestionId(expected.getQuestionId());
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws Exception {
        QuestionDao questionDao = new QuestionDao();
        List<Question> questions = questionDao.findAll();
        assertEquals(8, questions.size());
    }
}
