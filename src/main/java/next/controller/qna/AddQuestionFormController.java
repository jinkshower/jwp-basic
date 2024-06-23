package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.controller.UserSessionUtils;
import next.model.User;

public class AddQuestionFormController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request,
        final HttpServletResponse response)
        throws Exception {
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        if (user == null) {
            return jspView("redirect:/user/login.jsp");
        }
        return jspView("/qna/form.jsp").addObject("user", user);
    }
}
