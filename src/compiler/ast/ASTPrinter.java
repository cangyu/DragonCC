package compiler.ast;

import java.util.Iterator;

public class ASTPrinter implements ASTNodeVisitor
{
	final private static String leading = "--";
	final private static String seperator = "    |";

	/* Expr */
	public void visit(Expression exp)
	{
		// construct nodes and count lines
		Expression ce = exp;
		int lc = 1;
		while (ce != null)
		{
			ce.head.accept(this);
			lc += ce.head.ast_rep.length;
			ce = ce.next;
		}

		// initialize format
		exp.ast_rep = new String[lc];
		exp.ast_rep[0] = leading + "Expression";
		for (int i = 1; i < lc; i++)
			exp.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		ce = exp;
		while (ce != null)
		{
			for (String str : ce.head.ast_rep)
				exp.ast_rep[cl++] += str;

			ce = ce.next;
		}
	}

	public void visit(AssignmentExpr ae)
	{
		// construct nodes and count lines
		int lc = 2;
		ae.left.accept(this);
		lc += ae.left.ast_rep.length;
		ae.right.accept(this);
		lc += ae.right.ast_rep.length;

		// initialize format
		ae.ast_rep = new String[lc];
		ae.ast_rep[0] = leading + "AssignmentExpr";
		for (int i = 1; i < lc; i++)
			ae.ast_rep[i] = seperator;

		// add sub-nodes' content
		ae.ast_rep[1] += leading + "Operator: ";
		switch (ae.op)
		{
		case ASSIGN:
			ae.ast_rep[1] += "=";
			break;
		case MUL_ASSIGN:
			ae.ast_rep[1] += "*=";
			break;
		case DIV_ASSIGN:
			ae.ast_rep[1] += "/=";
			break;
		case MOD_ASSIGN:
			ae.ast_rep[1] += "%=";
			break;
		case ADD_ASSIGN:
			ae.ast_rep[1] += "+=";
			break;
		case SUB_ASSIGN:
			ae.ast_rep[1] += "-=";
			break;
		case SHL_ASSIGN:
			ae.ast_rep[1] += "<<=";
			break;
		case SHR_ASSIGN:
			ae.ast_rep[1] += ">>=";
			break;
		case AND_ASSIGN:
			ae.ast_rep[1] += "&=";
			break;
		case XOR_ASSIGN:
			ae.ast_rep[1] += "^=";
			break;
		case OR_ASSIGN:
			ae.ast_rep[1] += "|=";
			break;
		default:
			break;
		}

		int cl = 2;
		for (String str : ae.left.ast_rep)
			ae.ast_rep[cl++] += str;

		for (String str : ae.right.ast_rep)
			ae.ast_rep[cl++] += str;
	}

	public void visit(BinaryExpr be)
	{
		// construct sub-nodes and count lines
		int lc = 2;
		be.left.accept(this);
		lc += be.left.ast_rep.length;
		be.right.accept(this);
		lc += be.right.ast_rep.length;

		// initialize format
		be.ast_rep = new String[lc];
		be.ast_rep[0] = leading + "BinaryExpr";
		for (int i = 1; i < lc; i++)
			be.ast_rep[i] = seperator;

		// add sub-nodes' content
		be.ast_rep[1] += leading + "Operator: ";
		switch (be.op)
		{
		case BIT_AND:
			be.ast_rep[1] += "&";
			break;
		case BIT_XOR:
			be.ast_rep[1] += "^";
			break;
		case BIT_OR:
			be.ast_rep[1] += "|";
			break;
		case AND:
			be.ast_rep[1] += "&&";
			break;
		case OR:
			be.ast_rep[1] += "||";
			break;
		case EQ:
			be.ast_rep[1] += "==";
			break;
		case NE:
			be.ast_rep[1] += "!=";
			break;
		case LT:
			be.ast_rep[1] += "<";
			break;
		case GT:
			be.ast_rep[1] += ">";
			break;
		case LE:
			be.ast_rep[1] += "<=";
			break;
		case GE:
			be.ast_rep[1] += ">=";
			break;
		case SHL:
			be.ast_rep[1] += "<<";
			break;
		case SHR:
			be.ast_rep[1] += ">>";
			break;
		case PLUS:
			be.ast_rep[1] += "+";
			break;
		case MINUS:
			be.ast_rep[1] += "-";
			break;
		case TIMES:
			be.ast_rep[1] += "*";
			break;
		case DIVIDE:
			be.ast_rep[1] += "/";
			break;
		case MODULE:
			be.ast_rep[1] += "%";
			break;
		default:
			break;
		}

		int cl = 2;
		for (String str : be.left.ast_rep)
			be.ast_rep[cl++] += str;

		for (String str : be.right.ast_rep)
			be.ast_rep[cl++] += str;
	}

	public void visit(CastExpr ce)
	{
		// construct sub-nodes
		ce.target_type.accept(this);
		ce.expr.accept(this);

		// count lines
		int lc = 1;
		lc += ce.target_type.ast_rep.length;
		lc += ce.expr.ast_rep.length;

		// initialize format
		ce.ast_rep = new String[lc];
		ce.ast_rep[0] = leading + "CastExpr";
		for (int i = 1; i < lc; i++)
			ce.ast_rep[i] = seperator;

		// add sub-nodes' content
		int cl = 1;
		for (String str : ce.target_type.ast_rep)
			ce.ast_rep[cl++] += str;

		for (String str : ce.expr.ast_rep)
			ce.ast_rep[cl++] += str;
	}

	public void visit(UnaryExpr ue)
	{
		// construct sub-nodes
		if (ue.expr != null)
			ue.expr.accept(this);

		if (ue.type_name != null)
			ue.type_name.accept(this);

		// count lines
		int lc = 2;
		lc += ue.expr.ast_rep.length;

		if (ue.type_name != null)
			lc += ue.type_name.ast_rep.length;

		// initialize format
		ue.ast_rep = new String[lc];
		ue.ast_rep[0] = leading + "UnaryExpr";
		for (int i = 1; i < lc; i++)
			ue.ast_rep[i] = seperator;

		// add sub-nodes' content
		ue.ast_rep[1] += "--Operator: ";
		switch (ue.op)
		{
		case BIT_AND:
			ue.ast_rep[1] += "&";
			break;
		case STAR:
			ue.ast_rep[1] += "*";
			break;
		case POSITIVE:
			ue.ast_rep[1] += "+";
			break;
		case NEGATIVE:
			ue.ast_rep[1] += "-";
			break;
		case BIT_NOT:
			ue.ast_rep[1] += "~";
			break;
		case NOT:
			ue.ast_rep[1] += "!";
			break;
		case SIZEOF:
			ue.ast_rep[1] += "SIZEOF";
			break;
		case INC:
			ue.ast_rep[1] += "++";
			break;
		case DEC:
			ue.ast_rep[1] += "--";
			break;
		default:
			break;
		}

		int cl = 2;
		if (ue.expr != null)
			for (String str : ue.expr.ast_rep)
				ue.ast_rep[cl++] += str;

		if (ue.type_name != null)
			for (String str : ue.type_name.ast_rep)
				ue.ast_rep[cl++] += str;
	}

	public void visit(PostfixExpr pe)
	{
		// construct and count
		int lc = 2;
		pe.expr.accept(this);
		lc += pe.expr.ast_rep.length;

		if (pe.param != null)
		{
			if (pe.op == PostfixExpr.Operator.MPAREN)
			{
				Expression es = (Expression) pe.param;
				es.accept(this);
				lc += es.ast_rep.length;
			}
			else if (pe.op == PostfixExpr.Operator.PAREN)
			{
				ArgumentList al = (ArgumentList) pe.param;
				al.accept(this);
				lc += al.ast_rep.length;
			}
			else
				lc += 1;
		}

		// initialize format
		pe.ast_rep = new String[lc];

		pe.ast_rep[0] = leading + "PostfixExpr";

		for (int i = 1; i < lc; i++)
			pe.ast_rep[i] = seperator;

		// add contents
		int cl = 1;

		for (String str : pe.expr.ast_rep)
			pe.ast_rep[cl++] += str;

		pe.ast_rep[cl] += leading + "Operator: ";
		switch (pe.op)
		{
		case MPAREN:
			pe.ast_rep[cl++] += "[]";
			break;
		case PAREN:
			pe.ast_rep[cl++] += "()";
			break;
		case DOT:
			pe.ast_rep[cl++] += ".";
			break;
		case PTR:
			pe.ast_rep[cl++] += "->";
			break;
		case INC:
			pe.ast_rep[cl++] += "++";
			break;
		case DEC:
			pe.ast_rep[1] += "--";
			break;
		default:
			pe.ast_rep[1] += "";
			break;
		}

		if (pe.param != null)
		{
			if (pe.op == PostfixExpr.Operator.MPAREN)
			{
				Expression es = (Expression) pe.param;
				for (String str : es.ast_rep)
					pe.ast_rep[cl++] += str;
			}
			else if (pe.op == PostfixExpr.Operator.PAREN)
			{
				ArgumentList al = (ArgumentList) pe.param;
				for (String str : al.ast_rep)
					pe.ast_rep[cl++] += str;
			}
			else
			{
				String str = (String) pe.param;
				pe.ast_rep[cl] += leading + "Identifier: ";
				pe.ast_rep[cl++] += str;
			}
		}
	}

	public void visit(PrimaryExpr pe)
	{
		pe.ast_rep = new String[1];
		pe.ast_rep[0] = "--";

		switch (pe.elem_type)
		{
		case ID:
			pe.ast_rep[0] += "Identifier: " + (String) pe.elem;
			break;
		case STRING:
			pe.ast_rep[0] += "String: " + '\"' + (String) pe.elem + '\"';
			break;
		case INT:
			pe.ast_rep[0] += "Integer-Constant: " + ((Integer) pe.elem).toString();
			break;
		case CHAR:
			pe.ast_rep[0] += "Character-Constant: " + '\'' + (Character) pe.elem + '\'';
			break;
		default:
			break;
		}
	}

	/* Stmt */
	public void visit(StmtList sl)
	{
		// construct sub-nodes
		StmtList s = sl;
		while (s != null)
		{
			s.head.accept(this);
			s = s.next;
		}

		// count lines
		int lc = 1;
		s = sl;
		while (s != null)
		{
			lc += s.head.ast_rep.length;
			s = s.next;
		}

		// initialize format
		sl.ast_rep = new String[lc];
		sl.ast_rep[0] = leading + "StmtList";
		for (int i = 1; i < lc; i++)
			sl.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		s = sl;
		while (s != null)
		{
			for (String str : s.head.ast_rep)
				sl.ast_rep[cl++] += str;

			s = s.next;
		}
	}

	public void visit(ExpressionStmt es)
	{
		if (es.e != null)
		{
			es.e.accept(this);
			es.ast_rep = es.e.ast_rep;
		}
	}

	public void visit(CompoundStmt cs)
	{
		// construct components and count lines
		int lc = 1;
		if (cs.declaration_list != null)
		{
			cs.declaration_list.accept(this);
			lc += cs.declaration_list.ast_rep.length;
		}

		if (cs.stmt_list != null)
		{
			cs.stmt_list.accept(this);
			lc += cs.stmt_list.ast_rep.length;
		}

		// initialize format
		cs.ast_rep = new String[lc];
		cs.ast_rep[0] = leading + "CompoundStmt";
		for (int i = 1; i < lc; i++)
			cs.ast_rep[i] = seperator;

		// add contents
		int cl = 1;

		if (cs.declaration_list != null)
			for (String str : cs.declaration_list.ast_rep)
				cs.ast_rep[cl++] += str;

		if (cs.stmt_list != null)
			for (String str : cs.stmt_list.ast_rep)
				cs.ast_rep[cl++] += str;
	}

	public void visit(SelectionStmt ss)
	{
		// construct sub-nodes and count lines
		int lc = 1;

		ss.cond.accept(this);
		lc += ss.cond.ast_rep.length;

		ss.if_clause.accept(this);
		lc += ss.if_clause.ast_rep.length;

		if (ss.else_clause != null)
		{
			ss.else_clause.accept(this);
			lc += ss.else_clause.ast_rep.length;
		}

		// initialize format
		ss.ast_rep = new String[lc];
		ss.ast_rep[0] = leading + "SelectionStmt";
		for (int i = 1; i < lc; i++)
			ss.ast_rep[i] = seperator;

		// add contents
		int cl = 1;

		for (String str : ss.cond.ast_rep)
			ss.ast_rep[cl++] += str;

		for (String str : ss.if_clause.ast_rep)
			ss.ast_rep[cl++] += str;

		if (ss.else_clause != null)
			for (String str : ss.else_clause.ast_rep)
				ss.ast_rep[cl++] += str;
	}

	public void visit(JumpStmt js)
	{
		// construct sub-nodes and count lines
		int lc = 1;
		if (js.expr != null)
		{
			js.expr.accept(this);
			lc += js.expr.ast_rep.length;
		}

		js.ast_rep = new String[lc];

		// initialize format
		js.ast_rep[0] = leading + "JumpStmt: ";
		for (int i = 1; i < lc; i++)
			js.ast_rep[i] = seperator;

		// add components
		switch (js.type)
		{
		case CONTINUE:
			js.ast_rep[0] += "CONTINUE";
			break;
		case BREAK:
			js.ast_rep[0] += "BREAK";
			break;
		case RETURN:
			js.ast_rep[0] += "RETURN";
			break;
		default:
			break;
		}

		if (js.expr != null)
		{
			int cl = 1;
			for (String str : js.expr.ast_rep)
				js.ast_rep[cl++] += str;
		}
	}

	public void visit(IterationStmt is)
	{
		// construct sub-nodes and count lines
		int lc = 2;

		if (is.init != null)
		{
			is.init.accept(this);
			lc += is.init.ast_rep.length;
		}

		if (is.judge != null)
		{
			is.judge.accept(this);
			lc += is.judge.ast_rep.length;
		}

		if (is.next != null)
		{
			is.next.accept(this);
			lc += is.next.ast_rep.length;
		}

		is.stmt.accept(this);
		lc += is.stmt.ast_rep.length;

		// initialize format
		is.ast_rep = new String[lc];
		is.ast_rep[0] = leading + "IterationStmt";
		for (int i = 1; i < lc; i++)
			is.ast_rep[i] = seperator;

		// add contents
		is.ast_rep[1] += leading + "Type: ";
		switch (is.iteration_type)
		{
		case FOR:
			is.ast_rep[1] += "FOR_";
			break;
		case WHILE:
			is.ast_rep[1] += "WHILE";
			break;
		default:
			break;
		}

		int cl = 2;
		if (is.iteration_type == IterationStmt.Type.WHILE)
		{
			for (String str : is.judge.ast_rep)
				is.ast_rep[cl++] += str;

			for (String str : is.stmt.ast_rep)
				is.ast_rep[cl++] += str;
		}
		else
		{
			if (is.init != null)
			{
				is.ast_rep[1] += 'O';

				for (String str : is.init.ast_rep)
					is.ast_rep[cl++] += str;
			}
			else
				is.ast_rep[1] += 'X';

			if (is.judge != null)
			{
				is.ast_rep[1] += 'O';

				for (String str : is.judge.ast_rep)
					is.ast_rep[cl++] += str;
			}
			else
				is.ast_rep[1] += 'X';

			if (is.next != null)
			{
				is.ast_rep[1] += 'O';

				for (String str : is.next.ast_rep)
					is.ast_rep[cl++] += str;
			}
			else
				is.ast_rep[1] += 'X';

			for (String str : is.stmt.ast_rep)
				is.ast_rep[cl++] += str;
		}
	}

	/* Decl */
	public void visit(StarList sl)
	{
		sl.ast_rep = new String[1];
		sl.ast_rep[0] = String.format(leading + "StarList: %d", sl.cnt);
	}

	public void visit(Declaration decl)
	{
		// construct components and count lines
		int lc = 1;

		decl.type_specifier.accept(this);
		lc += decl.type_specifier.ast_rep.length;

		if (decl.init_declarator_list != null)
		{
			decl.init_declarator_list.accept(this);
			lc += decl.init_declarator_list.ast_rep.length;
		}

		// initialize format
		decl.ast_rep = new String[lc];
		decl.ast_rep[0] = leading + "Declaration";
		for (int i = 1; i < lc; i++)
			decl.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		for (String str : decl.type_specifier.ast_rep)
			decl.ast_rep[cl++] += str;

		if (decl.init_declarator_list != null)
			for (String str : decl.init_declarator_list.ast_rep)
				decl.ast_rep[cl++] += str;
	}

	public void visit(FuncDeclarator fd)
	{
		// construct components and count lines
		int lc = 1;

		fd.plain_declarator.accept(this);
		lc += fd.plain_declarator.ast_rep.length;

		if (fd.param != null)
		{
			fd.param.accept(this);
			lc += fd.param.ast_rep.length;
		}

		// initialize format
		fd.ast_rep = new String[lc];
		fd.ast_rep[0] = "--FuncDecl";
		for (int i = 1; i < lc; i++)
			fd.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		for (String str : fd.plain_declarator.ast_rep)
			fd.ast_rep[cl++] += str;

		if (fd.param != null)
			for (String str : fd.param.ast_rep)
				fd.ast_rep[cl++] += str;
	}

	public void visit(VarDeclarator vd)
	{
		// construct components and count lines
		vd.plain_declarator.accept(this);

		if (vd.dimension.size() != 0)
		{
			Iterator<Expr> it = vd.dimension.iterator();
			while (it.hasNext())
				it.next().accept(this);
		}

		// count lines
		int lc = 1;
		lc += vd.plain_declarator.ast_rep.length;
		if (vd.dimension.size() != 0)
		{
			Iterator<Expr> it = vd.dimension.iterator();
			while (it.hasNext())
				lc += it.next().ast_rep.length;
		}

		// initialize format
		vd.ast_rep = new String[lc];
		vd.ast_rep[0] = "--VarDecl";
		for (int i = 1; i < lc; i++)
			vd.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		for (String str : vd.plain_declarator.ast_rep)
			vd.ast_rep[cl++] += str;

		if (vd.dimension.size() != 0)
		{
			Iterator<Expr> it = vd.dimension.iterator();
			while (it.hasNext())
			{
				String[] _s = it.next().ast_rep;
				for (String str : _s)
					vd.ast_rep[cl++] += str;
			}
		}
	}

	public void visit(DeclarationList ds)
	{
		// construct components
		DeclarationList _dl = ds;
		while (_dl != null)
		{
			_dl.head.accept(this);
			_dl = _dl.next;
		}

		// count lines
		int lc = 1;
		_dl = ds;
		while (_dl != null)
		{
			lc += _dl.head.ast_rep.length;
			_dl = _dl.next;
		}

		// initialize format
		ds.ast_rep = new String[lc];
		ds.ast_rep[0] = leading + "DeclarationList";
		for (int i = 1; i < lc; i++)
			ds.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		_dl = ds;
		while (_dl != null)
		{
			for (String str : _dl.head.ast_rep)
				ds.ast_rep[cl++] += str;

			_dl = _dl.next;
		}
	}

	public void visit(DeclaratorList ds)
	{
		// construct components
		DeclaratorList _dl = ds;
		while (_dl != null)
		{
			_dl.head.accept(this);
			_dl = _dl.next;
		}

		// count lines
		int lc = 1;
		_dl = ds;
		while (_dl != null)
		{
			lc += _dl.head.ast_rep.length;
			_dl = _dl.next;
		}

		// initialize format
		ds.ast_rep = new String[lc];
		ds.ast_rep[0] = leading + "DeclaratorList";
		for (int i = 1; i < lc; i++)
			ds.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		_dl = ds;
		while (_dl != null)
		{
			for (String str : _dl.head.ast_rep)
				ds.ast_rep[cl++] += str;

			_dl = _dl.next;
		}
	}

	public void visit(InitDeclarator id)
	{
		// construct components and count lines
		int lc = 1;

		id.declarator.accept(this);
		lc += id.declarator.ast_rep.length;

		if (id.initializer != null)
		{
			id.initializer.accept(this);
			lc += id.initializer.ast_rep.length;
		}

		// initialize format
		id.ast_rep = new String[lc];
		id.ast_rep[0] = leading + "InitDeclarator";
		for (int i = 1; i < lc; i++)
			id.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		for (String str : id.declarator.ast_rep)
			id.ast_rep[cl++] += str;

		if (id.initializer != null)
			for (String str : id.initializer.ast_rep)
				id.ast_rep[cl++] += str;
	}

	public void visit(InitDeclaratorList ids)
	{
		// construct components
		InitDeclaratorList _dl = ids;
		while (_dl != null)
		{
			_dl.head.accept(this);
			_dl = _dl.next;
		}

		// count lines
		int lc = 1;
		_dl = ids;
		while (_dl != null)
		{
			lc += _dl.head.ast_rep.length;
			_dl = _dl.next;
		}

		// initialize format
		ids.ast_rep = new String[lc];
		ids.ast_rep[0] = leading + "InitDeclaratorList";
		for (int i = 1; i < lc; i++)
			ids.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		_dl = ids;
		while (_dl != null)
		{
			for (String str : _dl.head.ast_rep)
				ids.ast_rep[cl++] += str;

			_dl = _dl.next;
		}
	}

	public void visit(Initializer ini)
	{
		// construct components and count lines
		int lc = 1;

		if (ini.expr != null)
		{
			ini.expr.accept(this);
			lc += ini.expr.ast_rep.length;
		}

		if (ini.initializer_list != null)
		{
			ini.initializer_list.accept(this);
			lc += ini.initializer_list.ast_rep.length;
		}

		// initialize format
		ini.ast_rep = new String[lc];
		ini.ast_rep[0] = leading + "Initializer";
		for (int i = 1; i < lc; i++)
			ini.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		if (ini.expr != null)
			for (String str : ini.expr.ast_rep)
				ini.ast_rep[cl++] += str;

		if (ini.initializer_list != null)
			for (String str : ini.initializer_list.ast_rep)
				ini.ast_rep[cl++] += str;
	}

	public void visit(InitializerList x)
	{
		// construct components
		InitializerList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}

		// count lines
		int lc = 1;
		y = x;
		while (y != null)
		{
			lc += y.head.ast_rep.length;
			y = y.next;
		}

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "InitializerList";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		y = x;
		while (y != null)
		{
			for (String str : y.head.ast_rep)
				x.ast_rep[cl++] += str;

			y = y.next;
		}
	}

	public void visit(NonInitDeclaration x)
	{
		// construct components and count lines
		int lc = 1;
		x.type_specifier.accept(this);
		lc += x.type_specifier.ast_rep.length;

		x.declarator_list.accept(this);
		lc += x.declarator_list.ast_rep.length;

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "NonInitDeclaration";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = "   |";

		// add contents
		int cl = 1;
		for (String str : x.type_specifier.ast_rep)
			x.ast_rep[cl++] += str;

		for (String str : x.declarator_list.ast_rep)
			x.ast_rep[cl++] += str;
	}

	public void visit(NonInitDeclarationList x)
	{
		// construct components
		NonInitDeclarationList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}

		// count lines
		int lc = 1;
		y = x;
		while (y != null)
		{
			lc += y.head.ast_rep.length;
			y = y.next;
		}

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "NonInitDeclarationList";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		y = x;
		while (y != null)
		{
			for (String str : y.head.ast_rep)
				x.ast_rep[cl++] += str;

			y = y.next;
		}
	}

	public void visit(PlainDeclaration x)
	{
		// construct components and count lines
		int lc = 1;
		x.type_specifier.accept(this);
		lc += x.type_specifier.ast_rep.length;

		x.declarator.accept(this);
		lc += x.declarator.ast_rep.length;

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "PlainDeclaration";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		for (String str : x.type_specifier.ast_rep)
			x.ast_rep[cl++] += str;

		for (String str : x.declarator.ast_rep)
			x.ast_rep[cl++] += str;
	}

	public void visit(PlainDeclarator x)
	{
		// construct components and count lines
		int lc = 2;
		if (x.star_list.cnt > 0)
		{
			x.star_list.accept(this);
			lc += x.star_list.ast_rep.length;
		}

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "PlainDeclarator";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		if (x.star_list.cnt > 0)
			for (String str : x.star_list.ast_rep)
				x.ast_rep[cl++] += str;

		x.ast_rep[cl++] += leading + "Identifier: " + x.identifier;
	}

	/* Func */
	public void visit(FuncDef func)
	{
		// construct sub-nodes
		func.type_specifier.accept(this);

		func.func_name.accept(this);

		if (func.params != null)
			func.params.accept(this);

		func.comp_stmt.accept(this);

		// count length and initialize format
		int lc = 1;
		lc += func.type_specifier.ast_rep.length;
		lc += func.func_name.ast_rep.length;
		if (func.params != null)
			lc += func.params.ast_rep.length;
		lc += func.comp_stmt.ast_rep.length;

		func.ast_rep = new String[lc];
		func.ast_rep[0] = leading + "FuncDef";

		for (int i = 1; i < lc; i++)
			func.ast_rep[i] = seperator;

		// add nodes' content
		int cl = 1;
		for (String str : func.type_specifier.ast_rep)
			func.ast_rep[cl++] += str;

		for (String str : func.func_name.ast_rep)
			func.ast_rep[cl++] += str;

		if (func.params != null)
			for (String str : func.params.ast_rep)
				func.ast_rep[cl++] += str;

		for (String str : func.comp_stmt.ast_rep)
			func.ast_rep[cl++] += str;
	}

	public void visit(ArgumentList x)
	{
		// construct components
		ArgumentList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}

		// count lines
		int lc = 1;
		y = x;
		while (y != null)
		{
			lc += y.head.ast_rep.length;
			y = y.next;
		}

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "ArgumentList";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		y = x;
		while (y != null)
		{
			for (String str : y.head.ast_rep)
				x.ast_rep[cl++] += str;

			y = y.next;
		}
	}

	public void visit(ParameterList x)
	{
		// construct components
		ParameterList y = x;
		while (y != null)
		{
			y.head.accept(this);
			y = y.next;
		}

		// count lines
		int lc = 1;
		y = x;
		while (y != null)
		{
			lc += y.head.ast_rep.length;
			y = y.next;
		}

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "ParameterList";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		y = x;
		while (y != null)
		{
			for (String str : y.head.ast_rep)
				x.ast_rep[cl++] += str;

			y = y.next;
		}
	}

	/* Type */
	public void visit(TypeName x)
	{
		// construct components and count lines
		int lc = 1;
		x.type_specifier.accept(this);
		lc += x.type_specifier.ast_rep.length;

		x.star_list.accept(this);
		lc += x.star_list.ast_rep.length;

		// initialize format
		x.ast_rep = new String[lc];
		x.ast_rep[0] = leading + "TypeName";
		for (int i = 1; i < lc; i++)
			x.ast_rep[i] = seperator;

		// add contents
		int cl = 1;
		for (String str : x.type_specifier.ast_rep)
			x.ast_rep[cl++] += str;

		for (String str : x.star_list.ast_rep)
			x.ast_rep[cl++] += str;
	}

	public void visit(TypeSpecifier ts)
	{
		// construct sub-nodes
		if (ts.comp != null)
			ts.comp.accept(this);

		// count lines
		int lc = 2;
		if (ts.comp != null)
			lc += ts.comp.ast_rep.length;

		// initialize format
		ts.ast_rep = new String[lc];
		ts.ast_rep[0] = leading + "TypeSpecifier";
		for (int i = 1; i < lc; i++)
			ts.ast_rep[i] = seperator;

		// add contents
		ts.ast_rep[1] += "--Type: ";
		switch (ts.type)
		{
		case VOID:
			ts.ast_rep[1] += "void";
			break;
		case INT:
			ts.ast_rep[1] += "int";
			break;
		case CHAR:
			ts.ast_rep[1] += "char";
			break;
		case STRUCT:
			ts.ast_rep[1] += "struct ";
			break;
		case UNION:
			ts.ast_rep[1] += "union ";
			break;
		}
		if (ts.tag != null)
			ts.ast_rep[1] += ts.tag;

		int cl = 2;
		if (ts.comp != null)
			for (String str : ts.comp.ast_rep)
				ts.ast_rep[cl] += str;
	}

	/* Program */
	public void visit(Program prog)
	{
		// construct sub-nodes
		Program cp = prog;
		while (cp != null)
		{
			cp.head.accept(this);
			cp = cp.next;
		}

		// count lines
		int lc = 1;
		cp = prog;
		while (cp != null)
		{
			lc += cp.head.ast_rep.length;
			cp = cp.next;
		}

		// initialize format
		prog.ast_rep = new String[lc];
		prog.ast_rep[0] = "Program";
		for (int i = 1; i < lc; i++)
			prog.ast_rep[i] = "  |";// since no leading characters for "Program"

		// add sub-nodes' content
		cp = prog;
		int cl = 1;
		while (cp != null)
		{
			for (String str : cp.head.ast_rep)
				prog.ast_rep[cl++] += str;

			cp = cp.next;
		}
	}
}
