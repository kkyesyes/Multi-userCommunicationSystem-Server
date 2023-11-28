package com.kk.qqserver.service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理客户端线程
 *
 * @author KK
 * @version 1.0
 */
public class ManageClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    /**
     * 将客户端线程加入到服务端集合中
     */
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    /**
     * 将客户端线程从服务端集合中移除
     */
    public static void deleteClientThread(String userId) {
        hm.remove(userId);
    }

    /**
     * 根据userId取得对应的线程
     */
    public static ServerConnectClientThread getClientThread(String userId) {
        return hm.get(userId);
    }

    /**
     * 取得所有在线用户对应的线程
     */
    public static Set<String> getAllClientThread(String sender) {
        Set<String> getters = hm.keySet().stream()
                .filter(element -> !element.equals(sender))
                .collect(Collectors.toSet());
        return getters;
    }

    /**
     * 获取在线用户ID
     */
    public static String getOnlineFriends() {
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUsersList = "";
        while (iterator.hasNext()) {
            onlineUsersList += iterator.next().toString() + " ";
        }
        return onlineUsersList;
    }
}
