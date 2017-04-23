package compiler.translate.ir;

public class SEQ extends Stm
{
	public Stm left, right;

	public SEQ(Stm _l, Stm _r)
	{
		left = _l;
		right = _r;
	}
}
