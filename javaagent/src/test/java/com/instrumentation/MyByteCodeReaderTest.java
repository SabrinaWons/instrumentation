package com.instrumentation;

import org.junit.Test;

import java.io.InputStream;

public class MyByteCodeReaderTest {

	@Test
	public void shouldRead() throws Exception {


		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("MyService.class");
		byte[] byteCode = inputStream.readAllBytes();


		MyByteCodeReader reader = new MyByteCodeReader(byteCode);
		reader.read();

	}

}