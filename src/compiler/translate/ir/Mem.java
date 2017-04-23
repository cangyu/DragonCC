package compiler.translate.ir;

public class Mem extends Exp
{
	public Exp e;
	
	public Mem(Exp _e)
	{
		e = _e;
	}
}
