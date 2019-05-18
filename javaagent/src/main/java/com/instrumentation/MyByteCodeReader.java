package com.instrumentation;

import java.util.HashMap;
import java.util.Map;

public class MyByteCodeReader {

	private final byte[] byteCode;

	private int index = 0;

	private Map<Integer, String> constantPool = new HashMap<>();

	public MyByteCodeReader(byte[] byteCode) {
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
		System.out.println("Attributes count : " + size);
		getAttribute();

	}

	private void getMethods(){
		String size = getHexFromByteOfLength(2);
		System.out.println("Methods count : " + size);
		int _size = getIntFromHex(size);

		for(int i = 0; i<2; i++){
			System.out.println("  Access flags : " + getHexFromByteOfLength(2));
			System.out.println("  Name index : " + getHexFromByteOfLength(2));
			System.out.println("  Type index : " + getHexFromByteOfLength(2));
			String attributeCount = getHexFromByteOfLength(2);
			System.out.println("  Attribute count : " + attributeCount);
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
		System.out.println("    Name index : " + index + "(" + word + ")");
		System.out.println("    Attribute length : " + getHexFromByteOfLength(4));

		switch(word){
			case "Code" :
				System.out.println("    max_stack : " + getHexFromByteOfLength(2));
				System.out.println("    max_locals : " + getHexFromByteOfLength(2));
				String codeLength = getHexFromByteOfLength(4);
				int _codeLength = getIntFromHex(codeLength);
				System.out.println("    code_length : " + codeLength);
				String code = getHexFromByteOfLength(_codeLength);
				System.out.println("    code : " + code);
				getCode(code);
				System.out.println("    exception_table_length : " + getHexFromByteOfLength(2));
				String attributeCount = getHexFromByteOfLength(2);
				int _attributeCount = getIntFromHex(attributeCount);
				System.out.println("    attributes_count : " + attributeCount);
				for(int i = 0; i<_attributeCount; i++){
					getAttribute();
				}

				break;
			case "LineNumberTable" :
				System.out.println("    line_number_table_length : " + getHexFromByteOfLength(2));
				System.out.println("    start_pc : " + getHexFromByteOfLength(2));
				System.out.println("    line_number : " + getHexFromByteOfLength(2));
				break;

			case "LocalVariableTable" :
				System.out.println("    local_variable_table_length : " + getHexFromByteOfLength(2));
				System.out.println("    start_pc : " + getHexFromByteOfLength(2));
				System.out.println("    length : " + getHexFromByteOfLength(2));
				System.out.println("    name_index : " + getHexFromByteOfLength(2));
				System.out.println("    descriptor_index : " + getHexFromByteOfLength(2));
				System.out.println("    index : " + getHexFromByteOfLength(2));
				break;
			case "SourceFile" :
				System.out.println("    sourcefile_index : " + getHexFromByteOfLength(2));
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
		System.out.println("Fields count : " + hex);
	}

	private void getInterfacesCount(){
		String hex = getHexFromByteOfLength(2);
		System.out.println("Interfaces count : " + hex);
	}

	private void getSuperClass(){
		String hex = getHexFromByteOfLength(2);
		System.out.println("Super class : " + hex);
	}

	private void getThisClass(){
		String hex = getHexFromByteOfLength(2);
		System.out.println("This class : " + hex);
	}

	private void getAccessFlags(){
		String hex = getHexFromByteOfLength(2);
		System.out.println("Access flags : " + hex);
	}

	private void getConstantPool(){
		String constantPoolSize = getHexFromByteOfLength(2);
		System.out.println("Constant Pool size : " + constantPoolSize);

		int _constantPoolSize = getIntFromHex(constantPoolSize);
		for (int i = 1; i<_constantPoolSize; i++){
			String tag = getHexFromByteOfLength(1);
			int _tag = getIntFromHex(tag);
			switch (_tag) {
				case 10:
					String hex = getHexFromByteOfLength(4);
					System.out.println("  #" + i + " : Method ref " + tag + hex);
					break;
				case 8:
					hex = getHexFromByteOfLength(2);
					System.out.println("  #" + i + " : String " + tag + hex);
					break;
				case 7:
					hex = getHexFromByteOfLength(2);
					System.out.println("  #" + i + " : Class " + tag + hex);
					break;
				case 1:
					String length = getHexFromByteOfLength(2);
					int _length = getIntFromHex(length);
					hex = getHexFromByteOfLength(_length);
					String word = getAsciiFromHex(hex);
					System.out.println("  #" + i + " : Utf8 " + tag + length + hex + "(" + word + ")");
					constantPool.put(i, word);
					break;
				case 12:
					hex = getHexFromByteOfLength(4);
					System.out.println("  #" + i + " : Name and type " + tag + hex);
					break;
			}

		}

	}


	private void getVersion(){
		String hex = getHexFromByteOfLength(4);
		System.out.println("Version : " + hex);
	}

	private void getMagicNumber(){
		String hex = getHexFromByteOfLength(4);
		System.out.println("Magic number : " + hex);
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
