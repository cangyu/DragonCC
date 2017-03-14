package compiler.ast;

public class PrimaryExpr extends Expr
{
	public static enum Type{ID, STRING, CHAR, INT, PAREN};
	
	public Type elem_type;
	public Object elem;
	
	public PrimaryExpr(Type _t, Object _val)
	{
		elem_type = _t;
		elem = _val;;
	}
	
	@Override
	public String toString()
	{
		switch(elem_type)
		{
		case ID:
			return (String)elem;
		case STRING:
			return "\"" + (String)elem + "\"";
		case INT:
			return ((Integer)elem).toString();
		case CHAR:
			return "\'" + (String)elem + "\'";
		case PAREN:
		    return '(' + ((Expression)elem).toString() + ')';
		default:
			return "";
		}
	}
}
