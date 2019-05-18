package com.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class MyTransformer implements ClassFileTransformer {
	public byte[] transform(ClassLoader loader,
							String className,
							Class<?> classBeingRedefined,
							ProtectionDomain protectionDomain,
							byte[] classfileBuffer){
		if(!className.equals("com/example/MyService")){
			return classfileBuffer;
		}
		byte[] byteCode = classfileBuffer;

		MyByteCodeTransformer transformer = new MyByteCodeTransformer(byteCode);
		byteCode = transformer.changeStringInConstantPool("hello", "hella");

		return byteCode;
	}
}

