package compiler.translate.ir;

public class ESeq extends Exp
{
	public Stm s;
	public Exp e;
	
	public ESeq(Stm _s, Exp _e)
	{
		s = _s;
		e = _e;
	}
}
