package com.mylo.betterrichpresense.animatedtext;

import java.awt.Toolkit;
import java.sql.Time;

public class animatedtext {
	public static String animatetext(String text, animtype type, long speed){
		long offset = (long) ((System.currentTimeMillis() / (1000 / speed)));
		offset = offset % (text.length());
		String output = "";
		switch(type) {
		case SCROLL:
			text = text + " ";
			output = scroll(text, (int)offset);
		break;
		case REMOVEEND:
			offset = (long) ((System.currentTimeMillis() / (1000 / speed)));
			offset = offset % (text.length()*2);
			output = removeend(text, (int)offset);
		break;
		case CLOCK:
			output = clock(text);
		break;
		}
		return output;
	}
	
	public static String scroll(String s, int times) {
		char[] arr = s.toCharArray();
		arr = reversearray(arr);
		for(int i = 0; i < times; i++){ 
		char last;
		int j;
        last = arr[arr.length-1];    
        
        for(j = arr.length-1; j > 0; j--) {
            arr[j] = arr[j-1];    
        }
        arr[0] = last;
		}
		arr = reversearray(arr);
        return charrarrtostring(arr);
	}
	
	public static String clock(String s) {
		return s + " " + new Time(System.currentTimeMillis());
	}
	
	public static String removeend(String s, int amount) {
		int totalsize = s.length();
		String output = s;
		if(amount > totalsize) {
			amount -= totalsize;
			amount = totalsize - amount;
		}
		
		for(int i = 1; i < amount; i++) {
			output = removeLastChar(output);
		}
		
		if(output.toString() == "") {
			output = s.charAt(0) + "";
		}
		
		return output;
	}
	
	public static String charrarrtostring(char[] chars) {
		String string = "";
		for(char c: chars) {
			string = string + c;
		}
		return string;
	}
	
	public static char[] reversearray(char[] array) {
		char[] outputarr = array;
		String input = new String(array);
		String reverse = "";
        
        
        for(int i = input.length() - 1; i >= 0; i--)
        {
            reverse = reverse + input.charAt(i);
        }
		return reverse.toCharArray();
	}
	
	public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
}
