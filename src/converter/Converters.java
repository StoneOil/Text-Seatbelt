package converter;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Converters {
	
	final public static List<Character> Digits = Arrays.asList(new Character[] {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	});
	
	final public static List<Character> Base32Chars = Arrays.asList(new Character[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '7',
	});
	
	final public static List<Character> Base32Chars_ = Arrays.asList(new Character[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', '2', '3', '4', '5', '6', '7',
	});
	
	/**
	 * 文字编码转换
	 */
	public static byte[] transcoding(byte[] in, String to) throws UnsupportedEncodingException {
		return new String(in).getBytes(to);
	}
	
	/**
	 * 文字编码转换
	 */
	public static byte[] transcoding(byte[] in, String from, String to) throws UnsupportedEncodingException {
		return new String(in, from).getBytes(to);
	}
	
	public static String bytesToBin(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
		for(byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				builder.append((b & 128) > 0 ? '1' : '0');
				b <<= 1;
			}
		}
		
		return builder.toString();
	}
	
	public static byte[] binToBytes(String bin) throws IllegalArgumentException {
		if (bin.length() % 8 != 0) {
			throw new IllegalArgumentException();
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(bin.length() / 8);
		
		for (int i = 0; i + 8 <= bin.length(); i += 8) {
			out.write(Integer.parseUnsignedInt(bin.substring(i, i + 8), 2));
		}
		
		return out.toByteArray();
	}
	
	public static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
		for(byte b : bytes) {
			builder.append(Digits.get((b >>> 4) & 15));
			builder.append(Digits.get(b & 15));
		}
		
		return builder.toString();
	}
	
	public static byte[] hexToBytes(String hex) {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(hex.length() / 2);
		
		for (int i = 0; i + 2 <= hex.length(); i+= 2) {
			out.write(
					(Digits.indexOf(hex.charAt(i)) << 4) +
					Digits.indexOf(hex.charAt(i + 1))
					);
		}
		
		return out.toByteArray();
	}
	
	public static String bytesToBase32(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		for (; i + 5 <= bytes.length; i += 5) {
			builder.append(Base32Chars.get((bytes[i] & 255) >>> 3));
			builder.append(Base32Chars.get(((bytes[i] & 7) << 2) | ((bytes[i + 1] & 255) >>> 6)));
			builder.append(Base32Chars.get(((bytes[i + 1] & 255) >>> 1) & 31));
			builder.append(Base32Chars.get(((bytes[i + 1] & 1) << 4) | ((bytes[i + 2] & 255) >>> 4)));
			builder.append(Base32Chars.get(((bytes[i + 2] & 15) << 1) | ((bytes[i + 3] & 255) >>> 7)));
			builder.append(Base32Chars.get(((bytes[i + 3] & 255) >>> 2) & 31));
			builder.append(Base32Chars.get(((bytes[i + 3] & 3) << 3) | ((bytes[i + 4] & 255) >>> 5)));
			builder.append(Base32Chars.get(bytes[i + 4] & 31));
		}
		
		int remaining = bytes.length - i;
		
		if (remaining > 0) {
			byte[] fill = new byte[5];
			System.arraycopy(bytes, i, fill, 0, remaining);
			builder.append(Base32Chars.get((bytes[i] & 255) >>> 3));
			if (remaining * 8 > 5)builder.append(Base32Chars.get(((fill[0] & 7) << 2) | (fill[1] >>> 6)));
			else builder.append('=');
			if (remaining * 8 > 10)builder.append(Base32Chars.get((fill[1] >>> 1) & 31));
			else builder.append('=');
			if (remaining * 8 > 15)builder.append(Base32Chars.get(((fill[1] & 1) << 4) | (fill[2] >>> 4)));
			else builder.append('=');
			if (remaining * 8 > 20)builder.append(Base32Chars.get(((fill[2] & 15) << 1) | (fill[3] >>> 7)));
			else builder.append('=');
			if (remaining * 8 > 25)builder.append(Base32Chars.get((fill[3] >>> 2) & 31));
			else builder.append('=');
			if (remaining * 8 > 30)builder.append(Base32Chars.get(((fill[3] & 3) << 3) | (fill[4] >>> 5)));
			else builder.append('=');
			builder.append('=');
		}
		
		return builder.toString();
	}
	
	public static byte[] base32ToBytes(String base32) {
		base32 = base32.replace("\r", "");
		base32 = base32.replace("\n", "");
		
		if (base32.length() % 8 != 0)
			throw new IllegalArgumentException("参数不是正确的base32编码");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int i = 0;
		byte[] cache = new byte[8];
		
		f:for (; i + 8 <= base32.length(); i += 8) {
			for (int x = 0; x < 8; x++) {
				if(base32.charAt(i + x) == '=')
					break f;
				if (Base32Chars.indexOf(base32.charAt(i + x)) != -1)
					cache[x] = (byte) Base32Chars.indexOf(base32.charAt(i + x));
				else if (Base32Chars_.indexOf(base32.charAt(i + x)) != -1)
					cache[x] = (byte) Base32Chars_.indexOf(base32.charAt(i + x));
				else
					throw new IllegalArgumentException("参数不是正确的base32编码");
			}
			
			out.write((cache[0] << 3) | (cache[1] >>> 2));
			out.write((cache[1] << 6) | (cache[2] << 1) | (cache[3] >>> 4));
			out.write((cache[3] << 4) | (cache[4] >>> 1));
			out.write((cache[4] << 7) | (cache[5] << 2) | (cache[6] >>> 3));
			out.write((cache[6] << 5) | cache[7]);
		}
		
		if (i < base32.length()) {
			int end = 0;
			for (int x = 0; x < 8; x++) {
				if(base32.charAt(i + x) == '=') {
					if(end == 0)end = x;
					continue;
				} else if(end != 0)
					throw new IllegalArgumentException("参数不是正确的base32编码");
				
				if (Base32Chars.indexOf(base32.charAt(i + x)) != -1)
					cache[x] = (byte) Base32Chars.indexOf(base32.charAt(i + x));
				else if (Base32Chars_.indexOf(base32.charAt(i + x)) != -1)
					cache[x] = (byte) Base32Chars_.indexOf(base32.charAt(i + x));
				else
					throw new IllegalArgumentException("参数不是正确的base32编码");
			}
			
			switch (end) {
			case 2:
				out.write((cache[0] << 3) | (cache[1] >>> 2));
				break;
			case 4:
				out.write((cache[0] << 3) | (cache[1] >>> 2));
				out.write((cache[1] << 6) | (cache[2] << 1) | (cache[3] >>> 4));
				break;
			case 5:
				out.write((cache[0] << 3) | (cache[1] >>> 2));
				out.write((cache[1] << 6) | (cache[2] << 1) | (cache[3] >>> 4));
				out.write((cache[3] << 4) | (cache[4] >>> 1));
				break;
			case 7:
				out.write((cache[0] << 3) | (cache[1] >>> 2));
				out.write((cache[1] << 6) | (cache[2] << 1) | (cache[3] >>> 4));
				out.write((cache[3] << 4) | (cache[4] >>> 1));
				out.write((cache[4] << 7) | (cache[5] << 2) | (cache[6] >>> 3));
				break;
			default:
				throw new IllegalArgumentException("参数不是正确的base32编码");
			}
		}
		
		return out.toByteArray();
	}
	
	public static String encodeMorseCode(String str) {
		str = str.replace(" ", "");
		str = str.toUpperCase();
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < str.length(); i++) {
			if (builder.length() > 0)
				builder.append('/');
			if (MorseCodeTable.table.containsKey(str.charAt(i)))
				builder.append(MorseCodeTable.table.get(str.charAt(i)));
			else
				throw new IllegalArgumentException("不能找到字符'" + str.charAt(i) + "'对应的莫尔斯电码");
		}
		
		return builder.toString();
	}
	
	public static String decodeMorseCode(String str) {
		Scanner sc = new Scanner(str);
		sc.useDelimiter("/");
		
		StringBuilder builder = new StringBuilder();
		
		String next;
		while (sc.hasNext()) {
			next = sc.next();
			if (MorseCodeTable.table_.containsKey(next))
				builder.append(MorseCodeTable.table_.get(next));
			else {
				sc.close();
				throw new IllegalArgumentException("不能找到 \"" + next + "\" 对应的字符");
			}
		}
		
		sc.close();
		
		return builder.toString();
	}
}
