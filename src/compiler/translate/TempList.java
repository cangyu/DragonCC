package compiler.translate;

public class TempList
{
	public Temp head;
	public TempList next;
	
	public TempList(Temp _h, TempList _n)
	{
		head = _h;
		next = _n;
	}
}
