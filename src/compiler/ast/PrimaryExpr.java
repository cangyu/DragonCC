package compiler.ast;

public class PrimaryExpr extends Expr
{
	public static enum Type{ID, STRING, CHAR, INT};
	
	public Type elem_type;
	public Object value;
	
	public PrimaryExpr(Type _t, Object _val)
	{
		elem_type = _t;
		value = _val;;
	}
	
	@Override
	public String toString()
	{
		switch(elem_type)
		{
		case ID:
			return (String)value;
		case STRING:
			return "\"" + (String)value + "\"";
		case INT:
			return ((Integer)value).toString();
		case CHAR:
			return "\'" + (String)value + "\'";
		default:
			return "";	
		}
	}
}
