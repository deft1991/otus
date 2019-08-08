package ru.deft.homework.classLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LogClassLoader extends ClassLoader {

    public Class<?> defineClass(String className) throws IOException {
        File file = new File(getFileName(className));
        byte[] byteCode = Files.readAllBytes(file.toPath());
        return super.defineClass(className, byteCode, 0, byteCode.length);
    }

    private String getFileName(String className) {
        return "compiledClass" + File.separator + className.substring(className.lastIndexOf(".") + 1) + ".class";
    }

}
