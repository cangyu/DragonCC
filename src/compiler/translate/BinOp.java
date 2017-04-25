package compiler.translate;

import compiler.ast.BinaryExpr.Operator;

public class BinOp extends Quad
{
    public Operator op;
    public Oprand left;
    public Oprand right;
    public Oprand ans;

    public BinOp(Operator _op, Oprand _larg, Oprand _rarg, Oprand _ans)
    {
        op = _op;
        left = _larg;
        right = _rarg;
        ans = _ans;
    }

    @Override
    public String toString()
    {
        return op.toString() + ' ' + left.toString() + right.toString() + "->" + ans.toString();
    }
}
