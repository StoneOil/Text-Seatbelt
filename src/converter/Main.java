package converter;

import java.util.Optional;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
//		try {
//			Optional<? extends Executer> opt = null;
//			
//			if ((opt = BytesToString.getOperation(args[0])).isPresent()) {
//				bytesToString((BytesToString) opt.get(), args);
//			} else if ((opt = StringToBytes.getOperation(args[0])).isPresent()) {
//				stringToBytes((StringToBytes) opt.get(), args);
//			} else if (args[0].equalsIgnoreCase("MorseCodeEncode")) {
//				if (args.length >= 2)
//					System.out.println(Converters.encodeMorseCode(Converters.encodeMorseCode(args[1])));
//				else
//					System.out.println(Converters.encodeMorseCode(getInput("请输入神秘代码：")));
//			} else if (args[0].equalsIgnoreCase("MorseCodeDecode")) {
//				if (args.length >= 2)
//					System.out.println(Converters.encodeMorseCode(Converters.decodeMorseCode(args[1])));
//				else
//					System.out.println(Converters.encodeMorseCode(getInput("请输入神秘代码：")));
//			} else if (args[0].equals("/?") || args[0].equalsIgnoreCase("--help")) {
//				printHelp();
//			} else {
//				System.err.println("命令语法不正确");
//				System.err.println("请使用参数 /? 以查看帮助");
//				System.exit(1);
//			}
//		} catch(ArrayIndexOutOfBoundsException e) {
//			System.err.println("命令语法不正确");
//			System.err.println("请使用参数 /? 以查看帮助");
//			System.exit(1);
//		} catch(IllegalArgumentException e) {
//			System.err.println(e.getMessage());
//			System.exit(1);
//		}
	}
	
	private static void printHelp() {
		System.out.println("Seatbelt <operation> [-encoding <encoding>] [content]");
		System.out.println("  operation    -进行的操作，可选以下参数");
		System.out.println("                Base64Encode 编码base64");
		System.out.println("                Base32Encode 编码base32");
		System.out.println("                ToHex 转为十六进制字符串");
		System.out.println("                ToBin 转为二进制字符串");
		System.out.println();
		System.out.println("                Base64Decode 译码base64");
		System.out.println("                Base32Decode 译码base32");
		System.out.println("                DeHex 将十六进制字符串转回原文");
		System.out.println("                DeBin 将二进制字符串转回原文");
		System.out.println();
		System.out.println("                MorseCodeEncode 编码莫尔斯电码（仅英文）");
		System.out.println("                MorseCodeDecode 译码莫尔斯电码（仅英文）");
		System.out.println("                注意：译码前请替换字符。短信号为“.”，");
		System.out.println("                长信号为“-”，分隔符为“/”。");
		System.out.println();
		System.out.println("  -encoding    -如果对原文进行转换，则在转换前将原文转为");
		System.out.println("                指定编码；如果将某编码转回原文，则在转换");
		System.out.println("                后将原文从指定编码转为平台默认编码。如果");
		System.out.println("                操作是莫尔斯电码编解码，请勿输入此参数。");
		System.out.println();
		System.out.println("  content      -相应的数据，如果无此参数则要求输入。");
	}

	public static String getInput(String prompt) {
		System.out.print(prompt);
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		sc.close();
		return str;
	}
}
