package compiler.ast;

public interface ASTNodeVisitor
{
    /* Expr */
    void visit(Expression exp);
    void visit(AssignmentExpr ae);
    void visit(BinaryExpr be);
    void visit(CastExpr ce);
    void visit(UnaryExpr ue);
    void visit(PostfixExpr pe);
    void visit(PrimaryExpr pe);
    
    /* Stmt */
    void visit(Stmt s);
    void visit(StmtList _sl);
    void visit(ExpressionStmt es);
    void visit(CompoundStmt cs);
    void visit(SelectionStmt ss);
    void visit(JumpStmt js);
    void visit(IterationStmt is);
    
    /* Decl */
    void visit(StarList _p);
    void visit(Declaration decl);
    void visit(Declarator d);
    void visit(DeclarationList ds);
    void visit(DeclaratorList ds);
    void visit(InitDeclarator id);
    void visit(InitDeclaratorList ids);
    void visit(Initializer ini);
    void visit(InitializerList ir);
    void visit(NonInitDeclaration nid);
    void visit(NonInitDeclarationList nids);
    void visit(PlainDeclaration pd);
    void visit(PlainDeclarator pdr);

    /* Func */
    void visit(FuncDef func);
    void visit(ArgumentList args);
    void visit(ParameterList pl);

    /* Type */
    void visit(TypeName t);
    void visit(TypeSpecifier ts);
    
    /* Program */
    void visit(Program prog);
    void visit(ProgramComp pc);
}
