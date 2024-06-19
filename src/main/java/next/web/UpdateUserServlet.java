package next.web;

import core.db.DataBase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        String userId = req.getParameter("userId");
        log.debug("Update Request for userId : {}", userId);

        User user = DataBase.findUserById(userId);
        DataBase.update(new User(userId, req.getParameter("password"), req.getParameter("name"), req.getParameter("email")));

        resp.sendRedirect("/user/list");
    }
}
