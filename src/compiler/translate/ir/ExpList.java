package compiler.translate.ir;

public class ExpList
{
	public Exp head;
	public ExpList next;
	
	public ExpList(Exp _h, ExpList _n)
	{
		head = _h;
		next = _n;
	}
}
