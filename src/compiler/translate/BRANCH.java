package compiler.translate;

import compiler.ast.BinaryExpr.Operator;

public class BRANCH extends Quad
{
    public Operator op;
    public Oprand left;
    public Oprand right;
    public Label result;
    
    public BRANCH(Operator _op, Oprand _larg, Oprand _rarg, Label _label)
    {
        op = _op;
        left = _larg;
        right = _rarg;
        result = _label;
    }

    @Override
    public String toString()
    {
        return op.toString() + ' ' + left.toString() + ' ' + right.toString() + " ? goto " + result.toString();
    }
}
