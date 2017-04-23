package compiler.translate.ir;

public class SEXP extends Stm
{
	public Exp exp;
	
	public SEXP(Exp e)
	{
		exp = e;
	}
}
