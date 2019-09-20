package ru.deft.homework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonWriterImpl implements JsonWriter {

    private static JsonWriterImpl jsonWriter = new JsonWriterImpl();

    @Override public String writeToJson(Object obj) throws IllegalAccessException {
        JSONObject jsObject = new JSONObject();
        Class aClass = obj.getClass();
        boolean match = Integer.class.equals(aClass) || Long.class.equals(aClass) || Character.class.equals(aClass) || Short.class
                .equals(aClass) || Boolean.class.equals(aClass) || Byte.class.equals(aClass);
        if (match) {
            return obj.toString();
        } else {
            parserToJson(obj, jsObject, aClass);
        }
        String rezult = jsObject.toJSONString();
        System.out.println(rezult);
        return rezult;
    }

    private JSONObject writeToObj(Object obj) throws IllegalAccessException {
        JSONObject jsObject = new JSONObject();
        Class aClass = obj.getClass();
        parserToJson(obj, jsObject, aClass);
        return jsObject;
    }

    private boolean isBaseType(Object arrayElement) {
        return Long.class.equals(arrayElement.getClass()) || Integer.class.equals(arrayElement) || String.class
                .equals(arrayElement) || Character.class.equals(arrayElement) || Float.class.equals(arrayElement) || Double.class
                .equals(arrayElement);
    }

    @SuppressWarnings("unchecked") private void parserToJson(Object obj, JSONObject jsObject, Class aClass)
            throws IllegalAccessException {
        for (Field declaredField : aClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (isIterable(declaredField.getType())) {
                JSONArray jsonArray = new JSONArray();
                Iterable iterable = (Iterable) declaredField.get(obj);
                if (iterable != null) {
                    for (Object value : iterable) {
                        jsonArray.add(value);
                    }
                }
                jsObject.put(declaredField.getName(), jsonArray);
            } else if (isMap(declaredField.getType()) && declaredField.get(obj) != null) {
                JSONObject mapObject = new JSONObject((Map) declaredField.get(obj));
                jsObject.put(declaredField.getName(), mapObject);
            } else if (declaredField.getType().isArray() && declaredField.get(obj) != null) {
                Object[] arr = (Object[]) declaredField.get(obj);
                int length = Array.getLength(arr);
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < length; i++) {
                    Object arrayElement = Array.get(arr, i);
                    if (isBaseType(arrayElement)) {
                        String jsonString = jsonWriter.writeToJson(arrayElement);
                        jsonArray.add(jsonString);
                    } else {
                        JSONObject jsonObject = jsonWriter.writeToObj(arrayElement);
                        jsonArray.add(jsonObject);
                    }
                }
                jsObject.put(declaredField.getName(), jsonArray);
            } else {
                jsObject.put(declaredField.getName(), declaredField.get(obj));
            }
            declaredField.setAccessible(false);
        }
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
