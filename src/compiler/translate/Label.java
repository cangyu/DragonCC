package compiler.translate;

public class Label 
{
	public String name;
	public static int count = 0;
	
	public Label() 
	{
		name = "_L" + count++;
	}
	
	public Label(String s) 
	{
		name = s;
	}
	
	
	@Override
	public String toString() 
	{
		return name;
	}
}
