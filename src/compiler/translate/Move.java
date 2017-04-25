package compiler.translate;

public class Move extends Quad 
{	
	public Oprand src;
	public Oprand dst;

	public Move(Oprand _src, Oprand _dst) 
	{
	    src = _src;
	    dst = _dst;
	}
	
	@Override
	public String toString() 
	{
		return src.toString()  + " -> " + dst.toString();
	}
}
