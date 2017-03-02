package compiler.semantic;

public final class Void extends Type
{
	public static Void instance;
	
	public static Void getInstance()
	{
		if(instance == null)
			instance = new Void();
		
		return instance;
	}
}
