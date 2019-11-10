package ru.deft.homework.processor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/8/19
 */
public class TemplateProcessor {
    private static final String HTML_DIR = "/tml/";
    private final Configuration configuration;


    public TemplateProcessor() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), HTML_DIR);
        configuration.setDefaultEncoding("UTF-8");
    }

    public TemplateProcessor(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getPage(String fileName, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(fileName);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPage(String fileName) throws IOException {
        Template template = configuration.getTemplate(fileName);
        return template.toString();
    }
}
