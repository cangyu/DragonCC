package compiler.ast;

public interface ASTNodeVisitor
{
    /* Expr */
    public abstract void visit(Expression x) throws Exception;
    public abstract void visit(AssignmentExpr x) throws Exception;
    public abstract void visit(BinaryExpr x) throws Exception;
    public abstract void visit(CastExpr x) throws Exception;
    public abstract void visit(UnaryExpr x) throws Exception;
    public abstract void visit(PostfixExpr x) throws Exception;
    public abstract void visit(PrimaryExpr x) throws Exception;
    
    /* Stmt */
    public abstract void visit(StmtList x) throws Exception;
    public abstract void visit(ExpressionStmt x) throws Exception;
    public abstract void visit(CompoundStmt x) throws Exception;
    public abstract void visit(SelectionStmt x) throws Exception;
    public abstract void visit(JumpStmt x) throws Exception;
    public abstract void visit(IterationStmt x) throws Exception;
    
    /* Decl */
    public abstract void visit(StarList x) throws Exception;
    public abstract void visit(Declaration x) throws Exception;
    public abstract void visit(FuncDeclarator x) throws Exception;
    public abstract void visit(VarDeclarator x) throws Exception;
    public abstract void visit(DeclarationList x) throws Exception;
    public abstract void visit(DeclaratorList x) throws Exception;
    public abstract void visit(InitDeclarator x) throws Exception;
    public abstract void visit(InitDeclaratorList x) throws Exception;
    public abstract void visit(Initializer x) throws Exception;
    public abstract void visit(InitializerList x) throws Exception;
    public abstract void visit(NonInitDeclaration x) throws Exception;
    public abstract void visit(NonInitDeclarationList x) throws Exception;
    public abstract void visit(PlainDeclaration x) throws Exception;
    public abstract void visit(PlainDeclarator x) throws Exception;

    /* Func */
    public abstract void visit(FuncDef x) throws Exception;
    public abstract void visit(ArgumentList x) throws Exception;
    public abstract void visit(ParameterList x) throws Exception;

    /* Type */
    public abstract void visit(TypeName x) throws Exception;
    public abstract void visit(TypeSpecifier x) throws Exception;
    
    /* Program */
    public abstract void visit(Program x) throws Exception;
}
