package com.xsoin.ch02;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TestVolatile1 {
    public static void main(String[] args) {
        myData myData=new myData();
        for (int i = 0; i <20 ; i++) {
            new Thread(()->{
                for (int j = 0; j <1000 ; j++) {
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount()>2){ //java最起碼有一個main執行緒，和一個垃圾回收執行緒
            Thread.yield();//當前執行緒由執行態變為就緒態，讓出cpu
        }
        System.out.println(Thread.currentThread().getName()+"\t" +myData.atomicInteger);
    }
}

class myData{
    AtomicInteger atomicInteger=new AtomicInteger();  // 不用賦值，默認就是0
    public void addAtomic(){
        atomicInteger.getAndIncrement();// 表示i++
    }
}
