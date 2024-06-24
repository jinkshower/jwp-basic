package core.ref;

import java.lang.reflect.Method;
import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("test")) {
                Junit3Test junit3Test = clazz.newInstance();
                method.invoke(junit3Test);
            }
        }
    }
}
