package io.techery.github.api.fixtures.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface FieldAnnotationProcessor<A extends Annotation> {

    Object process(A annotation, Field field, BeanFactory factory) throws IllegalAccessException, IOException, InstantiationException;

}
