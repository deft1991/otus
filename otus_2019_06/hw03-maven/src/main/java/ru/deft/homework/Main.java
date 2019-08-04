package ru.deft.homework;

import ru.deft.homework.annotation.After;
import ru.deft.homework.annotation.Before;
import ru.deft.homework.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class myClass  = Class.forName("ru.deft.homework.test.TestFirst");
        List<String> err = new ArrayList<>();
        Method[] methods = myClass.getMethods();
        List<Method> before = new ArrayList<>();
        List<Method> test = new ArrayList<>();
        List<Method> after = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)){
                before.add(method);
            }
            if (method.isAnnotationPresent(Test.class)){
                test.add(method);
            }
            if (method.isAnnotationPresent(After.class)){
                after.add(method);
            }
        }

        for (Method methodTest : test){
            Object obj = myClass.newInstance();
            invokeMethodList(err, before, obj);
            invokeTestMethods(err, methodTest, obj);
            invokeMethodList(err, after, obj);
        }
        if (err.isEmpty()){
            System.out.println("Test sucseed");
        } else  {
            String errors = err.stream().map(String::valueOf).collect(Collectors.joining(", "));
            System.out.println(String.format("Tests finished with %d errors. Errors : %s", err.size(), errors));
        }
    }

    private static void invokeTestMethods(List<String> err, Method methodTest, Object obj) {
        try {
            Object invoke = methodTest.invoke(obj);
            System.out.println(String.format("Test method = %s result = %s", methodTest.getName(), invoke.toString()));
        } catch (IllegalArgumentException |IllegalAccessException | InvocationTargetException e) {
            err.add(e.getLocalizedMessage());
        }
    }

    private static void invokeMethodList(List<String> err, List<Method> before, Object obj) {
        before.forEach(methodBefore -> {
            try {
                methodBefore.invoke(obj);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                err.add(e.getLocalizedMessage());
            }
        });
    }

}
