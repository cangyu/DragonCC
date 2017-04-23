package compiler.translate.ir;

public class Seq extends Stm
{
	public Stm left, right;

	public Seq(Stm _l, Stm _r)
	{
		left = _l;
		right = _r;
	}
}
