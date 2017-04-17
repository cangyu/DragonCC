package compiler.semantic;

public abstract class Record extends Type
{
	public String tag;
	public Table comp;
	public int ref_cnt;

	public Record(String _n, Table _c)
	{
		tag = _n;
		comp = _c;
		size = 0;
		complete = false;
		visited = false;
	}
}
