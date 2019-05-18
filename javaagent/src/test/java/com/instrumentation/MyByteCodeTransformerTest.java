package com.instrumentation;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyByteCodeTransformerTest {

	@Test
	public void shouldChangeStringInConstantPool() throws IOException {

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("MyService.class");
		byte[] byteCode = inputStream.readAllBytes();

		MyByteCodeReader reader = new MyByteCodeReader(byteCode);
		reader.read();

		MyByteCodeTransformer transformer = new MyByteCodeTransformer(byteCode);
		byteCode = transformer.changeStringInConstantPool("hello", "world");

		reader = new MyByteCodeReader(byteCode);
		reader.read();


	}

}