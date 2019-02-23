package converter;

import java.util.HashMap;
import java.util.Map;

public class MorseCodeTable {
	
	final public static Map<Character, String> table = new HashMap<>();
	
	final public static Map<String, Character> table_ = new HashMap<>();
	
	static {
		table.put('A', ".-");
		table.put('B', "-...");
		table.put('C', "-.-.");
		table.put('D', "-..");
		table.put('E', ".");
		table.put('F', "..-.");
		table.put('G', "--.");
		table.put('H', "....");
		table.put('I', "..");
		table.put('J', ".---");
		table.put('K', "-.-");
		table.put('L', ".-..");
		table.put('M', "--");
		table.put('N', "-.");
		table.put('O', "---");
		table.put('P', ".--.");
		table.put('Q', "--.-");
		table.put('R', ".-.");
		table.put('S', "...");
		table.put('T', "-");
		table.put('U', "..-");
		table.put('V', "...-");
		table.put('W', ".--");
		table.put('X', "-..-");
		table.put('Y', "-.--");
		table.put('Z', "--..");
		table.put('0', "-----");
		table.put('1', ".----");
		table.put('2', "..---");
		table.put('3', "...--");
		table.put('4', "....-");
		table.put('5', ".....");
		table.put('6', "-....");
		table.put('7', "--...");
		table.put('8', "---..");
		table.put('9', "----.");
		table.put(',', "--..--");
		table.put('.', ".-.-.-");
		table.put('?', "..--..");
		table.put('!', "-.-.--");
		table.put('\'', ".----.");
		table.put('\"', ".-..-.");
		table.put('=', "-...-");
		table.put(':', "---...");
		table.put(';', "-.-.-.");
		table.put('(', "-.--.");
		table.put(')', "-.--.-");
		
		table_.put(".-", 'A');
		table_.put("-...", 'B');
		table_.put("-.-.", 'C');
		table_.put("-..", 'D');
		table_.put(".", 'E');
		table_.put("..-.", 'F');
		table_.put("--.", 'G');
		table_.put("....", 'H');
		table_.put("..", 'I');
		table_.put(".---", 'J');
		table_.put("-.-", 'K');
		table_.put(".-..", 'L');
		table_.put("--", 'M');
		table_.put("-.", 'N');
		table_.put("---", 'O');
		table_.put(".--.", 'P');
		table_.put("--.-", 'Q');
		table_.put(".-.", 'R');
		table_.put("...", 'S');
		table_.put("-", 'T');
		table_.put("..-", 'U');
		table_.put("...-", 'V');
		table_.put(".--", 'W');
		table_.put("-..-", 'X');
		table_.put("-.--", 'Y');
		table_.put("--..", 'Z');
		table_.put("-.-.--", '!');
		table_.put(".-..-.", '"');
		table_.put(".----.", '\'');
		table_.put("-.--.", '(');
		table_.put("-.--.-", ')');
		table_.put("--..--", ',');
		table_.put(".-.-.-", '.');
		table_.put("-----", '0');
		table_.put(".----", '1');
		table_.put("..---", '2');
		table_.put("...--", '3');
		table_.put("....-", '4');
		table_.put(".....", '5');
		table_.put("-....", '6');
		table_.put("--...", '7');
		table_.put("---..", '8');
		table_.put("----.", '9');
		table_.put("---...", ':');
		table_.put("-.-.-.", ';');
		table_.put("-...-", '=');
		table_.put("..--..", '?');
	}
}
