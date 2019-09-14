package ru.deft.homework;

import com.google.gson.Gson;
import org.json.simple.parser.ParseException;
import ru.deft.homework.testobjects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, ParseException {

        List<String> nickNames = new ArrayList<>();
        nickNames.add("qqq");
        nickNames.add("www");
        nickNames.add("eee");
        Map<Integer, String> todos = new HashMap<>();
        todos.put(1, "do somth");
        todos.put(2, "do anything");
        todos.put(3, "hoh hoh");
        todos.put(4, "piu piu");
        Long[] nums = new Long[]{1L,2L,3L,4L,5L};
        User user = new User(1L, "Greengo", nickNames, todos);
        user.setNums(nums);
        JsonWriterImpl jsonWriter = new JsonWriterImpl();
        String jsonString = jsonWriter.writeToJson(user);
        Gson gson = new Gson();
        User user1 = gson.fromJson(jsonString, User.class);
        System.out.println(user1.getId() + " " + user1.getTodos());

    }

}
