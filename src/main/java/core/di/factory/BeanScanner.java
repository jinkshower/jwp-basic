package core.di.factory;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;

public class BeanScanner {
    private Reflections reflections;

    public BeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> scan() {
        return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiateBeans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiateBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiateBeans;
    }
}
