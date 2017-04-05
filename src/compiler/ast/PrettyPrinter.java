package compiler.ast;

import java.util.*;

public class PrettyPrinter implements ASTNodeVisitor
{
	// for simplicity, if an record appears in TypeName
	// or sizeof or ParameterList or any other inconvenient
	// places, just convert it into one line

	@Override
	public void visit(Expression x) throws Exception
	{
		Expression y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}

		x.code_rep = new String[1];

		y = x;
		x.code_rep[0] += y.head.code_rep[0];
		y = y.next;

		while (y != null)
		{
			x.code_rep[0] += ", ";
			x.code_rep[0] += y.head.code_rep[0];
			y = y.next;
		}
	}

	@Override
	public void visit(AssignmentExpr x) throws Exception
	{
		x.left.accept(this);
		x.right.accept(this);

		x.code_rep = new String[1];
		x.code_rep[0] += x.left.code_rep[0];
		x.code_rep[0] += (" " + x.getOperator() + " ");
		x.code_rep[0] += x.right.code_rep[0];
	}

	@Override
	public void visit(BinaryExpr x) throws Exception
	{
		x.left.accept(this);
		x.right.accept(this);

		x.code_rep = new String[1];
		x.code_rep[0] += x.left.code_rep[0];
		x.code_rep[0] += (" " + x.getOperator() + " ");
		x.code_rep[0] += x.right.code_rep[0];
	}

	@Override
	public void visit(CastExpr x) throws Exception
	{
		x.target_type.accept(this);
		x.expr.accept(this);

		x.code_rep = new String[0];
		x.code_rep[0] += "(";
		x.code_rep[0] += x.target_type.code_rep[0];
		x.code_rep[0] += ") ";
		x.code_rep[0] += x.expr.code_rep[0];
	}

	@Override
	public void visit(UnaryExpr x) throws Exception
	{
		if (x.expr != null)
			x.expr.accept(this);
		if (x.type_name != null)
			x.type_name.accept(this);

		x.code_rep = new String[1];
		x.code_rep[0] += x.getOperator();
		if (x.type_name != null)
		{
			x.code_rep[0] += "(";
			x.code_rep[0] += x.type_name.code_rep[0];
			x.code_rep[0] += ")";
		}
		else
		{
			x.code_rep[0] += " ";
			x.code_rep[0] += x.expr.code_rep[0];
		}
	}

	@Override
	public void visit(PostfixExpr x) throws Exception
	{
		x.expr.accept(this);
		x.code_rep = new String[1];
		x.code_rep[0] += x.expr.code_rep[0];

		switch (x.op)
		{
		case INC:
		case DEC:
			x.code_rep[0] += x.getOperator();
			break;
		case DOT:
		case PTR:
			x.code_rep[0] += x.getOperator();
			x.code_rep[0] += (String) x.param;
			break;
		case MPAREN:
			Expression e = (Expression) x.param;
			e.accept(this);
			x.code_rep[0] += "[";
			x.code_rep[0] += e.code_rep[0];
			x.code_rep[0] += "]";
			break;
		case PAREN:
			ArgumentList args = (ArgumentList) x.param;
			args.accept(this);
			x.code_rep[0] += "(";
			x.code_rep[0] += args.code_rep[0];
			x.code_rep[0] += ")";
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(PrimaryExpr x) throws Exception
	{
		x.code_rep = new String[1];
		switch (x.elem_type)
		{
		case ID:
		case STRING:
			x.code_rep[0] += (String) x.elem;
			break;
		case CHAR:
			x.code_rep[0] += (Character) x.elem;
			break;
		case INT:
			x.code_rep[0] += ((Integer) x.elem).toString();
			break;
		case PAREN:
			Expression e = (Expression) x.elem;
			e.accept(this);
			x.code_rep[0] += "(";
			x.code_rep[0] += e.code_rep[0];
			x.code_rep[0] += ")";
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(StmtList x) throws Exception
	{
		int lc = 0;
		StmtList y = x;
		while (y != null)
		{
			y.head.accept(this);
			lc += y.head.code_rep.length;
			y = y.next;
		}

		x.code_rep = new String[lc];
		int cl = 0;
		y = x;
		while (y != null)
		{
			for (String str : y.head.code_rep)
				x.code_rep[cl++] += str;
			y = y.next;
		}
	}

	@Override
	public void visit(ExpressionStmt x) throws Exception
	{
		x.code_rep = new String[1];
		if (x.e != null)
		{
			x.e.accept(this);
			x.code_rep[0] += x.e.code_rep[0];
		}

		x.code_rep[0] += ";";
	}

	@Override
	public void visit(CompoundStmt x) throws Exception
	{
		int lc = 0;
		if (x.declaration_list != null)
		{
			x.declaration_list.accept(this);
			lc += x.declaration_list.code_rep.length;
		}
		if (x.stmt_list != null)
		{
			x.stmt_list.accept(this);
			lc += x.stmt_list.code_rep.length;
		}

		x.code_rep = new String[lc + 2];
		int cl = 0;
		x.code_rep[cl++] += "{";

		if (x.declaration_list != null)
			for (String str : x.declaration_list.code_rep)
				x.code_rep[cl++] += ("\t" + str);

		if (x.stmt_list != null)
			for (String str : x.stmt_list.code_rep)
				x.code_rep[cl++] += ("\t" + str);

		x.code_rep[cl] += "}";
	}

	@Override
	public void visit(SelectionStmt x) throws Exception
	{
		int lc = 0;
		x.cond.accept(this);
		lc += x.cond.code_rep.length;

		x.if_clause.accept(this);
		lc += 2;
		lc += x.if_clause.code_rep.length;

		if (x.else_clause != null)
		{
			lc += 3;
			x.else_clause.accept(this);
			lc += x.else_clause.code_rep.length;
		}

		x.code_rep = new String[lc];
		int cl = 0;
		x.code_rep[cl] += "if(";
		x.code_rep[cl] += x.cond.code_rep[0];
		x.code_rep[cl++] += ")";
		x.code_rep[cl++] += "{";
		for (String str : x.if_clause.code_rep)
			x.code_rep[cl++] += ("\t" + str);
		x.code_rep[cl++] += "}";
		if (x.else_clause != null)
		{
			x.code_rep[cl++] += "else";
			x.code_rep[cl++] += "{";
			for (String str : x.else_clause.code_rep)
				x.code_rep[cl++] += ("\t" + str);
			x.code_rep[cl++] += "}";
		}
	}

	@Override
	public void visit(JumpStmt x) throws Exception
	{
		x.code_rep = new String[1];

		switch (x.type)
		{
		case RETURN:
			x.code_rep[0] += "return";
			if (x.expr != null)
			{
				x.expr.accept(this);
				x.code_rep[0] += " ";
				x.code_rep[0] += x.expr.code_rep[0];
			}
			x.code_rep[0] += ";";
			break;
		case CONTINUE:
			x.code_rep[0] += "continue;";
			break;
		case BREAK:
			x.code_rep[0] += "break;";
			break;
		default:
			break;
		}
	}

	@Override
	public void visit(IterationStmt x) throws Exception
	{
		int lc = 0;
		if (x.iteration_type == IterationStmt.Type.FOR)
		{
			if (x.init != null)
				x.init.accept(this);
			if (x.judge != null)
				x.judge.accept(this);
			if (x.next != null)
				x.next.accept(this);

			lc += 3;
			x.stmt.accept(this);
			lc += x.stmt.code_rep.length;

			x.code_rep = new String[lc];
			int cl = 0;
			x.code_rep[cl] = "for(";
			if (x.init != null)
				x.code_rep[cl] += x.init.code_rep[0];
			x.code_rep[cl] += ";";
			if (x.judge != null)
				x.code_rep[cl] += x.judge.code_rep[0];
			x.code_rep[cl] += ";";
			if (x.next != null)
				x.code_rep[cl] += x.next.code_rep[0];
			x.code_rep[cl++] += ")";
			x.code_rep[cl++] += "{";
			for (String str : x.stmt.code_rep)
				x.code_rep[cl++] += ("\t" + str);
			x.code_rep[cl++] += "}";
		}
		else
		{
			x.judge.accept(this);
			x.stmt.accept(this);
			lc = 3 + x.stmt.code_rep.length;
			x.code_rep = new String[lc];

			int cl = 0;
			x.code_rep[cl] += "while(";
			x.code_rep[cl] += x.judge.code_rep[0];
			x.code_rep[cl++] += ")";
			x.code_rep[cl++] += "{";
			for (String str : x.stmt.code_rep)
				x.code_rep[cl++] += ("\t" + str);
			x.code_rep[cl++] += "}";
		}
	}

	@Override
	public void visit(StarList x) throws Exception
	{
		x.code_rep = new String[1];
		x.code_rep[0] = "";
		for (int i = 0; i < x.cnt; i++)
			x.code_rep[0] += "*";
	}

	@Override
	public void visit(Declaration x) throws Exception
	{
		x.type_specifier.accept(this);
		if (x.init_declarator_list != null)
			x.init_declarator_list.accept(this);

		int lc = x.type_specifier.code_rep.length, cl = 0;
		x.code_rep = new String[lc];

		for (String str : x.type_specifier.code_rep)
			x.code_rep[cl++] += str;

		--cl;
		if (x.init_declarator_list != null)
			x.code_rep[cl] += x.init_declarator_list.code_rep[0];
		x.code_rep[cl] += ";";
	}

	@Override
	public void visit(FuncDeclarator x) throws Exception
	{
		x.code_rep = new String[1];

		x.plain_declarator.accept(this);
		x.code_rep[0] += x.plain_declarator.code_rep[0];

		if (x.param != null)
		{
			x.param.accept(this);
			x.code_rep[0] += x.param.code_rep[0];
		}
	}

	@Override
	public void visit(VarDeclarator x) throws Exception
	{
		x.plain_declarator.accept(this);
		x.code_rep = new String[1];
		x.code_rep[0] += x.plain_declarator.code_rep[0];
		Iterator<Expr> it = x.dimension.iterator();
		while (it.hasNext())
		{
			Expr ce = it.next();
			ce.accept(this);
			x.code_rep[0] += "[";
			x.code_rep[0] += ce.code_rep[0];
			x.code_rep[0] += "]";
		}
	}

	@Override
	public void visit(DeclarationList x) throws Exception
	{
		int lc = 0;
		DeclarationList y = x;
		while (y != null)
		{
			y.head.accept(this);
			lc += y.head.code_rep.length;
			y = y.next;
		}

		x.code_rep = new String[lc];
		int cl = 0;
		y = x;
		while (y != null)
		{
			for (String str : y.head.code_rep)
				x.code_rep[cl++] += str;
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

		x.code_rep = new String[1];
		y = x;
		x.code_rep[0] += y.head.code_rep[0];
		y = y.next;
		while (y != null)
		{
			x.code_rep[0] += ", ";
			x.code_rep[0] += y.head.code_rep[0];
			y = y.next;
		}
	}

	@Override
	public void visit(InitDeclarator x) throws Exception
	{
		x.declarator.accept(this);
		x.initializer.accept(this);

		x.code_rep = new String[1];
		x.code_rep[0] += x.declarator.code_rep[0];
		x.code_rep[0] += " = ";
		x.code_rep[0] += x.initializer.code_rep[0];
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

		x.code_rep = new String[1];
		y = x;
		x.code_rep[0] += y.head.code_rep[0];
		y = y.next;

		while (y != null)
		{
			x.code_rep[0] += ", ";
			x.code_rep[0] += y.head.code_rep[0];
			y = y.next;
		}
	}

	@Override
	public void visit(Initializer x) throws Exception
	{
		if (x.expr != null)
		{
			x.expr.accept(this);
			int lc = x.expr.code_rep.length, cl = 0;
			x.code_rep = new String[lc];
			for (String str : x.expr.code_rep)
				x.code_rep[cl++] += str;
		}
		else
		{
			x.initializer_list.accept(this);
			x.code_rep = new String[1];
			x.code_rep[0] += x.initializer_list.code_rep[0];
		}
	}

	@Override
	public void visit(InitializerList x) throws Exception
	{
		// put all in one line for simplicity

		InitializerList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}

		x.code_rep = new String[1];
		x.code_rep[0] += "{";

		y = x;
		x.code_rep[0] += y.head.code_rep[0];
		y = y.next;
		while (y != null)
		{
			x.code_rep[0] += (", " + y.head.code_rep[0]);
			y = y.next;
		}

		x.code_rep[0] += "}";
	}

	@Override
	public void visit(NonInitDeclaration x) throws Exception
	{
		x.type_specifier.accept(this);
		x.declarator_list.accept(this);

		int lc = x.type_specifier.code_rep.length;
		x.code_rep = new String[lc];

		int cl = 0;
		for (String str : x.type_specifier.code_rep)
			x.code_rep[cl++] += str;

		--cl;
		x.code_rep[cl] += (x.declarator_list.code_rep[0] + ";");
	}

	@Override
	public void visit(NonInitDeclarationList x) throws Exception
	{
		int lc = 0;
		NonInitDeclarationList y = x;
		while (y != null)
		{
			y.head.accept(this);
			lc += y.head.code_rep.length;
			y = y.next;
		}

		x.code_rep = new String[lc];
		int cl = 0;
		y = x;
		while (y != null)
		{
			for (String str : y.head.code_rep)
				x.code_rep[cl++] += str;

			y = y.next;
		}
	}

	@Override
	public void visit(PlainDeclaration x) throws Exception
	{
		x.type_specifier.accept(this);
		x.declarator.accept(this);

		int lc = x.type_specifier.code_rep.length + x.declarator.code_rep.length - 1;
		x.code_rep = new String[lc];

		int cl = 0;
		for (String str : x.type_specifier.code_rep)
			x.code_rep[cl++] += str;

		--cl;
		x.code_rep[cl] += " ";
		int sc = x.code_rep[cl].length();
		String space = "";
		for (int i = 0; i < sc; i++)
			space += " ";

		String[] tmp = new String[x.declarator.code_rep.length];
		tmp[0] += x.declarator.code_rep[0];
		for (int i = 1; i < x.declarator.code_rep.length; i++)
			tmp[i] += (space + x.declarator.code_rep[i]);

		for (String str : tmp)
			x.code_rep[cl++] += str;
	}

	@Override
	public void visit(PlainDeclarator x) throws Exception
	{
		x.star_list.accept(this);
		x.code_rep = new String[1];

		x.code_rep[0] += x.star_list.code_rep[0];
		x.code_rep[0] += x.identifier;
	}

	@Override
	public void visit(FuncDef x) throws Exception
	{
		int lc = 0;
		x.type_specifier.accept(this);
		lc += x.type_specifier.code_rep.length;
		x.func_name.accept(this);
		lc += x.func_name.code_rep.length;
		if (x.params != null)
		{
			x.params.accept(this);
			lc += x.params.code_rep.length;
		}
		x.comp_stmt.accept(this);
		lc += x.comp_stmt.code_rep.length;

		lc -= 2;
		x.code_rep = new String[lc];

		int cl = 0;
		for (String str : x.type_specifier.code_rep)
			x.code_rep[cl++] += str;

		--cl;
		x.code_rep[cl] += (" " + x.func_name.code_rep[0]);
		if (x.params == null)
			x.code_rep[cl++] += "()";
		else if (x.params.code_rep.length == 1)
		{
			x.code_rep[cl] += "(";
			x.code_rep[cl] += x.params.code_rep[0];
			x.code_rep[cl++] += ")";
		}
		else
		{
			int sc = x.code_rep[cl].length();
			String space = "";
			for (int i = 0; i < sc; i++)
				space += " ";

			String[] tmp = new String[x.params.code_rep.length];
			tmp[0] += x.params.code_rep[0];
			for (int i = 1; i < x.params.code_rep.length; i++)
				tmp[i] += (space + x.params.code_rep[i]);

			x.code_rep[cl] += "(";
			for (int i = 0; i < tmp.length; i++)
				x.code_rep[cl++] += tmp[i];
			x.code_rep[cl - 1] += ")";
		}

		for (String str : x.comp_stmt.code_rep)
			x.code_rep[cl++] += str;
	}

	@Override
	public void visit(ArgumentList x) throws Exception
	{
		boolean multi = false;
		ArgumentList y = x;
		while (y != null)
		{
			y.head.accept(this);
			if (y.head.code_rep.length > 1 && !multi)
				multi = true;
			y = y.next;
		}

		if (!multi)
		{
			x.code_rep = new String[1];
			y = x;
			x.code_rep[0] += y.head.code_rep[0];
			y = y.next;
			while (y != null)
			{
				x.code_rep[0] += (", " + y.head.code_rep[0]);
				y = y.next;
			}
		}
		else
		{
			int lc = 0;
			y = x;
			while (y != null)
			{
				lc += y.head.code_rep.length;
				y = y.next;
			}

			x.code_rep = new String[lc];
			int cl = 0;
			y = x;
			for (String str : y.head.code_rep)
				x.code_rep[cl++] += str;
			y = y.next;
			while (y != null)
			{
				x.code_rep[cl - 1] += ",";
				for (String str : y.head.code_rep)
					x.code_rep[cl++] += str;
				y = y.next;
			}
		}
	}

	@Override
	public void visit(ParameterList x) throws Exception
	{
		boolean multi = false;
		ParameterList y = x;
		while (y != null)
		{
			y.head.accept(this);
			if (y.head.code_rep.length > 1 && !multi)
				multi = true;
			y = y.next;
		}

		if (!multi)
		{
			x.code_rep = new String[1];
			y = x;
			x.code_rep[0] += y.head.code_rep[0];
			y = y.next;
			while (y != null)
			{
				x.code_rep[0] += (", " + y.head.code_rep[0]);
				y = y.next;
			}
		}
		else
		{
			int lc = 0;
			y = x;
			while (y != null)
			{
				lc += y.head.code_rep.length;
				y = y.next;
			}

			x.code_rep = new String[lc];
			int cl = 0;
			y = x;
			for (String str : y.head.code_rep)
				x.code_rep[cl++] += str;
			y = y.next;
			while (y != null)
			{
				x.code_rep[cl - 1] += ",";
				for (String str : y.head.code_rep)
					x.code_rep[cl++] += str;
				y = y.next;
			}
		}
	}

	@Override
	public void visit(TypeName x) throws Exception
	{
		int lc = 0;
		x.type_specifier.accept(this);
		x.star_list.accept(this);

		lc += x.type_specifier.code_rep.length;
		x.code_rep = new String[lc];
		int cl = 0;
		for (String str : x.type_specifier.code_rep)
			x.code_rep[cl++] += str;

		if (x.star_list.cnt > 0)
			x.code_rep[cl] += (" " + x.star_list.code_rep[0]);
	}

	@Override
	public void visit(TypeSpecifier x) throws Exception
	{
		switch (x.type)
		{
		case VOID:
			x.code_rep = new String[1];
			x.code_rep[0] = "void";
			return;
		case INT:
			x.code_rep = new String[1];
			x.code_rep[0] = "int";
			return;
		case CHAR:
			x.code_rep = new String[1];
			x.code_rep[0] = "char";
			return;
		default:
			break;
		}

		if (x.comp == null)
		{
			x.code_rep = new String[1];
			x.code_rep[0] = (x.type == TypeSpecifier.Type.STRUCT ? "struct" : "union");
			if (x.tag != null)
				x.code_rep[0] += (" " + x.tag);
		}
		else
		{
			int lc = 3;
			x.comp.accept(this);
			lc += x.comp.code_rep.length;
			x.code_rep = new String[lc];

			x.code_rep[0] = (x.type == TypeSpecifier.Type.STRUCT ? "struct" : "union");
			if (x.tag != null)
				x.code_rep[0] += (" " + x.tag);

			x.code_rep[1] = "{";
			x.code_rep[lc - 1] = "}";

			int cl = 2;
			for (String str : x.comp.code_rep)
				x.code_rep[cl++] += ("\t" + str);
		}
	}

	@Override
	public void visit(Program x) throws Exception
	{
		Program y = x;
		int lc = 0;
		while (y != null)
		{
			y.head.accept(this);
			lc += (y.head.code_rep.length + 1);
			y = y.next;
		}

		x.code_rep = new String[lc];
		int cl = 0;
		y = x;
		while (y != null)
		{
			for (String str : y.head.code_rep)
				x.code_rep[cl++] += str;
			cl++;
		}
	}
}
