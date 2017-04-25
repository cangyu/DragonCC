package compiler.translate;

public class Return extends Quad 
{
	public Oprand value;
	
	public Return(Oprand v)
	{
		value = v;
	}

	@Override
	public String toString() 
	{
		return "return " + value.toString();
	}
}
