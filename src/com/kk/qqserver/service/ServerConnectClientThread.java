package com.kk.qqserver.service;

import com.kk.qqcommon.Message;
import com.kk.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 与客户端保持通信
 *
 * @author KK
 * @version 1.0
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("服务端与客户端" + userId + "保持通信中...");
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message) ois.readObject();
                // 使用message
                if (ms.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    System.out.println("客户端 " + userId + " 请求拉取用户在线列表");
                    Message message = new Message();
                    message.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    String onlineFriends = ManageClientThreads.getOnlineFriends();
                    message.setContent(onlineFriends);
                    message.setGetter(ms.getSender());

                    // 得到输出流
                    ObjectOutputStream oos =
                            new ObjectOutputStream(socket.getOutputStream());
                    // 写入数据
                    oos.writeObject(message);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
