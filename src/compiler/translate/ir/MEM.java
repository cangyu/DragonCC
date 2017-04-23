package compiler.translate.ir;

public class MEM extends Exp
{
	public Exp e;
	
	public MEM(Exp _e)
	{
		e = _e;
	}
}
