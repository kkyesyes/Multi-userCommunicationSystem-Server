package com.kk.qqserver.service;

import com.kk.qqcommon.Message;
import com.kk.qqcommon.MessageType;
import com.kk.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器，监听9999，等待客户端连接 并 保持通信
 *
 * @author KK
 * @version 1.0
 */
public class QQServer {
    private ServerSocket ss = null;

    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    static {
        // 初始化validUsers
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("沸羊羊", new User("沸羊羊", "123456"));
        validUsers.put("喜羊羊", new User("喜羊羊", "123456"));
        validUsers.put("美羊羊", new User("美羊羊", "123456"));
    }

    /**
     * 验证用户
     */
    public boolean checkUser(String userId, String passwd) {
        User user = validUsers.get(userId);
        // 用户不存在
        if (user == null) {
            return false;
        }

        // 密码错误
        if (!user.getPasswd().equals(passwd)) {
            return false;
        }

        return true;
    }

    /**
     * 服务端服务
     */
    public QQServer() {
        try {
            ss = new ServerSocket(9999);
            boolean exit = false;
            System.out.println("服务器在9999端口监听中...");

            while (!exit) {
                // 监听获取
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User) ois.readObject();

                // 回复验证消息
                ObjectOutputStream oos =
                        new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();

                // 验证用户
                if (checkUser(u.getUserId(), u.getPasswd())) {
                    // 登录成功
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);

                    // 开启线程
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(socket, u.getUserId());
                    serverConnectClientThread.start();

                    // 加入到集合
                    ManageClientThreads.addClientThread(u.getUserId(), serverConnectClientThread);
                } else {
                    // 登录失败
                    System.out.println("user = " + u.getUserId() + " pwd = " + u.getPasswd() + "登录失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);

                    // 关闭socket
                    socket.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 不再监听
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
