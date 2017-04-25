package compiler.translate;

public class TempOprand extends Oprand
{
	public Temp temp;

	public TempOprand(Temp t) 
	{
		temp = t;
	}
	
	@Override
	public String toString() 
	{
		return temp.toString();
	}
}
