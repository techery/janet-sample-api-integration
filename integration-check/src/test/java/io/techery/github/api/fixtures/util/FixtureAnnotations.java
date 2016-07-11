package io.techery.github.api.fixtures.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import ie.corballis.fixtures.annotation.Fixture;
import ie.corballis.fixtures.io.ClassPathFixtureScanner;
import ie.corballis.fixtures.util.FieldSetter;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

/**
 * Mimics {@link ie.corballis.fixtures.annotation.FixtureAnnotations} but for all super classes of provided instance
 */
public class FixtureAnnotations {

    private static final Map<Class<? extends Annotation>, FieldAnnotationProcessor<?>> annotationProcessorMap = newHashMap();

    static {
        annotationProcessorMap.put(Fixture.class, new FixtureFieldAnnotationProcessor());
    }

    public static void initFixtures(Object targetInstance, Gson gson) throws Exception {
        checkNotNull(targetInstance, "Target instance must not be null");

        BeanFactory beanFactory = new BeanFactory(gson, new ClassPathFixtureScanner());
        beanFactory.init();

        processAnnotations(targetInstance, beanFactory);
    }

    private static void processAnnotations(Object targetInstance, BeanFactory beanFactory) throws Exception {
        Class<?> targetClass = targetInstance.getClass();
        processAnnotationsRecursively(targetInstance, beanFactory, targetClass);

    }

    private static void processAnnotationsRecursively(Object targetInstance, BeanFactory beanFactory, Class<?> targetClass) throws Exception {
        if (targetClass.isAssignableFrom(Object.class)) return;
        else {
            processAnnotationsForClass(targetInstance, beanFactory, targetClass);
            processAnnotationsRecursively(targetInstance, beanFactory, targetClass.getSuperclass());
        }
    }

    private static void processAnnotationsForClass(Object targetInstance, BeanFactory beanFactory, Class targetClass) throws Exception {
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            for (Annotation annotation : field.getAnnotations()) {
                Object bean = generateFixture(annotation, field, beanFactory);
                if (bean != null) {
                    try {
                        new FieldSetter(targetInstance, field).set(bean);
                    } catch (Exception e) {
                        throw new Exception("Problems setting field " + field.getName() + " annotated with "
                                + annotation, e);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Object generateFixture(
            Annotation annotation, Field field,
            BeanFactory beanFactory) throws IllegalAccessException, InstantiationException, IOException {

        Object result = null;

        if (annotationProcessorMap.containsKey(annotation.annotationType())) {
            FieldAnnotationProcessor processor = annotationProcessorMap.get(annotation.annotationType());
            result = processor.process(annotation, field, beanFactory);
        }

        return result;
    }

}
