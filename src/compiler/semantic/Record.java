package compiler.semantic;

public abstract class Record extends Type
{
	public String tag;
	public Table comp;
	public boolean complete;

	public Record(String _n, Table _c)
	{
		tag = _n;
		comp = _c;
		complete = false;
		size = 0;
	}
	
	public abstract int getSize();
}
