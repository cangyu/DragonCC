package compiler.ast;

public class PrettyPrinter implements ASTNodeVisitor
{

	@Override
	public void visit(Expression x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BinaryExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UnaryExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PostfixExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PrimaryExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StmtList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExpressionStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CompoundStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectionStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JumpStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IterationStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StarList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Declaration x) throws Exception
	{
		int lc =0;
		x.type_specifier.accept(this);
		lc += x.type_specifier.code_rep.length;
		x.init_declarator_list.accept(this);
		lc += x.init_declarator_list.code_rep.length;
		
		x.code_rep=new String[lc];
		
		
	}

	@Override
	public void visit(FuncDeclarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VarDeclarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclarationList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclaratorList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitDeclarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitDeclaratorList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Initializer x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitializerList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonInitDeclaration x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonInitDeclarationList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainDeclaration x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainDeclarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FuncDef x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArgumentList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ParameterList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypeName x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypeSpecifier x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Program x) throws Exception
	{	
		Program y = x;
		int lc =0;
		while(y!=null)
		{
			y.head.accept(this);
			lc+=(y.head.code_rep.length+1);
			y=y.next;
		}
		
		x.code_rep=new String[lc];
		int cl =0;
		y = x;
		while(y!=null)
		{
			for(String str : y.head.code_rep)
				x.code_rep[cl++]+=str;
			cl++;
		}
	}
}
