package compiler.IR;

import compiler.ast.*;

public class Translator implements ASTNodeVisitor
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
		if(x.iteration_type == IterationStmt.Type.WHILE)
		{
			Label begin = new Label();
			Label end = new Label();
			
			x.judge.accept(this);
			x.stmt.accept(this);
			
			x.ir_rep = new Seq(new Branch(x.judge.ir_rep, begin, end), 
							   new Seq(begin, 
									   new Seq(x.stmt.ir_rep, 
											   new Seq(new Jump(begin),
													   end))));
		}
		else
		{
			
		}
	}

	@Override
	public void visit(StarList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Declaration x) throws Exception
	{
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
