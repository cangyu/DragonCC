package compiler.translate;

public class Temp 
{
    public int num;
    public static int count = 0;
    
	public Temp() 
	{
		num = count++;
	}
	
	@Override
	public String toString() 
	{
		return "t" + num;
	}
}
