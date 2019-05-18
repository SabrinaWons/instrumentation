package com.example;

public class MyApp {

	public static void main(String[] args){
		System.out.println("Start the app...");

		MyService service = new MyService();
		String message = service.getMessage();

		System.out.println(message);
	}
}
