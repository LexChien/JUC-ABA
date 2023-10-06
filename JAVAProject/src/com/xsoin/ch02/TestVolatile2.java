package com.xsoin.ch02;

public class TestVolatile2 {
	
	    public static void main(String[] args) {
	        myData myData=new myData();
	        for (int i = 0; i <20 ; i++) {
	            new Thread(()->{
	                for (int j = 0; j <1000 ; j++) {
	                    myData.addPlusPlus();
	                }
	            },String.valueOf(i)).start();
	        }
	        while (Thread.activeCount()>2){ 
	            Thread.yield();//當前執行緒由執行態變為就緒態讓出
	        }
	        System.out.println(myData.num);
	    }
	}

	class myData{
	     volatile int num=0;
	    public void addPlusPlus(){
	        this.num++;
	    }
	

}

