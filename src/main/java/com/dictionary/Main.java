package com.dictionary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		JSONObject obj = (JSONObject) parser.parse(new FileReader("data.json"));
		
		Iterator it = obj.entrySet().iterator();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the word:");
		String word = sc.nextLine();
		
		String result = getMeaning(obj, word);
		
		if (result.equals("")) {
			result = getPossibleOutcomes(obj, word);
			
			if (result.equals("")) {
				System.out.println("Word '" + word + "' doesn't exist");
			} else {
				System.out.println(getMeaning(obj, result));
			}
		} else {
			System.out.println(result);
		}
	}
	
	public static String getPossibleOutcomes(JSONObject obj, String word) {
		Iterator it = obj.keySet().iterator();
		List<String> list = new ArrayList<>();
		
		while (it.hasNext()) {
			String w = it.next().toString();
			int diff = StringUtils.getLevenshteinDistance(word, w);
			if (diff == 1) {
				list.add(w);
			}
		}
		
		if (list.size() > 0) {
			System.out.println("We couldn't find word '" + word + "' , but we found similar words so check suggestions below");
			for (String w: list) {
				System.out.println(w);
			}
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please choose word from above suggestions or press enter to quit");
		
		return sc.nextLine();
	}
	
	public static String getMeaning(JSONObject obj, String word) {
		Iterator it = obj.keySet().iterator();
		
		while (it.hasNext()) {
			String w = it.next().toString();
			
			if (word.equals(w)) {
				return obj.get(w).toString();
			}
		}
		
		return "";
	}
}
