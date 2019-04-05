package converter.text;

abstract public class AbstractDecoder implements Decoder {
	
	private boolean useBOM;
	
	private int bom;
	
	public AbstractDecoder(boolean bom) {
		this.useBOM = bom;
	}
	
	private int length = getBOM().length;
	
	public int decode(byte b) {
		if (bom != length && useBOM) {
			if (getBOM()[bom++] != b){
				bom = length;
				return 0x020000;
			}
			
			return 0x010000;
		} else {
			return decode0(b);
		}
	}
	
	abstract public int decode0(byte b);
	
	abstract public byte[] getBOM();
}
