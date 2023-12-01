package com.kk.qqcommon;

import java.io.Serializable;

/**
 * 表示客户/服务端通信时的消息对象
 *
 * @author KK
 * @version 1.0
 */
public class Message implements Serializable {
    public static final long serialVersionUID = 1L;

    private String sender;
    private String getter;
    private String content;
    private String sendTime;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    private String mesType;
}
