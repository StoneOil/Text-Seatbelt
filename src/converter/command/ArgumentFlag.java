package converter.command;

public class ArgumentFlag {

	String name;
	
	String[] values;
	
	public ArgumentFlag(String name, String[] values) {
		this.name = name;
		this.values = values;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getValues() {
		return values;
	}
}
