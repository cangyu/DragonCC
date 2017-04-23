package compiler.translate.ir;

public class MOVE extends Stm
{
	public Exp src, dst;
	
	public MOVE(Exp _d, Exp _s)
	{
		dst = _d;
		src = _s;
	}
}
