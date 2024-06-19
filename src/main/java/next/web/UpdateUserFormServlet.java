package next.web;

import core.db.DataBase;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/updateForm")
public class UpdateUserFormServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        log.debug("Update Form Request for user : {}", user);

        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");
        User sessionUser = (User) value;
        if (!user.equals(sessionUser)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req, resp);
    }
}
