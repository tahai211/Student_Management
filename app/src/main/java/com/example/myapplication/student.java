package com.example.myapplication;

import android.os.Bundle;

import java.io.Serializable;

public class student implements Serializable {
   private String user, password, email, name, phone, picture,lastMessage,chatKey;
   private int unseenMessages;

   public student(String user, String password, String email, String name, String phone, String picture,String lastMessage, int unseenMessages, String chatKey) {
      this.user = user;
      this.password = password;
      this.email = email;
      this.name = name;
      this.phone = phone;
      this.picture = picture;
      this.lastMessage = lastMessage;
      this.unseenMessages = unseenMessages;
      this.chatKey = chatKey;
   }

   public String getUser() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getPicture() {
      return picture;
   }

   public void setPicture(String picture) {
      this.picture = picture;
   }

   public String getLastMessage() {
      return lastMessage;
   }

   public void setLastMessage(String lastMessage) {
      this.lastMessage = lastMessage;
   }

   public int getUnseenMessages() {
      return unseenMessages;
   }

   public void setUnseenMessages(int unseenMessages) {
      this.unseenMessages = unseenMessages;
   }

   public String getChatKey() {
      return chatKey;
   }


   @Override
   public String toString() {
      return "student{" +
              "user='" + user + '\'' +
              ", password='" + password + '\'' +
              ", email='" + email + '\'' +
              ", name='" + name + '\'' +
              ", phone='" + phone + '\'' +
              ", picture='" + picture + '\'' +
              ", lastMessage='" + lastMessage + '\'' +
              ", unseenMessages=" + unseenMessages +
              '}';
   }
}