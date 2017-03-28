package compiler.ast;

public class NonInitDeclaration extends ASTNode
{
    public TypeSpecifier type_specifier;
    public DeclaratorList declarator_list;

    public NonInitDeclaration(TypeSpecifier _t, DeclaratorList _decls)
    {
        type_specifier = _t;
        declarator_list = _decls;
    }
    
    public void accept(ASTNodeVisitor v) throws Exception
    {
        v.visit(this);
    }
}
