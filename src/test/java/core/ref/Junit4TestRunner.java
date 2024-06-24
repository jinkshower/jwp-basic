package core.ref;

import java.lang.reflect.Method;
import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                Junit4Test junit4Test = clazz.newInstance();
                method.invoke(junit4Test);
            }
        }
    }
}
