package compiler.semantic;

public final class Char extends Type
{
	public static Char instance;
	
	public static Char getInstance()
	{
		if(instance == null)
			instance = new Char();
		
		return instance;
	}
}
