package next.controller.qna;

import core.mvc.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateQuestionController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

    @Override
    public String execute(final HttpServletRequest req, final HttpServletResponse resp)
        throws Exception {
        Question question = new Question(
            req.getParameter("writer"),
            req.getParameter("title"),
            req.getParameter("contents")
        );
        log.debug("Question : {}", question);

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);
        return "redirect:/";
    }
}
