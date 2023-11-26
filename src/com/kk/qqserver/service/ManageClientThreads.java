package com.kk.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

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
     * 根据userId取得对应的线程
     */
    public static ServerConnectClientThread getClientThread(String userId) {
        return hm.get(userId);
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