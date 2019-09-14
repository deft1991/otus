package ru.deft.homework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonWriterImpl implements JsonWriter {

    @SuppressWarnings("unchecked") @Override public String writeToJson(Object obj) throws IllegalAccessException {
        Class aClass = obj.getClass();
        JSONObject jsObject = new JSONObject();
        for (Field declaredField : aClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (isIterable(declaredField.getType())) {
                JSONArray jsonArray = new JSONArray();
                Iterable iterable = (Iterable) declaredField.get(obj);
                for (Object value : iterable) {
                    jsonArray.add(value);
                }
                jsObject.put(declaredField.getName(), jsonArray);
            } else if (isMap(declaredField.getType())) {
                JSONObject mapObject = new JSONObject((Map) declaredField.get(obj));
                jsObject.put(declaredField.getName(), mapObject);
            } else if(declaredField.getType().isArray() && declaredField.get(obj) != null){
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(Arrays.asList((Object[]) declaredField.get(obj)));
                jsObject.put(declaredField.getName(), jsonArray);
            } else {
                jsObject.put(declaredField.getName(), declaredField.get(obj));
            }
            declaredField.setAccessible(false);
        }
        String rezult = jsObject.toJSONString();
        System.out.println(rezult);
        return rezult;
    }

    private static boolean isIterable(Type type) {
        if (type instanceof Class && isIterableClass((Class) type)) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return isIterable(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            return upperBounds.length != 0 && isIterable(upperBounds[0]);
        }
        return false;
    }

    private static boolean isIterableClass(Class clazz) {
        List<Class<?>> classes = new ArrayList<>();
        computeClassHierarchy(clazz, classes);
        return classes.contains(Iterable.class);
    }

    private static void computeClassHierarchy(Class<?> clazz, List<Class<?>> classes) {
        for (Class current = clazz; current != null; current = current.getSuperclass()) {
            if (classes.contains(current)) {
                return;
            }
            classes.add(current);
            for (Class currentInterface : current.getInterfaces()) {
                computeClassHierarchy(currentInterface, classes);
            }
        }
    }

    private boolean isMap(Class<?> type) {
        return Map.class.equals(type);
    }
}
