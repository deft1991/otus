package ru.deft.homework;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.deft.homework.testobjects.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class JsonWriterImplTest {

    private static User testUser;
    private static JsonWriter jsonWriter;

    @BeforeAll public static void init() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("GreenGo");
        testUser.setNickNames(new ArrayList<>(Arrays.asList("qqq", "www", "eee")));
        jsonWriter = new JsonWriterImpl();
    }

    @Test void writeToJsonWithList() throws IllegalAccessException {
        String userJson = jsonWriter.writeToJson(testUser);
        Gson gson = new Gson();
        User userFromJson = gson.fromJson(userJson, testUser.getClass());
        assertEquals(testUser.getId(), userFromJson.getId());
        assertEquals(testUser.getName(), userFromJson.getName());
        for (int i = 0; i < testUser.getNickNames().size(); i++) {
            assertEquals(testUser.getNickNames().get(i), userFromJson.getNickNames().get(i));
        }
    }

    @Test void writeToJsonWithMap() throws IllegalAccessException {
        Map<Integer, String> todos = new HashMap<>();
        todos.put(1, "do somth");
        todos.put(2, "do anything");
        todos.put(3, "hoh hoh");
        todos.put(4, "piu piu");
        testUser.setTodos(todos);

        String userJson = jsonWriter.writeToJson(testUser);
        Gson gson = new Gson();
        User userFromJson = gson.fromJson(userJson, testUser.getClass());
        assertEquals(testUser.getId(), userFromJson.getId());
        assertEquals(testUser.getName(), userFromJson.getName());
        for (Integer key : testUser.getTodos().keySet()) {
            assertEquals(testUser.getTodos().get(key), userFromJson.getTodos().get(key));
        }
    }

    @Test void writeToJsonWithArray() throws IllegalAccessException {
        Long[] nums = new Long[]{1L,2L,3L,4L,5L};
        testUser.setNums(nums);

        String userJson = jsonWriter.writeToJson(testUser);
        Gson gson = new Gson();
        User userFromJson = gson.fromJson(userJson, testUser.getClass());
        assertEquals(testUser.getId(), userFromJson.getId());
        assertEquals(testUser.getName(), userFromJson.getName());
        for (int i = 0; i< testUser.getNums().length; i++) {
            assertEquals(testUser.getNums()[i], userFromJson.getNums()[i]);
        }
    }
}
