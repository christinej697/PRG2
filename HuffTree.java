/*
 * Name: Christine Johnson
 * Date: 5/27/19
 * Program Overview: This program creates a frequency table from an input file message.
 * It also encodes the message into binary and then decodes the message
 * and sends it to an ouput file.
 * Notes: Used code from the Goodrich Data Structures and Algorithms book
 */

package prg2;

import java.util.Arrays;
import java.util.HashMap;
	import java.util.Map;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.Scanner;
	import java.io.PrintWriter;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.lang.StringBuilder;

public class HuffTree {
	
	public static void frequencyTable(String inputStr){
		
		char[] charArray = inputStr.toCharArray();
		HashMap<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
		
		//Store characters in Hashmap
		for (char c : charArray) {
			
			if (frequencyMap.containsKey(c)) {
				frequencyMap.put(c,  frequencyMap.get(c) + 1);
			}
			else {
				frequencyMap.put(c, 1);
			}
		}
		
		//Print frequency table
		System.out.println("Frequency Table");
		System.out.println("----------------------------");
		int i = 0;
		for(Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			System.out.print(entry.getKey() + " " + entry.getValue() + "      ");
			i+=1;
			if((i%3) ==0 ) {
				System.out.println();
			}
		}
		
		Map.Entry<Character, Integer> min1 = null;
		Map.Entry<Character, Integer> min2 = null;
		for(Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			if (min1 == null || min1.getValue() > entry.getValue()) {
				min1 = entry;
			}
			//System.out.println(min1);
		}
	}
	
	public static String encode(String inputStr) {
		
		StringBuilder binaryStr = new StringBuilder();
		byte[] byteArray = inputStr.getBytes();
		for (byte b : byteArray) {
			int value = b;
			for (int i = 0; i < 8; i++) {
				binaryStr.append((value & 128) == 0 ? 0 : 1);
				value <<= 1;
			}
			//binaryStr.append(' ');
		}
		System.out.println();
		System.out.println("String in binary: " + binaryStr);
		String finalString = binaryStr.toString();
		return finalString;
	}
	
	public static void decode(String inputStr) {
		StringBuilder decodedStr = new StringBuilder();
		String[] list = inputStr.split("(?<=\\G.{8})");
		
		for(int i = 0; i < list.length ; i++){
			int convert = Integer.parseInt(list[i], 2);
			String str = new Character((char)convert).toString();
			decodedStr.append(str);
		}
		
		//System.out.println("Decoded string: " + decodedStr);
		try {
			PrintWriter outputFile = new PrintWriter(new FileWriter("output.txt"));
			outputFile.println("Decoded string: " + decodedStr);
			outputFile.close();
		}catch(IOException e) {
			System.out.println("Problem finding the file for output");
		}
	}
	
	public static void main(String[] args) {
		
		String input = "";
		String encoded;
		
		try{
			Scanner sc = new Scanner(new File("input.txt"));
			input = sc.nextLine();
			System.out.println("The input text is: " + input);
			System.out.println();
			sc.close();
		}catch(FileNotFoundException exc) {
			System.out.println("File not found");
		}
		
		frequencyTable(input);
		encoded = encode(input);
		decode(encoded);
	}

}
