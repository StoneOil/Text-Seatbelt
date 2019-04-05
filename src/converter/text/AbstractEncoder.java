package converter.text;

abstract public class AbstractEncoder implements Encoder {
	
	private boolean useBOM;
	
	private boolean bom;
	
	public AbstractEncoder(boolean bom) {
		this.useBOM = bom;
	}
	
	public int encode(char c, byte[] bytes) {
		if (!bom && useBOM) {
			int l1 = getBOM().length;
			int l2 = encode0(c, bytes);
			
			for (int i = l1 + l2 - 1; i >= l1; i--) {
				bytes[i] = bytes [i - l1];
			}
			
			System.arraycopy(getBOM(), 0, bytes, 0, l1);
			
			bom = true;
			
			return l1 + l2;
		} else {
			return encode0(c, bytes);
		}
	}
	
	abstract public int encode0(char c, byte[] bytes);
	
	abstract public byte[] getBOM();
}
