package converter;

import java.io.IOException;
import java.io.OutputStream;

import converter.text.StringToBytes;

public class BinDecoder extends StringToBytes {
	
	private int[] cache = new int[8];
	
	private int count;
	
	public BinDecoder(OutputStream out) {
		super(out);
	}

	@Override
	public void close() throws IOException {
		flush();
		out.close();
	}

	@Override
	public void flush() throws IOException {
		if (count != 0)
			throw new IllegalArgumentException("参数不是正确的二进制字符串");
		out.flush();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = 0; i < len; i++) {
			write(cbuf[i + off]);
		}
	}
	
	@Override
	public void write(int c) throws IOException {
		switch(c) {
		case '0':
			cache[count++] = 0;
			break;
		case '1':
			cache[count++] = 1;
			break;
		default:
			throw new IllegalArgumentException("参数不是正确的二进制字符串");
		}
		
		if (count == 8)
			parse();
	}
	
	private void parse() throws IOException {
		int data = 0;
		
		data += cache[0] << 7;
		data += cache[1] << 6;
		data += cache[2] << 5;
		data += cache[3] << 4;
		data += cache[4] << 3;
		data += cache[5] << 2;
		data += cache[6] << 1;
		data += cache[7] << 0;
		
		out.write(data);
		
		count = 0;
	}

	public static class Executer extends StringToBytes.Executer {
		
		public static final Executer INSTANCE = new Executer();

		@Override
		public StringToBytes getInstance(OutputStream out) {
			return new BinDecoder(out);
		}
	}
}
