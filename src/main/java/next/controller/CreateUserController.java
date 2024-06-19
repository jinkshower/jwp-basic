package next.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.InsertJdbcTemplate;
import next.dao.UpdateJdbcTemplate;
import next.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

//        DataBase.addUser(user);
        UserDao userDao = new UserDao(new InsertJdbcTemplate(), new UpdateJdbcTemplate());
        try {
            userDao.insert(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return "redirect:/";
    }
}
