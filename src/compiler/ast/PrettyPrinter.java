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
