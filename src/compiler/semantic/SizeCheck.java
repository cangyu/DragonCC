package compiler.semantic;

import java.util.Iterator;

import compiler.ast.*;

public class SizeCheck implements ASTNodeVisitor
{
	public static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}

	@Override
	public void visit(Expression x) throws Exception
	{
		Expression y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(AssignmentExpr x) throws Exception
	{
		x.left.accept(this);
		x.right.accept(this);
	}

	@Override
	public void visit(BinaryExpr x) throws Exception
	{
		x.left.accept(this);
		x.right.accept(this);
	}

	@Override
	public void visit(CastExpr x) throws Exception
	{
		x.target_type.accept(this);
		x.expr.accept(this);
	}

	@Override
	public void visit(UnaryExpr x) throws Exception
	{
		if (x.expr != null)
			x.expr.accept(this);
		
		if (x.type_name != null)
			x.type_name.accept(this);

		//TODO
	}

	@Override
	public void visit(PostfixExpr x) throws Exception
	{
		x.expr.accept(this);
		if (x.param != null)
		{
			if (x.param instanceof Expression)
				((Expression) x.param).accept(this);

			if (x.param instanceof ArgumentList)
				((ArgumentList) x.param).accept(this);
		}
	}

	@Override
	public void visit(PrimaryExpr x) throws Exception
	{
		switch (x.elem_type)
		{
		case INT:
		case CHAR:
		case STRING:
		case ID:
			break;
		case PAREN:
			((Expression)x.elem).accept(this);
			break;
		}
	}

	@Override
	public void visit(StmtList x) throws Exception
	{
		StmtList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(ExpressionStmt x) throws Exception
	{
		x.e.accept(this);
	}

	@Override
	public void visit(CompoundStmt x) throws Exception
	{
		if (x.declaration_list != null)
			x.declaration_list.accept(this);

		if (x.stmt_list != null)
			x.stmt_list.accept(this);
	}

	@Override
	public void visit(SelectionStmt x) throws Exception
	{
		x.cond.accept(this);
		x.if_clause.accept(this);
		if (x.else_clause != null)
			x.else_clause.accept(this);
	}

	@Override
	public void visit(JumpStmt x) throws Exception
	{
		if (x.expr != null)
			x.expr.accept(this);
	}

	@Override
	public void visit(IterationStmt x) throws Exception
	{
		if (x.init != null)
			x.init.accept(this);
		if (x.judge != null)
			x.judge.accept(this);
		if (x.next != null)
			x.next.accept(this);

		x.stmt.accept(this);
	}

	@Override
	public void visit(StarList x) throws Exception
	{
		return;
	}

	@Override
	public void visit(Declaration x) throws Exception
	{
		x.type_specifier.accept(this);

		if (x.init_declarator_list != null)
			x.init_declarator_list.accept(this);
	}

	@Override
	public void visit(FuncDeclarator x) throws Exception
	{
		x.plain_declarator.accept(this);
		if (x.param != null)
			x.param.accept(this);
	}

	@Override
	public void visit(VarDeclarator x) throws Exception
	{
		x.plain_declarator.accept(this);
		Iterator<Expr> it = x.dimension.iterator();
		while (it.hasNext())
			it.next().accept(this);
	}

	@Override
	public void visit(DeclarationList x) throws Exception
	{
		DeclarationList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(DeclaratorList x) throws Exception
	{
		DeclaratorList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(InitDeclarator x) throws Exception
	{
		x.declarator.accept(this);
		if (x.initializer != null)
			x.initializer.accept(this);
	}

	@Override
	public void visit(InitDeclaratorList x) throws Exception
	{
		InitDeclaratorList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(Initializer x) throws Exception
	{
		if (x.expr != null)
			x.expr.accept(this);
		else
			x.initializer_list.accept(this);
	}

	@Override
	public void visit(InitializerList x) throws Exception
	{
		InitializerList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(NonInitDeclaration x) throws Exception
	{
		x.type_specifier.accept(this);
		x.declarator_list.accept(this);
	}

	@Override
	public void visit(NonInitDeclarationList x) throws Exception
	{
		NonInitDeclarationList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(PlainDeclaration x) throws Exception
	{
		x.type_specifier.accept(this);
		x.declarator.accept(this);
	}

	@Override
	public void visit(PlainDeclarator x) throws Exception
	{
		return;
	}

	@Override
	public void visit(FuncDef x) throws Exception
	{
		x.type_specifier.accept(this);
		x.func_name.accept(this);
		if (x.params != null)
			x.params.accept(this);
		x.comp_stmt.accept(this);
	}

	@Override
	public void visit(ArgumentList x) throws Exception
	{
		ArgumentList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(ParameterList x) throws Exception
	{
		ParameterList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

	@Override
	public void visit(TypeName x) throws Exception
	{
		x.type_specifier.accept(this);
		x.star_list.accept(this);
	}

	@Override
	public void visit(TypeSpecifier x) throws Exception
	{
		switch (x.type)
		{
		case VOID:
		case INT:
		case CHAR:
			break;
		case STRUCT:
		case UNION:
			if (x.comp != null)
				x.comp.accept(this);
			calc_size(x);
			break;
		default:
			break;
		}
	}
	
	private void calc_size(TypeSpecifier x) throws Exception
	{
		//TODO
	}

	@Override
	public void visit(Program x) throws Exception
	{
		Program y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}
	}

}
