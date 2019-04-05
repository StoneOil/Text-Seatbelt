package converter;

public interface Executer<T, R> {
	
	public R execute(T t);
}
