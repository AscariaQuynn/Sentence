package cz.example.sentence.utils;

public class StringUtils {

	public static String yodaTalk(String text) {
		int lastSpace = text.lastIndexOf(" ");
		return text.substring(lastSpace + 1) + " " + text.substring(0, lastSpace);
	}
}
