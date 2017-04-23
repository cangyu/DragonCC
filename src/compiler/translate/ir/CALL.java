package compiler.translate.ir;

public class CALL extends Exp
{
	public Exp func;
	public ExpList args;
	
	public CALL(Exp _e, ExpList _a)
	{
		func = _e;
		args = _a;
	}
}
