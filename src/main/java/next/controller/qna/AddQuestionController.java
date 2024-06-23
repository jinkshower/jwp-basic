package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;
import next.model.Question;

public class AddQuestionController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception {
        Question question = new Question(request.getParameter("writer"), request.getParameter("title"),
            request.getParameter("contents"));

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(question);
        return jspView("redirect:/");
    }
}
