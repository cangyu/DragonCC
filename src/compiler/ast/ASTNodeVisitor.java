package compiler.ast;

public interface ASTNodeVisitor
{
    /* Expr */
    public void visit(Expression x);
    public void visit(AssignmentExpr x);
    public void visit(BinaryExpr x);
    public void visit(CastExpr x);
    public void visit(UnaryExpr x);
    public void visit(PostfixExpr x);
    public void visit(PrimaryExpr x);
    
    /* Stmt */
    public void visit(StmtList x);
    public void visit(ExpressionStmt x);
    public void visit(CompoundStmt x);
    public void visit(SelectionStmt x);
    public void visit(JumpStmt x);
    public void visit(IterationStmt x);
    
    /* Decl */
    public void visit(StarList x);
    public void visit(Declaration x);
    public void visit(FuncDeclarator x);
    public void visit(VarDeclarator x);
    public void visit(DeclarationList x);
    public void visit(DeclaratorList x);
    public void visit(InitDeclarator x);
    public void visit(InitDeclaratorList x);
    public void visit(Initializer x);
    public void visit(InitializerList x);
    public void visit(NonInitDeclaration x);
    public void visit(NonInitDeclarationList x);
    public void visit(PlainDeclaration x);
    public void visit(PlainDeclarator x);

    /* Func */
    public void visit(FuncDef x);
    public void visit(ArgumentList x);
    public void visit(ParameterList x);

    /* Type */
    public void visit(TypeName x);
    public void visit(TypeSpecifier x);
    
    /* Program */
    public void visit(Program x);
}
