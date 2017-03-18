package compiler.ast;

public class Program extends ASTNode
{
    public ProgramComp head;
    public Program next;

    public Program(ProgramComp _pc, Program _p)
    {
    	head = _pc;
        next = _p;
    }

    public void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}
