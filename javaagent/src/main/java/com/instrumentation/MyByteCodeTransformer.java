package com.instrumentation;

/**
 * This class takes a bytecode array
 * Looks for the constant pool inside the bytecode
 * And changes the spelling of a String with another String of same length.
 */
public class MyByteCodeTransformer {

	byte[] byteCode;

	int index = 8;

	public MyByteCodeTransformer(byte[] byteCode){
		this.byteCode = byteCode;
	}

	public byte[] changeStringInConstantPool(String source, String target){
		String constantPoolSize = getHexFromByteOfLength(2);
		int _constantPoolSize = getIntFromHex(constantPoolSize);
		for (int i = 1; i<_constantPoolSize; i++){
			String tag = getHexFromByteOfLength(1);
			int _tag = getIntFromHex(tag);
			switch (_tag) {
				case 10:
				case 12:
					index = index + 4;
					break;
				case 8:
				case 7:
					index = index + 2;
					break;
				case 1:
					String length = getHexFromByteOfLength(2);
					int _length = getIntFromHex(length);
					String hex = getHexFromByteOfLength(_length);
					String word = getAsciiFromHex(hex);
					if(word.equals(source)){
						index = index - _length;
						for(int j =0; j<_length; j++){
							byteCode[index++] = (byte)target.charAt(j);
						}
					}
					break;
			}

		}

		return byteCode;

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
