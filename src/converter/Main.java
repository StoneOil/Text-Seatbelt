package converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import converter.text.BytesToString;
import converter.text.StringToBytes;
import converter.text.StringToString;

public class Main {
	
	public static void main(String[] args) throws IOException {
		if (args.length >= 1){
			BytesToString.Executer e1 = BytesToString.getExecuter(args[0]);
			if (e1 != null) {
				e1.execute(Arrays.copyOfRange(args, 1, args.length));
				return;
			}
			
			StringToBytes.Executer e2 = StringToBytes.getExecuter(args[0]);
			if (e2 != null) {
				e2.execute(Arrays.copyOfRange(args, 1, args.length));
				return;
			}
			
			StringToString.Executer e3 = StringToString.getExecuter(args[0]);
			if (e3 != null) {
				e3.execute(Arrays.copyOfRange(args, 1, args.length));
				return;
			}
		}
		printHelp();
	}
	
	public static void printHelp() {
		System.out.println("Text-Seatbelt b64e|b32e|hexe|bine [options] [content]");
		System.out.println("  b64e                编码base64");
		System.out.println("  b32e                编码base32");
		System.out.println("  hexe                转为十六进制字符串");
		System.out.println("  bine                转为二进制字符串");
		System.out.println("  以下是可选参数");
		System.out.println("  -i <file>           从指定文件输入");
		System.out.println("  -o <file> <charset> 以指定编码向指定文件输出");
		System.out.println("  -e <charset>        将输入转为指定编码后输出");
		System.out.println();
		System.out.println("Text-Seatbelt b64d|b32d|hexd|bind [options] [content]");
		System.out.println("  b64d                译码base64");
		System.out.println("  b32d                译码base32");
		System.out.println("  hexd                将十六进制字符串转回原文");
		System.out.println("  bind                将二进制字符串转回原文");
		System.out.println("  以下是可选参数");
		System.out.println("  -i <file> <charset> 以指定编码从指定文件输入");
		System.out.println("  -o <file>           向指定文件输出");
		System.out.println("  -e <charset>        将译码的内容从指定编码转为默认编码后输出");
		System.out.println();
		System.out.println("Text-Seatbelt more|mord [options] [content]");
		System.out.println("  more                编码莫尔斯电码（仅英文）");
		System.out.println("  mord                译码莫尔斯电码（仅英文）");
		System.out.println("                      注意：译码前请替换字符。短信号为“.”，");
		System.out.println("                      长信号为“-”，分隔符为“/”。");
		System.out.println("  以下是可选参数");
		System.out.println("  -i <file> <charset> 以指定编码从指定文件输入");
		System.out.println("  -o <file> <charset> 以指定编码向指定文件输出");
	}

	public static String getInput(String prompt) {
		System.out.print(prompt);
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		sc.close();
		return str;
	}
}
