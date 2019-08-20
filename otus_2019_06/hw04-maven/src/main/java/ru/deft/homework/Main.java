package ru.deft.homework;

import ru.deft.homework.classLoader.LogClassLoader;
import ru.deft.homework.impl.PrintBye;
import ru.deft.homework.impl.PrintHello;
import ru.deft.homework.interfaces.PrintInterface;
import ru.deft.homework.ioc.IoC;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        loadCompiledClassAndPrintMethods();

        PrintInterface hello = (PrintInterface) IoC.createProxyClass(PrintHello.class);
        hello.print();
        hello.print();
        hello.print();
        hello.print();

        PrintInterface bye = (PrintInterface) IoC.createProxyClass(PrintBye.class);
        bye.print();
        bye.print();

        hello.print("Josh");
        hello.print("Joshi");
        bye.print("Danny");
        bye.print("Dannys");

    }

    private static void loadCompiledClassAndPrintMethods() throws IOException {
        LogClassLoader logClassLoader = new LogClassLoader();
        Class<?> aClass = logClassLoader.defineClass("ru.deft.homework.ClassForLoad");
        System.out.println("methods: ");
        Arrays.stream(aClass.getMethods()).forEach(method -> System.out.println(method.getName()));
        System.out.println("------------------------------------");
    }
}
