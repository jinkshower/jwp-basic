package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonView implements View {

    @Override
    public void render(final HttpServletRequest req, final HttpServletResponse resp)
        throws Exception {
        Map<String, Object> model = createModel(req);
        
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.write(mapper.writeValueAsString(model));
    }

    private Map<String, Object> createModel(HttpServletRequest req) {
        Enumeration<String> attributeNames = req.getAttributeNames();
        Map<String, Object> model = new HashMap<>();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            model.put(attributeName, req.getAttribute(attributeName));
        }
        return model;
    }
}
