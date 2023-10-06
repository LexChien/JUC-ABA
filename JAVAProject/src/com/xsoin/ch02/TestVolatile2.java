package com.xsoin.ch02;

public class TestVolatile2 {
	public static void main(String[] args) {
	    A a = new A(3);
	    }
	}

	class A extends B { // type A is already defined, A has a red underline
	    public A (int t) {
	    System.out.println("A's constructor is invoked");
	    }
	}

	class B { // type B is already defined, B has a red underline
	    public B () {
	        System.out.println("B's constructor is invoked");
	    }  
}

