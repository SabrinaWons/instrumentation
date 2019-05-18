package com.instrumentation;

import org.junit.Test;

import java.io.InputStream;

public class MyByteCodeReader2Test {

	@Test
	public void shouldRead() throws Exception {


		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("MyService.class");
		byte[] byteCode = inputStream.readAllBytes();


		MyByteCodeReader2 reader = new MyByteCodeReader2(byteCode);
		reader.read();

	}

}