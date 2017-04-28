package compiler.translate;

public class RETURN extends Quad 
{
	public Oprand value;
	
	public RETURN(Oprand v)
	{
		value = v;
	}

	@Override
	public String toString() 
	{
		return "return " + value.toString();
	}
}
