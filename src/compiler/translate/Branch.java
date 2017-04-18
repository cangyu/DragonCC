package compiler.translate;

public class Branch extends Stm
{
	public Stm e;
	public Label t, f;
	
	public Branch(Stm _e, Label _t, Label _f)
	{
		e = _e;
		t = _t;
		f = _f;
	}
}
