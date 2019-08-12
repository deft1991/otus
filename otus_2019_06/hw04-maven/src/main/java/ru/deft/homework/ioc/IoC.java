package ru.deft.homework.ioc;

import ru.deft.homework.annotation.Log;
import ru.deft.homework.interfaces.PrintInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IoC {

    private static final String LOG_MSG = "Execute method %s with params {%s}";
    private static List<Method> logableMethods = new ArrayList<>();

    public static Object createProxyClass(Class clazz) {
        InvocationHandler printInvocationHandler = new MyLogInvocationHandler(clazz);
        return Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class[] {PrintInterface.class}, printInvocationHandler);

    }

    private static class MyLogInvocationHandler implements InvocationHandler {

        Class clazz;

        MyLogInvocationHandler(Class clazz) {
            this.clazz = clazz;
        }

        @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logableMethods.contains(method)) {
                printMethod(method, args);
                return method.invoke(clazz.newInstance(), args);
            }
            Method proxyMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
            Log annotation = proxyMethod.getAnnotation(Log.class);
            if (annotation != null) {
                logableMethods.add(method);
                printMethod(method, args);
            }
            return method.invoke(clazz.newInstance(), args);
        }

        private void printMethod(Method method, Object[] args) {
            System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
            String argsToString = "";
            if (args != null) {
                argsToString = Arrays.stream(args).map(Objects::toString).collect(Collectors.joining(", "));
            }
            System.out.println(String.format(LOG_MSG, method.getName(), argsToString));
        }
    }

}
