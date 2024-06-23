package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddQuestionController.class);

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception {
        String userId = request.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        log.debug("user : {}", user);

        Question question = new Question(user.getUserId(), request.getParameter("title"),
            request.getParameter("contents"));

        QuestionDao questionDao = new QuestionDao();
        Question inserted = questionDao.insert(question);

        log.debug("question : {}", inserted);

        return jspView("redirect:/");
    }
}
