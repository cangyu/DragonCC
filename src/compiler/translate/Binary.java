package compiler.translate;

import compiler.ast.BinaryExpr.Operator;

public class Binary extends Exp
{
	public Operator op;
	public Exp e1, e2;

	public Binary(Operator _op, Exp _e1, Exp _e2)
	{
		op = _op;
		e1 = _e1;
		e2 = _e2;
	}
}
