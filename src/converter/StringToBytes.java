package converter;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

public enum StringToBytes implements Executer {
	Base64Decode(str -> Base64.getDecoder().decode(str)),
	Base32Decode(str -> Converters.base32ToBytes(str)),
	BinToBase64(str -> Converters.binToBytes(str)),
	HexToBase64(str -> Converters.hexToBytes(str));
	
	private Function<String, byte[]> executer;
	
	private String encoding;
	
	private StringToBytes(Function<String, byte[]> executer) {
		this.executer = executer;
	}
	
	@Override
	public void execute(String str) {
		try {
			String out;
			byte[] bytes = executer.apply(str);
			
			if(encoding == null)
				out = new String(bytes);
			else
				out = new String(bytes, encoding);
			
			System.out.println(out);
			
			System.exit(0);
		} catch(UnsupportedEncodingException e) {
			System.err.println("不支持编码" + encoding);
			System.exit(1);
		} catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public static Optional<StringToBytes> getOperation(String name) {
		if (name.equalsIgnoreCase("Base64Decode"))
			return Optional.of(StringToBytes.Base64Decode);
		else if (name.equalsIgnoreCase("Base32Decode"))
			return Optional.of(StringToBytes.Base32Decode);
		else if (name.equalsIgnoreCase("DeHex"))
			return Optional.of(StringToBytes.HexToBase64);
		else if (name.equalsIgnoreCase("DeBin"))
			return Optional.of(StringToBytes.BinToBase64);
		else
			return Optional.empty();
	}
}
