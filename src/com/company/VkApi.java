package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xeon on 12.12.2014.
 */
public class VkApi {

    public static final String VKAPI_ROOT = "https://api.vk.com";
    public static final String VKAPI_APP = "method";
    public static final Object VKAPI_VERSION = 5.27;
    public HashMap<String, Object> parameters;
    private ApiQuery apiQuery; // запрос к апи
    private String currentMethodName; // имя вызываемого метода вкапи
    private Long currentId; // Маркер
    public final String JSON_ROOT = "response";
    private int queryCount;

    public VkApi() {
        parameters = new HashMap<String, Object>();
    }
    // Получение списка(ArrayList) друзей пользователя по id
    public List<Long> getFriendsIds(Long friendId, int count) {
        parameters.put("v",VKAPI_VERSION);
        parameters.put("user_id", friendId);
        parameters.put("count", count);
        currentMethodName = "friends.get";
        currentId = friendId;
        //Если кол-во запросов >= 5, поток 'засыпает' на 1 секунду
        //makeItSleep(queryCount++);
        apiQuery = new ApiQuery(VKAPI_ROOT, VKAPI_APP, currentMethodName, parameters);
        parameters.clear();
//        System.out.println(apiQuery.getQuery().toString());
        try {
            apiQuery.connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Long> friendsIds = new ArrayList<Long>();
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(apiQuery.getResponse());
            JSONObject jsonRoot = (JSONObject) jsonObject.get(JSON_ROOT);
            JSONArray jsonArray = (JSONArray) jsonRoot.get("items");
            Iterator iterator = jsonArray.iterator();
            while(iterator.hasNext())
                friendsIds.add((Long) iterator.next());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return friendsIds;
    }
    // Получение информации о пользователе по id
    public String infoID(long id) {
        parameters.put("v",VKAPI_VERSION);
        parameters.put("user_ids",id);
        parameters.put("fields","verified,city");
        currentMethodName = "users.get";
        //makeItSleep(queryCount++);
        apiQuery = new ApiQuery(VKAPI_ROOT, VKAPI_APP, currentMethodName, parameters);
        parameters.clear();
//        System.out.println(apiQuery.getQuery().toString());
        try {
            apiQuery.connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String fio = "";
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(apiQuery.getResponse());
            JSONArray preRoot = (JSONArray) jsonObject.get(JSON_ROOT);
            JSONObject jsonRoot = (JSONObject) preRoot.get(0);
            String firstName = (String) jsonRoot.get("first_name");
            String lastName = (String) jsonRoot.get("last_name");
            fio = firstName.toString()+" "+lastName.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fio;
    }

    private void makeItSleep(int query) {
        if(queryCount >= 5) {
            try {
                TimeUnit.MILLISECONDS.sleep(1010);
                queryCount = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentId) {
        this.currentId = currentId;
    }

}
