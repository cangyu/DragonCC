package compiler.ast;

public class Program extends ASTNode
{
    public ProgramComp comp;
    public Program next;

    public Program(ProgramComp pc)
    {
        comp = pc;
        next = null;
    }

    void accept(ASTNodeVisitor v)
    {
        v.visit(this);
    }
}
