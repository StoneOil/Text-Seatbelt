package converter;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Function;

public enum BytesToString implements Executer {
	Base64Encode(b -> Base64.getEncoder().encodeToString(b)),
	Base32Encode(b -> Converters.bytesToBase32(b)),
	BytesToBin(b -> Converters.bytesToBin(b)),
	BytesToHex(b -> Converters.bytesToHex(b));
	
	private Function<byte[], String> executer;
	
	private String encoding;
	
	private BytesToString(Function<byte[], String> executer) {
		this.executer = executer;
	}
	
	@Override
	public void execute(String str) {
		try {
			byte[] bytes;
			
			if (encoding == null)
				bytes = str.getBytes();
			else
				bytes = str.getBytes(encoding);
			
			System.out.println(executer.apply(bytes));
			
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
	
	public static Optional<BytesToString> getOperation(String name) {
		if (name.equalsIgnoreCase("Base64Encode"))
			return Optional.of(BytesToString.Base64Encode);
		else if (name.equalsIgnoreCase("Base32Encode"))
			return Optional.of(BytesToString.Base32Encode);
		else if (name.equalsIgnoreCase("ToHex"))
			return Optional.of(BytesToString.BytesToHex);
		else if (name.equalsIgnoreCase("ToBin"))
			return Optional.of(BytesToString.BytesToBin);
		else
			return Optional.empty();
	}
}
