package com.xsoin.ch02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
    //設定一個初始值，為6
    static AtomicStampedReference<Integer> money = new AtomicStampedReference(6, 0);

    public static void main(String[] args) {
        //模擬後台執行緒，這3個執行緒的作用就是無限循環找出帳戶金額小於10的使用者，然後對該使用者充值20元
        for (int i = 0; i < 3; i++) {
            //取出stamp
            final int stamp = money.getStamp();
            new Thread(() -> {
                {
                    while (true) {
                        while (true) {
                            Integer m = money.getReference();
                            if (m < 10) {
                                if (money.compareAndSet(m, m + 20, stamp, stamp + 1)) {
                                    System.out.println("餘額小於十元，進行儲值,儲值後餘額=" + money.getReference() + "元");
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }).start();
        }
        //模擬使用者消費的執行緒，消費10次，每次只消費10元，金額不夠則給出提示
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                while (true) {
                    int stamp = money.getStamp();
                    Integer m = money.getReference();
                    if (m > 10) {
                        System.out.println("帳戶儲值足夠，可以消費");
                        if (money.compareAndSet(m, m - 10, stamp, stamp + 1)) {
                            System.out.println("消費成功,消費後餘額=" + money.getReference() + "元");
                            break;
                        }
                    } else {
                        System.out.println("餘額不足，餘額=" + money.getReference() + "元");
                        break;
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
