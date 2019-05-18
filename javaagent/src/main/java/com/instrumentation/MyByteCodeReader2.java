package com.instrumentation;

import java.util.HashMap;
import java.util.Map;

public class MyByteCodeReader2 {

	private final byte[] byteCode;

	private int index = 0;

	private Map<Integer, String> constantPool = new HashMap<>();

	public MyByteCodeReader2(byte[] byteCode) {
		this.byteCode = byteCode;
	}

	public void read(){

		getMagicNumber();
		getVersion();
		getConstantPool();
		getAccessFlags();
		getThisClass();
		getSuperClass();
		getInterfacesCount();
		getFieldsCount();
		getMethods();
		getAttributes();

	}

	private void getAttributes(){
		String size = getHexFromByteOfLength(2);
		System.out.println(size + "\t//Attributes count");
		getAttribute();

	}

	private void getMethods(){
		String size = getHexFromByteOfLength(2);
		System.out.println(size + "\t//Methods count");
		int _size = getIntFromHex(size);

		for(int i = 0; i<2; i++){
			System.out.println(getHexFromByteOfLength(2) + "\t//  Access flags");
			System.out.println(getHexFromByteOfLength(2) + "\t//  Name index " );
			System.out.println(getHexFromByteOfLength(2) + "\t//  Type index ");
			String attributeCount = getHexFromByteOfLength(2);
			System.out.println(attributeCount + "\t//  Attribute count ");
			int _attributeCount = getIntFromHex(attributeCount);
			for(int j = 0; j<_attributeCount; j++){
				getAttribute();
			}
		}

	}

	private void getAttribute(){
		String index = getHexFromByteOfLength(2);
		int _index = getIntFromHex(index);
		String word = constantPool.get(_index);
		System.out.println(index + "\t//    Name index " + "(" + word + ")");
		System.out.println(getHexFromByteOfLength(4) + "\t//    Attribute length ");

		switch(word){
			case "Code" :
				System.out.println(getHexFromByteOfLength(2) + "\t//    max_stack ");
				System.out.println(getHexFromByteOfLength(2) + "\t//    max_locals ");
				String codeLength = getHexFromByteOfLength(4);
				int _codeLength = getIntFromHex(codeLength);
				System.out.println(codeLength + "\t//    code_length ");
				String code = getHexFromByteOfLength(_codeLength);
				System.out.println(code + "\t//    code");
				getCode(code);
				System.out.println(getHexFromByteOfLength(2) + "\t//    exception_table_length");
				String attributeCount = getHexFromByteOfLength(2);
				int _attributeCount = getIntFromHex(attributeCount);
				System.out.println(attributeCount + "    attributes_count" );
				for(int i = 0; i<_attributeCount; i++){
					getAttribute();
				}

				break;
			case "LineNumberTable" :
				System.out.println(getHexFromByteOfLength(2) + "\t//    line_number_table_length");
				System.out.println(getHexFromByteOfLength(2) + "\t//    start_pc " );
				System.out.println(getHexFromByteOfLength(2) + "\t//    line_number " );
				break;

			case "LocalVariableTable" :
				System.out.println(getHexFromByteOfLength(2) + "\t//    local_variable_table_length" );
				System.out.println(getHexFromByteOfLength(2) + "\t//    start_pc ");
				System.out.println(getHexFromByteOfLength(2) + "\t//    length " );
				System.out.println(getHexFromByteOfLength(2) + "\t//    name_index ");
				System.out.println(getHexFromByteOfLength(2) + "\t//    descriptor_index ");
				System.out.println(getHexFromByteOfLength(2) + "\t//    index " );
				break;
			case "SourceFile" :
				System.out.println(getHexFromByteOfLength(2) + "\t//    sourcefile_index " );
				break;
		}

	}

	private void getCode(String code){
		String[] instructions = code.split(" ");
		for(int i = 0; i<instructions.length; i++){
			String instruction = instructions[i];
			switch(instruction){
				case "2A" :
					System.out.println("      " + instruction + " -> load 0");
					break;
				case "B7" :
					System.out.println("      " + instruction + " -> invokespecial " + instructions[++i] + " " + instructions[++i]);
					break;
				case "B1" :
					System.out.println("      " + instruction + " -> return");
					break;
				case "B0" :
					System.out.println("      " + instruction + " -> areturn");
					break;
				case "12" :
					System.out.println("      " + instruction + " -> ldc " + instructions[++i] );
					break;

			}
		}
	}

	private void getFieldsCount(){
		String hex = getHexFromByteOfLength(2);
		System.out.println(hex + "\t//Fields count");
	}

	private void getInterfacesCount(){
		String hex = getHexFromByteOfLength(2);
		System.out.println(hex + "\t//Interfaces count");
	}

	private void getSuperClass(){
		String hex = getHexFromByteOfLength(2);
		System.out.println(hex + "\t//Super class");
	}

	private void getThisClass(){
		String hex = getHexFromByteOfLength(2);
		System.out.println(hex + "\t//This class");
	}

	private void getAccessFlags(){
		String hex = getHexFromByteOfLength(2);
		System.out.println(hex + "\t//Access flags");
	}

	private void getConstantPool(){
		String constantPoolSize = getHexFromByteOfLength(2);
		System.out.println(constantPoolSize + "\t//Constant Pool size" );

		int _constantPoolSize = getIntFromHex(constantPoolSize);
		for (int i = 1; i<_constantPoolSize; i++){
			String tag = getHexFromByteOfLength(1);
			int _tag = getIntFromHex(tag);
			switch (_tag) {
				case 10:
					String hex = getHexFromByteOfLength(4);
					System.out.println(tag + hex + "\t//  #" + i + " : Method ref ");
					break;
				case 8:
					hex = getHexFromByteOfLength(2);
					System.out.println(tag + hex + "\t//  #" + i + " : String ");
					break;
				case 7:
					hex = getHexFromByteOfLength(2);
					System.out.println(tag + hex + "\t//  #" + i + " : Class ");
					break;
				case 1:
					String length = getHexFromByteOfLength(2);
					int _length = getIntFromHex(length);
					hex = getHexFromByteOfLength(_length);
					String word = getAsciiFromHex(hex);
					System.out.println(tag + length + hex + "\t//  #" + i + " : Utf8 " + "(" + word + ")");
					constantPool.put(i, word);
					break;
				case 12:
					hex = getHexFromByteOfLength(4);
					System.out.println(tag + hex + "\t//  #" + i + " : Name and type ");
					break;
			}

		}

	}


	private void getVersion(){
		String hex = getHexFromByteOfLength(4);
		System.out.println(hex + "\t//Version ");
	}

	private void getMagicNumber(){
		String hex = getHexFromByteOfLength(4);
		System.out.println(hex + "\t//Magic number" );
	}

	private String getHexFromByteOfLength(int length){
		String hex = "";
		for(int i = 0; i<length; i++){
			hex += String.format("%02X ", byteCode[index++]);
		}
		return hex;
	}

	private int getIntFromHex(String hex){
		return Integer.parseInt(hex.replaceAll(" ", ""), 16);
	}

	private String getAsciiFromHex(String hex){
		String ascii = "";
		String[] letters = hex.split(" ");
		for(String letter : letters){
			char c = (char)Integer.parseInt(letter, 16);
			ascii += c;
		}
		return ascii;
	}
}
