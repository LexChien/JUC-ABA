package com.xsoin.ch02;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ABADemo {
    // 表示記憶體裡初始值是100,版本好號為1
  private static AtomicStampedReference atomicStampedReference=new AtomicStampedReference(100,1);
    public static void main(String[] args) {
        new Thread(()->{
            // 獲得版本號
            int stamp = atomicStampedReference.getStamp();
            // 這裡延遲一秒不是為了說為了執行緒阻塞，而是為了讓執行緒B得到版本號
            System.out.println(Thread.currentThread().getName()+"\t第一次版本號："+stamp);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            // 期望值為100 和記憶體中的值進行比較，如果一樣，且版本號stamp也和記憶體中一樣，則改為50
            atomicStampedReference.compareAndSet(100,50,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t第二次版本號："+atomicStampedReference.getStamp());
//            atomicStampedReference.compareAndSet(100,50,stamp,stamp+1);
        },"A").start();

        new Thread(()->{
            // 獲得版本號
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本號："+stamp);
            // 執行緒阻塞
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            // 期望值為100 和記憶體中的值進行比較，如果一樣，且版本號stamp也和記憶體中一樣，則改為50
            boolean result = atomicStampedReference.compareAndSet(100, 50, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"\t修改是否成功"+result+"\t當前實際版本號："+atomicStampedReference.getStamp());
            System.out.println("當前實際最新值："+atomicStampedReference.getReference());
        },"B").start();

        new Thread(()->{
            // 這裡延遲兩秒不是為了說為了執行緒阻塞，而是為了讓執行緒A 執行完畢
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            // 獲得版本號
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本號："+stamp);
            // 期望值為100 和記憶體中的值進行比較，如果一樣，且版本號stamp也和記憶體中一樣，則改為50
            atomicStampedReference.compareAndSet(50,100,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t第二次版本號："+atomicStampedReference.getStamp());
        },"C").start();
    }
}
