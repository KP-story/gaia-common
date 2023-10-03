package com.delight.gaia.base.utility;

import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GaiaClassUtils {
    public static List<Field> listAllFields(Class c) {
        List<Field> fieldList = new ArrayList<Field>();
        while (c != null) {
            fieldList.addAll(Arrays.asList(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        return fieldList;
    }

    public static Type getFirstGeneric(Type c) {
        return((ParameterizedType) c).getActualTypeArguments()[0];
    }



    public static List<Field> listAllFields(Class c, Class annotation) {
        List<Field> fieldList = new ArrayList<Field>();
        while (c != null) {
            List<Field> fields = Arrays.asList(c.getDeclaredFields());
            for (Field field : fields) {
                if (field.getAnnotation(annotation) != null) {
                    fieldList.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return fieldList;
    }
}
