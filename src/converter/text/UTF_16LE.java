package converter.text;

public class UTF_16LE {
	
	private static byte[] BOM = {(byte) 0xFF, (byte) 0xFE};

	public static class Decoder extends AbstractDecoder {
		
		public Decoder(boolean bom) {
			super(bom);
		}

		private int last;
		
		private boolean bool;
		
		@Override
		public int decode0(byte b) {
			if (bool) {
				return (b << 8) | last;
			}
			
			return 0x010000;
		}

		@Override
		public byte[] getBOM() {
			return BOM;
		}
	}
	
	public static class Encoder extends AbstractEncoder {
		
		public Encoder(boolean bom) {
			super(bom);
		}

		@Override
		public int encode0(char c, byte[] bytes) {
			bytes[0] = (byte) c;
			bytes[1] = (byte) (c >> 8);
			return 2;
		}

		@Override
		public byte[] getBOM() {
			return BOM;
		}
	}
}
