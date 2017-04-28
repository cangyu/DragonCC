package compiler.translate;

public class MOVE extends Quad 
{	
	public Oprand src;
	public Oprand dst;

	public MOVE(Oprand _src, Oprand _dst) 
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
