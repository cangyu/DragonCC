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
}
