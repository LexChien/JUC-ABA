package com.xsoin.ch02;

public class synchronizedTest {
	  public static void main(String[] args) {
	        ThreadDemo td = new ThreadDemo();
	        new Thread(td).start();
	        while (true) {
	            synchronized (td) {  // 同步鎖，可以解決，因為每次都可以刷新快取
	                if (td.isFlag()) {
	                    System.out.println("------------------");
	                    break;
	                }
	            }
	        }
	    }
}
