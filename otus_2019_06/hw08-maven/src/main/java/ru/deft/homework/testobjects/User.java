package ru.deft.homework.testobjects;

import java.util.List;
import java.util.Map;

public class User {

    private Long id;
    private String name;
    private List<String> nickNames;
    private Map<Integer, String> todos;
    private Long[] nums;

    public User() {
    }

    public User(Long id, String name, List<String> nickNames, Map<Integer, String> todos) {
        this(id, name, nickNames);
        this.todos = todos;
    }

    public User(Long id, String name, List<String> nickNames) {
        this.id = id;
        this.name = name;
        this.nickNames = nickNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(List<String> nickNames) {
        this.nickNames = nickNames;
    }

    public Map<Integer, String> getTodos() {
        return todos;
    }

    public void setTodos(Map<Integer, String> todos) {
        this.todos = todos;
    }

    public Long[] getNums() {
        return nums;
    }

    public void setNums(Long[] nums) {
        this.nums = nums;
    }
}
