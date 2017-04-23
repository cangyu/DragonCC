package compiler.translate.ir;

public class Call extends Exp
{
	public Exp func;
	public ExpList args;
	
	public Call(Exp _e, ExpList _a)
	{
		func = _e;
		args = _a;
	}
}
