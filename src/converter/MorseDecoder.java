package converter;

import java.io.IOException;
import java.io.Writer;

import converter.text.StringToString;

public class MorseDecoder extends StringToString {

	private char[] cache = new char[6];

	private int count;

	public MorseDecoder(Writer writer) {
		super(writer);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = 0; i < len; i++)
			write(cbuf[i + off]);
	}

	@Override
	public void write(int c) throws IOException {
		if (count == 6)
			throw new IllegalArgumentException("请按照帮助文档指定的格式输入莫尔斯电码");
		
		if (c == '/')
			parse();
		else
			cache[count++] = (char) c;
	}

	private void parse() throws IOException {
		String str = new String(cache, 0, count);
		Character c = MorseCodeTable.table_.get(str);
		
		if (c == null)
			throw new IllegalArgumentException("请按照帮助文档指定的格式输入莫尔斯电码");
		
		writer.append(c);
		
		count = 0;
	}

	@Override
	public void close() throws IOException {
		flush();
		writer.close();
	}
	
	@Override
	public void flush() throws IOException {
		if (count != 0)
			parse();
		else
			throw new IllegalArgumentException("请按照帮助文档指定的格式输入莫尔斯电码");
		writer.flush();
	}

	public static class Executer extends StringToString.Executer {

		public static final Executer INSTANCE = new Executer();

		@Override
		public StringToString getInstance(Writer writer) {
			return new MorseDecoder(writer);
		}
	}
}
