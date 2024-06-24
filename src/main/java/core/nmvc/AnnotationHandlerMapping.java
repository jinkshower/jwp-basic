package core.nmvc;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        //controller scanner로 controller 클래스를 모두 찾는다.
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        //RequestMapping이 붙은 메서드를 모두 찾는다
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());

        for (Method method : methods) {
            //메서드에서 Requestmapping에 설정한 값을 찾는다
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            logger.debug("register handlerExecution : url is {}, method is {}", rm.value(), method);
            handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        }
    }

    private HandlerKey createHandlerKey(final RequestMapping rm) {
        //어노테이션에 선언된 url, http method로 만든다
        return new HandlerKey(rm.value(), rm.method());
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethods(final Set<Class<?>> classes) {
        //Controller 어노테이션이 붙은 클래스에서 RequestMapping어노테이션이 붙은 메서드들을 찾음
        Set<Method> requestMappingMethods = Sets.newHashSet();
        for (Class<?> clazz : classes) {
            Set<Method> allMethods = ReflectionUtils.getAllMethods(clazz,
                ReflectionUtils.withAnnotation(RequestMapping.class));
            requestMappingMethods.addAll(allMethods);
        }
        return requestMappingMethods;
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
