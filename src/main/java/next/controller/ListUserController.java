package next.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.InsertJdbcTemplate;
import next.dao.UpdateJdbcTemplate;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUserController implements Controller {
    private static Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }
        UserDao userDao = new UserDao(new InsertJdbcTemplate(), new UpdateJdbcTemplate());
        List<User> all = new ArrayList<>();
        try {
            all = userDao.findAll();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        req.setAttribute("users", all);
        return "/user/list.jsp";
    }
}
