package compiler.IR;

public class Seq extends Stm
{
	Stm s1, s2;

	public Seq(Stm _s1, Stm _s2)
	{
		s1 = _s1;
		s2 = _s2;
	}
}
