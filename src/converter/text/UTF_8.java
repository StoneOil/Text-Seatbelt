package converter.text;

public class UTF_8 {

	private static byte[] BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
	
	public static class Decoder extends AbstractDecoder {
		
		public Decoder(boolean bom) {
			super(bom);
		}

		private int[] bytes = new int[3];
		
		private int count;
		
		private int total;
		
		/**
		 * @return 
		 * 低16位表示字符，<br>
		 * 高16位表示状态：<br>
		 * 0 返回一个字符<br>
		 * 1 需要更多字节<br>
		 * 2 解码出现错误，放弃一个字符<br>
		 * 3 解码出现错误，放弃上一个字符，返回参数对应的字符<br>
		 * 4 表示字符需要的字节超过2个，放弃字符
		 */
		@Override
		public int decode0(byte b) {
			int bi = Byte.toUnsignedInt(b);
			
			if (bi >>> 6 == 0x02) {
				if (total == 0) {
					count = 0;
					total = 0;
					return 0x020000;
				}
				
				if (total > 3) {
					if (++count == total)
						return 0x040000;
					else
						return 0x010000;
				}
				
				bytes[count++] = bi;
				
				if (count == total) {
					int c = 0;
					switch (total) {
					case 2:
						c = ((bytes[0] & 0x1f) << 6) | (bytes[1] & 0x3f);
						break;
					case 3:
						c = (bytes[0] << 12) | ((bytes[1] & 0x3f) << 6) | (bytes[2] & 0x3f);
					}
					
					count = 0;
					total = 0;
					return c & 0xffff;
				}
				
				return 0x010000;
			}
			
			if (bi >>> 7 == 0x00) {
				if (total == 0)
					return bi;
				else {
					total = 0;
					count = 0;
					return 0x030000 | bi;
				}
			}
			
			if (bi >>> 5 == 0x06)
				return newChar(2, bi);
			
			if (bi >>> 4 == 0x0E)
				return newChar(3, bi);
			
			if (bi >>> 3 == 0x1E)
				return newChar(4, bi);
			
			if (bi >>> 2 == 0x3E)
				return newChar(5, bi);
			
			if (bi >>> 1 == 0x7E)
				return newChar(6, bi);
			
			return 0;
		}
		
		public int newChar(int total, int bi) {
			int i = 0x010000;
			if (this.total != 0)
				i = 0x020000;
			
			this.total = total;
			this.count = 1;
			this.bytes[0] = bi;
			
			return i;
		}

		@Override
		public byte[] getBOM() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class Encoder extends AbstractEncoder {
		
		public Encoder(boolean bom) {
			super(bom);
		}

		/**
		 * UTF-8编码一个字符，结果保存至 bytes
		 * @return 表示这个字符需要的字节数
		 */
		@Override
		public int encode0(char c, byte[] bytes) {
			if (c < 128) {
				bytes[0] = (byte) c;
				return 1;
			}
			
			if (c < 2048) {
				bytes[0] = (byte) (0xc0 | (c >> 6));
				bytes[1] = (byte) (0x80 | (c & 0x3f));
				return 2;
			}
			
			bytes[0] = (byte) (0xe0 | (c >> 12));
			bytes[1] = (byte) (0x80 | ((c & 0xfc0) >> 6));
			bytes[2] = (byte) (0x80 | (c & 0x3f));
			return 3;
		}

		@Override
		public byte[] getBOM() {
			return BOM;
		}
	}
}
