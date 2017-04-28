package compiler.translate;

import compiler.ast.BinaryExpr.Operator;

public class BINOP extends Quad
{
    public Operator op;
    public Oprand left;
    public Oprand right;
    public Oprand result;

    public BINOP(Operator _op, Oprand _larg, Oprand _rarg, Oprand _ans)
    {
        _op = op;
        left = _larg;
        right = _rarg;
        result = _ans;
    }

    @Override
    public String toString()
    {
        return result.toString() + " <- " + op.toString() + ' ' + left.toString() + ' ' + right.toString();
    }
}
