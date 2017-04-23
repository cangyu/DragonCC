package compiler.translate.ir;

public class ESEQ extends Exp
{
	public Stm s;
	public Exp e;
	
	public ESEQ(Stm _s, Exp _e)
	{
		s = _s;
		e = _e;
	}
}
