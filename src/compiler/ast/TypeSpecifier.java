package compiler.ast;

public class TypeSpecifier 
{
	public static enum Type{VOID, CHAR, INT, STRUCT, UNION};
	
	public Type type;
	public String id;
	public NonInitDecls non_init_decls;
	
	public TypeSpecifier(Type _t)
	{
		type=_t;
	}
	
	public TypeSpecifier(Type _t, NonInitDecls _nids)
	{
		type=_t;
		non_init_decls=_nids;
	}
	
	public TypeSpecifier(Type _t , String _id)
	{
		type=_t;
		id=_id;
	}
	
	public TypeSpecifier(Type _t, String _id, NonInitDecls _nids)
	{
		type=_t;
		id=_id;
		non_init_decls=_nids;
	}
}
