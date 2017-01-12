package compiler.ast;

public class CastExpr extends Expr
{
	public TypeSpecifier type;
	public Star stars;
	
	public CastExpr(TypeSpecifier _t, Star _s)
	{
		type=_t;
		stars=_s;
	}
}
