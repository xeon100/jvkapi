package com.company;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
//        long startUserId = 104797527;
//        int count = 40;
//        VkApi vkApi = new VkApi();
//
//        int id = 104797527;


        //Тест на макс. кол-во подключений к вк ап
        int i = 0;
        while(true){
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("user_ids",i);
            parameters.put("fields","city");
            parameters.put("v",5.27);
            ApiQuery ary = new ApiQuery("https://api.vk.com","method","users.get",parameters);
            break;
        }

//        List<Long> friendList1 = vkApi.getFriendsIds(startUserId, count);
//        System.out.println("Друзья пользователя: "+ vkApi.infoID(startUserId));
//
//        /*С информацией*/
//        for (long friendId : friendList1) {
//            System.out.println(vkApi.infoID(friendId));
//
//            for(long id : vkApi.getFriendsIds(friendId,count)){
//                System.out.println(vkApi.infoID(id));
//            }
//        }

        /*Без инфо по ID (друзья друзей)*/
//        for (long friendId : friendList1) {
//            System.out.println("Список друзей пользователя: "+ friendId);
//            for(long id : vkApi.getFriendsIds(friendId,count)){
//                System.out.println(id);
//            }
//        }
    }
}


