package core.mvc;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.ControllerHandlerAdapter;
import core.nmvc.HandlerAdapter;
import core.nmvc.HandlerExecutionAdapter;
import core.nmvc.HandlerMapping;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyRequestMapping lrm;
    private AnnotationHandlerMapping ahm;
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        lrm = new LegacyRequestMapping();
        lrm.initMapping();
        ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();

        handlerMappings.add(lrm);
        handlerMappings.add(ahm);
        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if (handler == null) {
            throw new IllegalArgumentException("존재하지 않는 URL 입니다.");
        }

        try {
            ModelAndView mav = execute(handler, req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest req) {
        for (HandlerMapping hm : handlerMappings) {
            Object handler = hm.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    private ModelAndView execute(final Object handler, final HttpServletRequest req,
        final HttpServletResponse resp) throws Exception {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(req, resp, handler);
            }
        }
        return null;
    }
}
