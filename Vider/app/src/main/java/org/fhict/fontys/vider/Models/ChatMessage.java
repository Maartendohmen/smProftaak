package org.fhict.fontys.vider.Models;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageReceiver;
    private String messageSender;
    private long messageTime;

    public ChatMessage(String messageText, String messageReceiver, String messageSender) {
        this.messageText = messageText;
        this.messageReceiver = messageReceiver;
        this.messageSender = messageSender;

        //Initialize time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageReceiver() {
        return messageReceiver;
    }

    public void setMessageReceiver(String messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    static class ChatMessageComparator implements Comparator<ChatMessage>
    {

        public int compare(ChatMessage o1, ChatMessage o2) {
            long time1 = o1.getMessageTime();
            long time2 = o2.getMessageTime();

            if(time1 == time2){
                return 0;
            }
            else if(time1 <= time2){
                return 1;
            }
            else if(time2 <= time1){
                return -1;
            }
            return 0;
        }
    }

}
