package compiler.ast;

public interface ASTNodeVisitor
{
    /* Expr */
    public void visit(Expression exp);
    public void visit(AssignmentExpr ae);
    public void visit(BinaryExpr be);
    public void visit(CastExpr ce);
    public void visit(UnaryExpr ue);
    public void visit(PostfixExpr pe);
    public void visit(PrimaryExpr pe);
    
    /* Stmt */
    public void visit(StmtList _sl);
    public void visit(ExpressionStmt es);
    public void visit(CompoundStmt cs);
    public void visit(SelectionStmt ss);
    public void visit(JumpStmt js);
    public void visit(IterationStmt is);
    
    /* Decl */
    public void visit(StarList _p);
    public void visit(Declaration decl);
    public void visit(FuncDeclarator fd);
    public void visit(VarDeclarator vd);
    public void visit(DeclarationList ds);
    public void visit(DeclaratorList ds);
    public void visit(InitDeclarator id);
    public void visit(InitDeclaratorList ids);
    public void visit(Initializer ini);
    public void visit(InitializerList ir);
    public void visit(NonInitDeclaration nid);
    public void visit(NonInitDeclarationList nids);
    public void visit(PlainDeclaration pd);
    public void visit(PlainDeclarator pdr);

    /* Func */
    public void visit(FuncDef func);
    public void visit(ArgumentList args);
    public void visit(ParameterList pl);

    /* Type */
    public void visit(TypeName t);
    public void visit(TypeSpecifier ts);
    
    /* Program */
    public void visit(Program prog);
}
