package compiler.semantic;

import java.util.*;
import compiler.ast.*;

public class ConstExprEval implements ASTNodeVisitor
{

	private void panic(String msg) throws Exception
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
		
		x.isConst = false;
		x.value = null;
		x.hasInitialized = false;
		x.type = null;
		x.isLvalue = false;
	}

	@Override
	public void visit(BinaryExpr x) throws Exception
	{
		x.left.accept(this);
		x.right.accept(this);

		if (x.left.isConst && x.right.isConst)
		{
			x.isConst = true;
			x.value = (int) 0;
			x.hasInitialized = true;
			x.type = Int.getInstance();
			x.isLvalue = false;

			switch (x.op)
			{
			case BIT_AND:
				x.value=((int)x.left.value) & ((int)x.right.value);
				break;
			case BIT_XOR:
				x.value=((int)x.left.value) ^ ((int)x.right.value);
				break;
			case BIT_OR:
				x.value=((int)x.left.value) | ((int)x.right.value);
				break;
			case AND:
				x.value=(((int)x.left.value)!=0 && ((int)x.right.value)!=0) ? 1 : 0;
				break;
			case OR:
				x.value=(((int)x.left.value)!=0 || ((int)x.right.value)!=0) ? 1 : 0;
				break;
			case EQ:
				x.value=(((int)x.left.value) == ((int)x.right.value)) ? 1 : 0;
				break;
			case NE:
				x.value=(((int)x.left.value) != ((int)x.right.value)) ? 1 : 0;
				break;
			case LT:
				x.value=(((int)x.left.value) < ((int)x.right.value)) ? 1 : 0;
				break;
			case GT:
				x.value=(((int)x.left.value) > ((int)x.right.value)) ? 1 : 0;
				break;
			case LE:
				x.value=(((int)x.left.value) <= ((int)x.right.value)) ? 1 : 0;
				break;
			case GE:
				x.value=(((int)x.left.value) >= ((int)x.right.value)) ? 1 : 0;
				break;
			case SHL:
				x.value=((int)x.left.value)<<((int)x.right.value);
				break;
			case SHR:
				x.value=((int)x.left.value)>>((int)x.right.value);
				break;
			case PLUS:
				x.value=((int)x.left.value)+((int)x.right.value);
				break;
			case MINUS:
				x.value=((int)x.left.value)-((int)x.right.value);
				break;
			case TIMES:
				x.value=((int)x.left.value)*((int)x.right.value);
				break;
			case DIVIDE:
				if((int)x.right.value == 0)
					panic("Dividend shall not be zero!");
				else
					x.value=((int)x.left.value)/((int)x.right.value);
				break;
			case MODULE:
				x.value=((int)x.left.value)%((int)x.right.value);
				break;
			}
		}
	}

	@Override
	public void visit(CastExpr x) throws Exception
	{
		x.target_type.accept(this);
		x.expr.accept(this);

		x.isConst = false;
		x.value = null;
		x.hasInitialized = false;
		x.type = null;
		x.isLvalue = false;

		if (x.target_type.star_list.cnt == 0)
		{
			TypeSpecifier.Type ct = x.target_type.type_specifier.type;
			if (ct == TypeSpecifier.Type.CHAR || ct == TypeSpecifier.Type.INT)
			{
				if (x.expr.isConst)
				{
					x.isConst = true;
					x.value = (int) x.expr.value;
					x.hasInitialized = true;
					x.type = Int.getInstance();
					x.isLvalue = false;
				}
			}
		}
	}

	@Override
	public void visit(UnaryExpr x) throws Exception
	{
		if (x.expr != null)
			x.expr.accept(this);
		if (x.type_name != null)
			x.type_name.accept(this);

		if (x.op == UnaryExpr.Operator.NEGATIVE && x.expr.isConst)
		{
			x.isConst = true;
			x.hasInitialized = true;
			x.type = Int.getInstance();
			x.isLvalue = false;

			int val = (int) x.expr.value;
			x.value = -val;
		}
		else if (x.op == UnaryExpr.Operator.POSITIVE && x.expr.isConst)
		{
			x.isConst = true;
			x.hasInitialized = true;
			x.type = Int.getInstance();
			x.isLvalue = false;
			x.value = (int) x.expr.value;
		}
		else
		{
			x.isConst = false;
			x.value = null;
			x.hasInitialized = false;
			x.type = null;
			x.isLvalue = false;
		}
	}

	@Override
	public void visit(PostfixExpr x) throws Exception
	{
		x.expr.accept(this);
		if (x.param != null)
		{
			if (x.param instanceof Expression)
				((Expression) x.param).accept(this);
			else
				((ArgumentList) x.param).accept(this);
		}

		x.isConst = false;
		x.value = null;
		x.hasInitialized = false;
		x.type = null;
		x.isLvalue = false;
	}

	@Override
	public void visit(PrimaryExpr x) throws Exception
	{
		// only integer and character are constants
		switch (x.elem_type)
		{
		case INT:
			x.isConst = true;
			x.value = x.elem;
			x.hasInitialized = true;
			x.type = Int.getInstance();
			x.isLvalue = false;
			break;
		case CHAR:
			x.isConst = true;
			x.value = x.elem;
			x.hasInitialized = true;
			x.type = Char.getInstance();
			x.isLvalue = false;
			break;
		case STRING:
			x.isConst = false;
			x.value = x.elem;
			x.hasInitialized = true;
			x.type = new Pointer(Char.getInstance());
			x.isLvalue = false;
			break;
		case ID:
			x.isConst = false;
			x.value = null;
			x.hasInitialized = false;
			x.type = null;
			x.isLvalue = false;
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
		x.declaration_list.accept(this);
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
		x.init_declarator_list.accept(this);
	}

	@Override
	public void visit(FuncDeclarator x) throws Exception
	{
		x.plain_declarator.accept(this);
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
	}

	@Override
	public void visit(TypeSpecifier x) throws Exception
	{
		switch (x.type)
		{
		case STRUCT:
		case UNION:
			x.comp.accept(this);
			break;
		default:
			break;
		}
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
