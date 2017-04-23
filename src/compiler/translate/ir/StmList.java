package compiler.translate.ir;

public class StmList
{
	public Stm head;
	public StmList next;
	
	public StmList(Stm _h, StmList _n)
	{
		head = _h;
		next = _n;
	}
}
