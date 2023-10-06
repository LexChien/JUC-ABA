package com.xsoin.ch02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TestAtomic {
    public static void main(String[] args) {
        AtomicReference<User> atomicUser=new AtomicReference<>();
        User zhangsan=new User("張三",21);
        User lisi =new User("李四",25);
        atomicUser.set(zhangsan);
        System.out.println(atomicUser.compareAndSet(zhangsan,lisi)+"\t"+atomicUser.get().toString());
    }
}

class User{
    private String username;
    private int age;

    public User(String username,int age){
        this.username=username;
        this.age=age;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
