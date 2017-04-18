package compiler.translate;

public class Call extends Exp
{
	Stm e;
	
	public Call(Stm _e)
	{
		e = _e;
	}
}
