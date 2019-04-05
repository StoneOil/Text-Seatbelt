package converter.command;

public class Flag {

	String name;
	
	int after;
	
	public Flag(String name, int after) {
		this.name = name;
		this.after = after;
	}
	
	public String getName() {
		return name;
	}
	
	public Arguments interpret(String[] args) {
		return null;
	}
}
