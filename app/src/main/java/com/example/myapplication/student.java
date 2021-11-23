package com.example.myapplication;

import android.os.Bundle;

import java.io.Serializable;

public class student implements Serializable {
   private String user, password, email, name, phone, picture;

   public student(String user, String password, String email, String name, String phone, String picture) {
      this.user = user;
      this.password = password;
      this.email = email;
      this.name = name;
      this.phone = phone;
      this.picture = picture;
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

   @Override
   public String toString() {
      return "student{" +
              "user='" + user + '\'' +
              ", password='" + password + '\'' +
              ", email='" + email + '\'' +
              ", name='" + name + '\'' +
              ", phone='" + phone + '\'' +
              ", picture='" + picture + '\'' +
              '}';
   }
}