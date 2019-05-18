package com.instrumentation;

import java.lang.instrument.Instrumentation;

public class MyJavaAgent {

	public static void premain(String agentArguments, Instrumentation instrumentation) throws Exception {
		System.out.println("In the premain....");
		Class clazz = Class.forName("com.example.MyService");
		MyTransformer transformer = new MyTransformer();
		instrumentation.addTransformer(transformer, true);
		instrumentation.retransformClasses(clazz);
	}

	public static void agentmain(String agentArguments, Instrumentation ininstrumentationst) {
		System.out.println("In the agentmain....");
	}
}
