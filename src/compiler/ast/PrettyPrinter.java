package compiler.ast;

public class PrettyPrinter implements ASTNodeVisitor
{

	@Override
	public void visit(Expression x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentExpr x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BinaryExpr x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpr x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UnaryExpr x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PostfixExpr x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PrimaryExpr x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StmtList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExpressionStmt x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CompoundStmt x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectionStmt x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JumpStmt x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IterationStmt x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StarList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Declaration x)
	{
		int lc =0;
		x.type_specifier.accept(this);
		lc += x.type_specifier.code_rep.length;
		x.init_declarator_list.accept(this);
		lc += x.init_declarator_list.code_rep.length;
		
		x.code_rep=new String[lc];
		
		
	}

	@Override
	public void visit(FuncDeclarator x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VarDeclarator x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclarationList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclaratorList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitDeclarator x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitDeclaratorList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Initializer x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitializerList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonInitDeclaration x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonInitDeclarationList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainDeclaration x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainDeclarator x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FuncDef x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArgumentList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ParameterList x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypeName x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TypeSpecifier x)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Program x)
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
