package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            logger.debug(field.toString());
        }

        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            logger.debug(constructor.toString());
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            logger.debug(method.toString());
        }

        logger.debug(clazz.getName());
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        try {
            Class<User> clazz = User.class;
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

            for (Constructor<?> constructor : declaredConstructors) {
                if (constructor.getParameterCount() == 4) {
                    // 인자의 타입을 지정합니다.
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    if (parameterTypes[0] == String.class &&
                        parameterTypes[1] == int.class &&
                        parameterTypes[2] == boolean.class &&
                        parameterTypes[3] == double.class) {

                        // 인스턴스를 생성합니다.
                        Object instance = constructor.newInstance("example", 25, true, 99.99);
                        logger.debug("Instance created: " + instance.toString());
                    }
                }
            }
            logger.debug(clazz.getName());
        } catch (Exception e) {
            logger.error("An error occurred", e);
        }
    }
    
    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        Student student = new Student();
        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");
        name.setAccessible(true);
        age.setAccessible(true);
        name.set(student, "주한");
        age.set(student, 25);

        logger.debug("Name: " + student.getName());
        logger.debug("Age: " + student.getAge());
        logger.debug(clazz.getName());
    }
}
