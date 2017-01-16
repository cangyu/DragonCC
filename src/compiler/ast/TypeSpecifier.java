package compiler.ast;

public class TypeSpecifier 
{
	public static enum Type{VOID, CHAR, INT, STRUCT, UNION};
	
	public Type type;
	public String id;
	public NonInitDecls non_init_decls;
	
	public TypeSpecifier(Type _t)
	{
		type = _t;
	}
	
	public TypeSpecifier(Type _t, String _id, NonInitDecls _nids)
	{
		type = _t;
		id = _id;
		non_init_decls = _nids;
	}
	
	@Override
	public String toString()
	{
		switch(type)
		{
		case VOID:
			return "void";
		case CHAR:
			return "char";
		case INT:
			return "int";
		case STRUCT:
			return "struct";
		case UNION:
			return "union";
		default:
			return "";
		}
	}
}
