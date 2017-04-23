package compiler.translate.frame;

public abstract class AccessList
{
	public Access head;
	public AccessList next;
	
	public AccessList(Access _h, AccessList _n)
	{
		head = _h;
		next = _n;
	}
}
