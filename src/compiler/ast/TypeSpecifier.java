package compiler.ast;

public class TypeSpecifier 
{
	public static enum Type{ VOID, CHAR, INT, STRUCT, UNION};
	
	public Type t;
	public String name;
	public NonInitDecls nids;
	
	public TypeSpecifier(Type _t)
	{
		t=_t;
	}
	
	public TypeSpecifier(Type _t, String _name, NonInitDecls _nids)
	{
		t=_t;
		name=_name;
		nids=_nids;
	}
}
