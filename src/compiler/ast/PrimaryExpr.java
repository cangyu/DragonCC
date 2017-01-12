package compiler.ast;

public class PrimaryExpr extends Expr
{
	public static enum Type{ID, STRING, CHAR, INT};
	
	public Type type;
	public Object object;
	
	public PrimaryExpr(Type _t, Object _obj)
	{
		type=_t;
		object=_obj;
	}
}
