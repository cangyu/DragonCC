package compiler.semantic;

public final class Int extends Type
{
	public static Int instance;
	
	public static Int getInstance()
	{
		if(instance == null)
			instance = new Int();
		
		return instance;
	}
}
