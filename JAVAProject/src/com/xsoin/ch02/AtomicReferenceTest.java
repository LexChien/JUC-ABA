package com.xsoin.ch02;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    //設定一個初始值，為6
    static AtomicReference<Integer> money = new AtomicReference(6);


    public static void main(String[] args) {
        //設定一個初始值，為6
        money.set(6);
        //模擬後台執行緒，這3個執行緒的作用就是無限循環找出帳戶金額小於10的使用者，然後對該使用者充值20元
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                {
                    while (true) {
                        while (true) {
                            Integer m = money.get();
                            if (m < 10) {
                                if (money.compareAndSet(m, m + 20)) {
                                    System.out.println( + money.get() );
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
                    Integer m = money.get();
                    if (m > 10) {
                        System.out.println("Yes");
                        if (money.compareAndSet(m, m - 10)) {
                            System.out.println( money.get());
                            break;
                        }
                    } else {
                        System.out.println( money.get());
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
