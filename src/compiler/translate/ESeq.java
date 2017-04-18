package compiler.translate;

public class ESeq extends Exp
{
	public Stm s, e;
	
	public ESeq(Stm _s, Stm _e)
	{
		s = _s;
		e = _e;
	}
}
