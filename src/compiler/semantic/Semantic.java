package compiler.semantic;

import java.util.*;
import compiler.ast.*;

public class Semantic
{
	Table tag_env, env;
	Program prog;
	int loop_cnt;

	private static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}

	public Semantic(Program x)
	{
		tag_env = new Table();
		env = new Table();
		prog = x;
		loop_cnt = 0;
	}

	public void check() throws Exception
	{
		checkProgram(prog);
	}

	private void checkProgram(Program x) throws Exception
	{
		Program y = x;
		while (y != null)
		{
			checkProgramComp(y.head);
			y = y.next;
		}
	}

	private void checkProgramComp(ProgramComp x) throws Exception
	{
		if (x instanceof Declaration)
			checkDeclaration((Declaration) x);
		else if (x instanceof FuncDef)
			checkFuncDef((FuncDef) x);
		else
			panic("Internal Error!");
	}

	private void checkDeclaration(Declaration x) throws Exception
	{
		Type def_type = checkTypeSpecifier(x.type_specifier);
		InitDeclaratorList y = x.init_declarator_list;

		if (y == null)
		{
			switch (x.type_specifier.type)
			{
			case VOID:
			case INT:
			case CHAR:
				panic("Meaningless declaration of intrinsic type!");
				break;
			default:
				break;
			}
		}
		else
			checkInitDeclaratorList(y, def_type);
	}

	private Type checkPlainDeclarator(PlainDeclarator x, Type def_type)
	{
		Type real_type = def_type;
		int n = x.star_list.cnt;
		for (int i = 0; i < n; i++)
			real_type = new Pointer(real_type);

		return real_type;
	}

	private void checkFuncDef(FuncDef x) throws Exception
	{
		Type def_type = checkTypeSpecifier(x.type_specifier);
		Type real_type = checkPlainDeclarator(x.func_name, def_type);
		String fn = x.func_name.identifier;
		Symbol csym = Symbol.getSymbol(fn);
		Entry ce = (Entry) env.get(csym);

		if (ce != null)
			panic(fn + " has already been defined!");

		if (!real_type.complete)
			panic("Return type of function: " + fn + " is incomplete!");

		Function ft = new Function(real_type);
		env.put(csym, new FuncEntry(ft));

		env.beginScope();
		checkParameterList(x, ft);
		checkCompoundStmt(x.comp_stmt);
		env.endScope();
	}

	private void checkParameterList(FuncDef x, Function ft) throws Exception
	{
		ParameterList y = x.params;

		while (y != null)
		{
			PlainDeclaration z = y.head;
			Type def_type = checkTypeSpecifier(z.type_specifier);
			Type real_type = checkDeclarator(z.declarator, def_type);
			String vn = z.declarator.plain_declarator.identifier;
			Symbol csym = Symbol.getSymbol(vn);

			if (env.get(csym) != null)
				panic("Variable: " + vn + " has already been declared in this scope!");

			env.put(csym, new VarEntry(real_type, false));
			ft.args.add(real_type);

			y = y.next;
		}
	}

	private void checkStmt(Stmt x) throws Exception
	{
		if (x instanceof ExpressionStmt)
			checkExpressionStmt((ExpressionStmt) x);
		else if (x instanceof CompoundStmt)
		{
			env.beginScope();
			checkCompoundStmt((CompoundStmt) x);
			env.endScope();
		}
		else if (x instanceof SelectionStmt)
			checkSelectionStmt((SelectionStmt) x);
		else if (x instanceof JumpStmt)
			checkJumpStmt((JumpStmt) x);
		else if (x instanceof IterationStmt)
			checkIterationStmt((IterationStmt) x);
		else
			panic("Internal Error!");
	}

	private void checkExpressionStmt(ExpressionStmt x) throws Exception
	{
		checkExpression(x.e);
	}

	private void checkCompoundStmt(CompoundStmt x) throws Exception
	{
		checkDeclarationList(x.declaration_list);
		checkStmtList(x.stmt_list);
	}

	private void checkDeclarationList(DeclarationList x) throws Exception
	{
		while (x != null)
		{
			checkDeclaration(x.head);
			x = x.next;
		}
	}

	private void checkStmtList(StmtList x) throws Exception
	{
		while (x != null)
		{
			checkStmt(x.head);
			x = x.next;
		}
	}

	private void checkSelectionStmt(SelectionStmt x) throws Exception
	{
		Type cond_type = checkExpr(x.cond);
		boolean ok = cond_type instanceof Int || cond_type instanceof Char || cond_type instanceof Pointer;
		if (!ok)
			panic("Invalid condition expr!");

		checkStmt(x.if_clause);
		if (x.else_clause != null)
			checkStmt(x.else_clause);
	}

	private void checkJumpStmt(JumpStmt x) throws Exception
	{
		switch (x.type)
		{
		case RETURN:
			checkExpr(x.expr);
			break;
		case BREAK:
			if (--loop_cnt < 0)
				panic("Break statement not within a loop!");
			break;
		case CONTINUE:
			if (--loop_cnt < 0)
				panic("Continue statement not within a loop!");
			break;
		default:
			panic("Internal Error!");
		}
	}

	private void checkIterationStmt(IterationStmt x) throws Exception
	{
		++loop_cnt;

		if (x.iteration_type == IterationStmt.Type.FOR)
		{
			if (x.init != null)
				checkExpr(x.init);
			if (x.judge != null)
				checkExpr(x.judge);
			if (x.next != null)
				checkExpr(x.next);
		}
		else
		{
			if (x.judge != null)
				checkExpr(x.judge);
		}

		checkStmt(x.stmt);
	}

	private Type checkTypeSpecifier(TypeSpecifier x) throws Exception
	{
		switch (x.type)
		{
		case VOID:
			x.detail = Void.getInstance();
			break;
		case INT:
			x.detail = Int.getInstance();
			break;
		case CHAR:
			x.detail = Char.getInstance();
			break;
		case STRUCT:
			check_struct(x);
			break;
		case UNION:
			check_union(x);
			break;
		default:
			panic("Internal Error!");
			break;
		}

		return x.detail;
	}

	private void check_struct(TypeSpecifier x) throws Exception
	{
		if (x.comp == null)// struct node
		{
			Symbol ss = Symbol.getSymbol(x.tag);
			TypeEntry ce = (TypeEntry) tag_env.get(ss);

			if (ce != null)
			{
				if (!(ce.type instanceof Struct))
					panic(x.tag + " is not declared as struct!");

				x.detail = ce.type;
			}
			else
			{
				x.detail = new Struct(x.tag, null);
				tag_env.put(ss, new TypeEntry(x.detail));
			}
		}
		else
		{
			if (x.tag == null)// struct { ... }
			{
				Table cc = new Table();
				x.detail = new Struct(null, cc);
				x.detail.size = check_record_comp(x);
				x.detail.complete = true;
			}
			else// struct node { ... }
			{
				Symbol csym = Symbol.getSymbol(x.tag);
				TypeEntry ce = (TypeEntry) tag_env.get(csym);

				if (ce != null)// may be declared before
				{
					if (!(ce.type instanceof Struct))
						panic(x.tag + " is not declared as struct!");

					Struct cst = (Struct) ce.type;
					if (cst.comp != null)
						panic("struct " + x.tag + " has been declared before!");

					cst.comp = new Table();
					x.detail = cst;
					x.detail.size = check_record_comp(x);
					x.detail.complete = true;
				}
				else// first time meet
				{
					Table cc = new Table();
					x.detail = new Struct(x.tag, cc);
					tag_env.put(csym, new TypeEntry(x.detail));
					x.detail.size = check_record_comp(x);
					x.detail.complete = true;
				}
			}
		}
	}

	private void check_union(TypeSpecifier x) throws Exception
	{
		if (x.comp == null)// union node
		{
			Symbol ss = Symbol.getSymbol(x.tag);
			TypeEntry ce = (TypeEntry) tag_env.get(ss);

			if (ce != null)
			{
				if (!(ce.type instanceof Union))
					panic(x.tag + " is not declared as union!");

				x.detail = ce.type;
			}
			else
			{
				x.detail = new Union(x.tag, null);
				tag_env.put(ss, new TypeEntry(x.detail));
			}
		}
		else
		{
			if (x.tag == null)// union { ... }
			{
				Table cc = new Table();
				x.detail = new Union(null, cc);
				x.detail.size = check_record_comp(x);
				x.detail.complete = true;
			}
			else// union node { ... }
			{
				Symbol csym = Symbol.getSymbol(x.tag);
				TypeEntry ce = (TypeEntry) tag_env.get(csym);

				if (ce != null)// may be declared before
				{
					if (!(ce.type instanceof Union))
						panic(x.tag + " is not declared as union!");

					Union cst = (Union) ce.type;
					if (cst.comp != null)
						panic("union " + x.tag + " has been declared before!");

					cst.comp = new Table();
					x.detail = cst;
					x.detail.size = check_record_comp(x);
					x.detail.complete = true;
				}
				else// first time meet
				{
					Table cc = new Table();
					x.detail = new Union(x.tag, cc);
					tag_env.put(csym, new TypeEntry(x.detail));
					x.detail.size = check_record_comp(x);
					x.detail.complete = true;
				}
			}
		}
	}

	private int check_record_comp(TypeSpecifier x) throws Exception
	{
		if (x.type != TypeSpecifier.Type.STRUCT && x.type != TypeSpecifier.Type.UNION)
			panic("Internal Error!");

		int total_size = 0;
		NonInitDeclarationList y = x.comp;
		Table ccomp = ((Record) x.detail).comp;

		while (y != null)
		{
			TypeSpecifier cts = y.head.type_specifier;
			checkTypeSpecifier(cts);

			DeclaratorList w = y.head.declarator_list;
			while (w != null)
			{
				Declarator vdr = w.head;
				String vdrr = vdr.plain_declarator.identifier;
				Symbol csym = Symbol.getSymbol(vdrr);
				if (ccomp.get(csym) != null)
					panic(vdrr + " has already been defined in this scope!");

				int sc = vdr.plain_declarator.star_list.cnt;
				Type real_type = cts.detail;

				// TypeSpecifier a
				if (sc == 0 && !cts.detail.complete)
					panic("Incomplete type detected!");

				// TypeSpecifier **a
				for (int i = 0; i < sc; i++)
					real_type = new Pointer(real_type);

				// TypeSpecifier **a[...][...]
				Iterator<Expr> eit = vdr.dimension.descendingIterator();
				while (eit.hasNext())
				{
					Expr ce = eit.next();
					checkExpr(ce);
					if (!ce.isConst)
						panic("Dimension of an array shall be constant!");

					int n = (int) ce.value;
					real_type = new Array(n, real_type);
					real_type.size = n * ((Array) real_type).elem_type.size;
				}

				total_size += real_type.size;
				ccomp.put(csym, new TypeEntry(real_type));
				w = w.next;
			}
			y = y.next;
		}

		return total_size;
	}

	private Type checkExpr(Expr x) throws Exception
	{
		if (x instanceof PrimaryExpr)
			checkPrimaryExpr((PrimaryExpr) x);
		else if (x instanceof PostfixExpr)
			checkPostfixExpr((PostfixExpr) x);
		else if (x instanceof UnaryExpr)
			checkUnaryExpr((UnaryExpr) x);
		else if (x instanceof CastExpr)
			checkCastExpr((CastExpr) x);
		else if (x instanceof BinaryExpr)
			checkBinaryExpr((BinaryExpr) x);
		else if (x instanceof AssignmentExpr)
			checkAssignmentExpr((AssignmentExpr) x);
		else if (x instanceof Expression)
			checkExpression((Expression) x);
		else
			panic("Internal Error!");

		return x.type;
	}

	private void checkPrimaryExpr(PrimaryExpr x) throws Exception
	{
		switch (x.elem_type)
		{
		case INT:
			x.isConst = true;
			x.value = (Integer) x.elem;
			x.type = Int.getInstance();
			x.hasInitialized = true;
			x.isLvalue = false;
			break;
		case CHAR:
			x.isConst = true;
			x.value = (Character) x.elem;
			x.type = Char.getInstance();
			x.hasInitialized = true;
			x.isLvalue = false;
			break;
		case STRING:
			x.isConst = false;// gcc doesn't consider "abcd"[2] as a const
			x.value = (String) x.elem;
			x.type = new Pointer(Char.getInstance());
			x.hasInitialized = true;
			x.isLvalue = false;
			break;
		case PAREN:
			Expression e = (Expression) x.elem;
			checkExpression(e);
			x.isConst = x.isConst;
			x.value = e.value;
			x.type = e.type;
			x.hasInitialized = e.hasInitialized;
			x.isLvalue = false;// (a, b, c) is not considered as a lvalue by gcc
			break;
		case ID:
			String vn = (String) x.elem;
			Symbol csym = Symbol.getSymbol(vn);
			Entry ve = (Entry) env.get(csym);
			if (ve == null)
				panic("Can not use identifier: " + vn + " before declaration!");
			else if (ve instanceof VarEntry)
			{
				x.isConst = false;
				x.value = null;
				x.hasInitialized = ((VarEntry) ve).hasInitialized;
				x.type = ve.type;
				x.isLvalue = ve.isLvalue;
			}
			else if (ve instanceof FuncEntry)
			{
				x.isConst = false;
				x.value = null;
				x.hasInitialized = true;
				x.type = ve.type;
				x.isLvalue = false;
			}
			else
				panic("Can not use a type as identifier!");
			break;
		default:
			panic("Internal Error!");
		}
	}

	private void checkPostfixExpr(PostfixExpr x) throws Exception
	{
		if (x.op == PostfixExpr.Operator.MPAREN)
		{
			Expression pe = (Expression) x.param;
			Type pt = checkExpression(pe);
			if (pt instanceof Int || pt instanceof Char)
			{
				// a[-2] is acceptable, no need to check range
				Type et = checkExpr(x.expr);
				if (et instanceof Pointer)
				{
					x.isConst = false;
					x.value = null;
					x.isLvalue = true;
					x.type = ((Pointer) et).elem_type;
					x.hasInitialized = x.expr.hasInitialized;
				}
				else if (et instanceof Array)
				{
					x.isConst = false;
					x.value = null;
					x.isLvalue = true;
					x.type = ((Array) et).elem_type;
					x.hasInitialized = x.expr.hasInitialized;
				}
				else
					panic("Only pointer or array can be indexed!");
			}
			else
				panic("Invalid index type!");
		}
		else if (x.op == PostfixExpr.Operator.PAREN)
		{
			// function overloading is not supported
			Type ft = checkExpr(x);
			if (ft instanceof Function)
			{
				Function cft = (Function) ft;
				x.isConst = false;
				x.value = null;
				x.isLvalue = false;
				x.hasInitialized = true;
				x.type = check_func_args((ArgumentList) x.param, cft);
			}
			else
				panic("Only function can be called!");
		}
		else if (x.op == PostfixExpr.Operator.DOT)
		{
			Type rt = checkExpr(x.expr);
			if (rt instanceof Record)
			{
				Record crt = (Record) rt;
				String p = (String) x.param;
				Symbol csym = Symbol.getSymbol(p);
				TypeEntry te = (TypeEntry) crt.comp.get(csym);

				if (te == null)
					panic("No item named: " + p + " in record!");

				x.isConst = false;
				x.type = te.type;
				x.value = null;
				x.isLvalue = true;
				x.hasInitialized = x.expr.hasInitialized;
			}
			else
				panic("Not a record!");
		}
		else if (x.op == PostfixExpr.Operator.PTR)
		{
			Type rt = checkExpr(x.expr);
			if (rt instanceof Pointer)
			{
				Pointer ptr = (Pointer) rt;
				if (ptr.elem_type instanceof Record)
				{
					String p = (String) x.param;
					Symbol csym = Symbol.getSymbol(p);
					Record crt = (Record) ptr.elem_type;
					TypeEntry te = (TypeEntry) crt.comp.get(csym);

					if (crt.comp.get(csym) == null)
						panic("No item named: " + p + " in record!");

					x.type = te.type;
					x.isConst = false;
					x.value = null;
					x.isLvalue = true;
					x.hasInitialized = x.expr.hasInitialized;
				}
				else
					panic("Not a pointer to record!");
			}
			else
				panic("Not a pointer!");
		}
		else if (x.op == PostfixExpr.Operator.INC)
		{
			Type pet = checkExpr(x.expr);
			if (pet instanceof Pointer || pet instanceof Int || pet instanceof Char)
			{
				if (!x.expr.isLvalue)
					panic("lvalue required as increment operand!");

				x.isConst = false;
				x.value = null;
				x.isLvalue = false;
				x.hasInitialized = x.expr.hasInitialized;
				x.type = pet;
			}
			else
				panic("Can not be post increased!");
		}
		else if (x.op == PostfixExpr.Operator.DEC)
		{
			Type pet = checkExpr(x.expr);
			if (pet instanceof Pointer || pet instanceof Int || pet instanceof Char)
			{
				if (!x.expr.isLvalue)
					panic("lvalue required as decrement operand!");

				x.isConst = false;
				x.value = null;
				x.isLvalue = false;
				x.hasInitialized = x.expr.hasInitialized;
				x.type = pet;
			}
			else
				panic("Can not be post increased!");
		}
		else
			panic("Internal Error!");
	}

	private Type check_func_args(ArgumentList x, Function f) throws Exception
	{
		ArgumentList y = x;
		LinkedList<Type> ats = new LinkedList<Type>();
		while (y != null)
		{
			ats.add(checkExpr(y.head));
			y = y.next;
		}

		if (ats.size() > f.args.size())
			panic("Too many arguments in function call!");
		else if (ats.size() < f.args.size())
			panic("Too few arguments in function call!");
		else
		{
			Iterator<Type> ait = ats.iterator();
			Iterator<Type> fit = f.args.iterator();
			while (ait.hasNext())
			{
				Type cat = ait.next();
				Type fat = fit.next();

				if (!cat.equals(fat))
					panic("Argument type does not match!");
			}
		}

		return f.ret_type;
	}

	private void checkUnaryExpr(UnaryExpr x) throws Exception
	{
		if (x.op == UnaryExpr.Operator.INC) // ++a
		{
			Type xt = checkExpr(x.expr);
			if (!(xt instanceof Int || xt instanceof Char || xt instanceof Pointer))
				panic("Can not be increased!");

			if (!x.expr.isLvalue)
				panic("lvalue required as increment operand!");

			x.type = xt;
			x.hasInitialized = x.expr.hasInitialized;
			x.isConst = false;
			x.value = null;
			x.isLvalue = false;
		}
		else if (x.op == UnaryExpr.Operator.DEC) // --a
		{
			Type xt = checkExpr(x.expr);
			if (!(xt instanceof Int || xt instanceof Char || xt instanceof Pointer))
				panic("Can not be decreased!");

			if (!x.expr.isLvalue)
				panic("lvalue required as decrement operand!");

			x.type = xt;
			x.hasInitialized = x.expr.hasInitialized;
			x.isConst = false;
			x.value = null;
			x.isLvalue = false;
		}
		else if (x.op == UnaryExpr.Operator.BIT_AND) // &a
		{
			Type xt = checkExpr(x.expr);
			x.hasInitialized = true;
			x.isConst = true;
			x.value = null;
			x.isLvalue = false;
			x.type = new Pointer(xt);
		}
		else if (x.op == UnaryExpr.Operator.STAR) // *a
		{
			Type xt = checkExpr(x.expr);
			if (xt instanceof Array)
			{
				x.type = ((Array) xt).elem_type;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = false;
				x.isLvalue = !x.isConst;
				x.value = null;
			}
			else if (xt instanceof Pointer)
			{
				x.type = ((Pointer) xt).elem_type;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = false;
				x.value = null;
				x.isLvalue = false;
			}
			else
				panic("Only array or pointer can be dereferenced!");
		}
		else if (x.op == UnaryExpr.Operator.POSITIVE) // +a
		{
			Type xt = checkExpr(x.expr);
			if (xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.isLvalue = false;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;

				if (x.isConst)
					x.value = (Integer) x.expr.value;
				else
					x.value = null;
			}
			else
				panic("Can not be converted to be positive!");

		}
		else if (x.op == UnaryExpr.Operator.NEGATIVE) // -a
		{
			Type xt = checkExpr(x.expr);
			if (xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;

				if (x.isConst)
					x.value = -((Integer) x.expr.value);
				else
					x.value = null;
			}
			else
				panic("Can not be converted to be negative!");
		}
		else if (x.op == UnaryExpr.Operator.BIT_NOT) // ~a
		{
			Type xt = checkExpr(x.expr);
			if (xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;

				if (x.isConst)
					x.value = ~((Integer) x.expr.value);
				else
					x.value = null;
			}
			else
				panic("Can not be negated!");
		}
		else if (x.op == UnaryExpr.Operator.NOT) // !a
		{
			Type xt = checkExpr(x.expr);
			if (xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;

				if (x.isConst)
					x.value = ((Integer) x.expr.value) == 0 ? 1 : 0;
				else
					x.value = null;
			}
			else
				panic("Can not be logically reversed!");
		}
		else if (x.op == UnaryExpr.Operator.SIZEOF)
		{
			if (x.expr != null && x.type_name == null)// sizeof(a)
			{
				x.type = Int.getInstance();
				x.isConst = true;
				x.isLvalue = false;
				x.hasInitialized = true;

				if (x.expr instanceof PrimaryExpr)
				{
					PrimaryExpr pe = (PrimaryExpr) x.expr;
					if (pe.elem_type != PrimaryExpr.ElemType.ID)
						panic("Parameter of sizeof() must be variable or type!");

					String id = (String) pe.elem;
					Symbol csym = Symbol.getSymbol(id);
					Entry ce = (Entry) env.get(csym);
					if (ce == null)
						panic("Undefined variable: " + id);
					else if (!(ce instanceof VarEntry))
						panic("Not a variable!");
					else if (!ce.type.complete)
						panic("Incomplete type for variable: " + id);
					else
						x.value = (Integer) ce.type.size;
				}
				else
					panic("Parameter of sizeof() must be variable or type!");
			}
			else if (x.expr == null && x.type_name != null)// sizeof(int)
			{
				Type ct = checkTypeName(x.type_name);
				x.type = Int.getInstance();
				x.isConst = true;
				x.isLvalue = false;
				x.hasInitialized = true;
				x.value = (Integer) ct.size;
			}
			else
				panic("Internal Error!");
		}
		else
			panic("Internal Error!");
	}

	private Type checkTypeName(TypeName x) throws Exception
	{
		Type tt = checkTypeSpecifier(x.type_specifier);
		int n = x.star_list.cnt;
		for (int i = 0; i < n; i++)
			tt = new Pointer(tt);

		return tt;
	}

	private void checkCastExpr(CastExpr x) throws Exception
	{
		Type tt = checkTypeName(x.target_type);

		Type ot = checkExpr(x.expr);
		if (!ot.canBeCastTo(tt))
			panic("Invalid Conversion!");

		x.hasInitialized = x.expr.hasInitialized;
		x.isConst = x.expr.isConst;
		x.isLvalue = x.expr.isLvalue;
		x.type = tt;
		x.value = x.expr.value;
	}

	private void checkBinaryExpr(BinaryExpr x) throws Exception
	{
		Type lt = checkExpr(x.left);
		Type rt = checkExpr(x.right);

		if (!lt.canOperateWith(x.op, rt))
			panic("Incompatible types in BinaryExpr!");

		x.hasInitialized = (x.left.hasInitialized && x.right.hasInitialized);
		x.isConst = (x.left.isConst && x.right.isConst);
		x.isLvalue = false;
		x.type = rt;
		x.value = null;
		calc_const(x);
	}

	private void calc_const(BinaryExpr x) throws Exception
	{
		if (!x.isConst)
			return;

		x.type = Int.getInstance();

		switch (x.op)
		{
		case BIT_AND:
			x.value = (Integer) (((Integer) x.left.value) & ((Integer) x.right.value));
			break;
		case BIT_XOR:
			x.value = (Integer) (((Integer) x.left.value) ^ ((Integer) x.right.value));
			break;
		case BIT_OR:
			x.value = (Integer) (((Integer) x.left.value) | ((Integer) x.right.value));
			break;
		case AND:
			x.value = (Integer) ((((Integer) x.left.value) != 0 && ((Integer) x.right.value) != 0) ? 1 : 0);
			break;
		case OR:
			x.value = (Integer) ((((Integer) x.left.value) != 0 || ((Integer) x.right.value) != 0) ? 1 : 0);
			break;
		case EQ:
			x.value = (Integer) ((((Integer) x.left.value) == ((Integer) x.right.value)) ? 1 : 0);
			break;
		case NE:
			x.value = (Integer) ((((Integer) x.left.value) != ((Integer) x.right.value)) ? 1 : 0);
			break;
		case LT:
			x.value = (Integer) ((((Integer) x.left.value) < ((Integer) x.right.value)) ? 1 : 0);
			break;
		case GT:
			x.value = (Integer) ((((Integer) x.left.value) > ((Integer) x.right.value)) ? 1 : 0);
			break;
		case LE:
			x.value = (Integer) ((((Integer) x.left.value) <= ((Integer) x.right.value)) ? 1 : 0);
			break;
		case GE:
			x.value = (Integer) ((((Integer) x.left.value) >= ((Integer) x.right.value)) ? 1 : 0);
			break;
		case SHL:
			x.value = (Integer) (((Integer) x.left.value) << ((Integer) x.right.value));
			break;
		case SHR:
			x.value = (Integer) (((Integer) x.left.value) >> ((Integer) x.right.value));
			break;
		case PLUS:
			x.value = (Integer) (((Integer) x.left.value) + ((Integer) x.right.value));
			break;
		case MINUS:
			x.value = (Integer) (((Integer) x.left.value) - ((Integer) x.right.value));
			break;
		case TIMES:
			x.value = (Integer) (((Integer) x.left.value) * ((Integer) x.right.value));
			break;
		case DIVIDE:
			if ((Integer) x.right.value == 0)
				panic("Dividend shall not be zero!");
			else
				x.value = (Integer) (((Integer) x.left.value) / ((Integer) x.right.value));
			break;
		case MODULE:
			x.value = (Integer) (((Integer) x.left.value) % ((Integer) x.right.value));
			break;
		}
	}

	private void checkAssignmentExpr(AssignmentExpr x) throws Exception
	{
		Type lt = checkExpr(x.left);
		Type rt = checkExpr(x.right);

		if (!lt.isAssignableWith(rt))
			panic("Incompatible assignment!");
		if (!x.left.isLvalue)
			panic("Left expr can not be assigned!");

		if (x.op == AssignmentExpr.Operator.ASSIGN)
		{
			if (!x.right.hasInitialized)
				panic("Can not do assignment with uninitialized value!");
		}
		else
		{
			if (!x.left.hasInitialized || !x.right.hasInitialized)
				panic("Can not do assignment with uninitialized value!");

			boolean ok1 = lt instanceof Int && lt instanceof Char;
			boolean ok2 = rt instanceof Int && rt instanceof Char;

			if (!(ok1 && ok2))
				panic("Invalid Assignment!");
		}

		x.type = rt;
		x.hasInitialized = true;
		x.isConst = false;
		x.value = null;
		x.isLvalue = false;
	}

	private Type checkExpression(Expression x) throws Exception
	{
		Expression y = x;
		while (y != null)
		{
			checkExpr(y.head);
			y = y.next;
		}

		y = x;
		while (y.next != null)
			y = y.next;

		x.isConst = y.head.isConst;
		x.value = x.isConst ? y.head.value : null;
		x.hasInitialized = y.head.hasInitialized;
		x.type = y.head.type;
		x.isLvalue = false;

		return x.type;
	}

	private void checkInitDeclaratorList(InitDeclaratorList x, Type def_type) throws Exception
	{
		while (x != null)
		{
			checkInitDeclarator(x.head, def_type);
			x = x.next;
		}
	}

	private void checkInitDeclarator(InitDeclarator x, Type def_type) throws Exception
	{
		String vn = x.declarator.plain_declarator.identifier;
		Symbol csym = Symbol.getSymbol(vn);
		if (env.get(csym) != null)
			panic("Variable: " + vn + " has been declared in this scope!");

		Type real_type = checkDeclarator(x.declarator, def_type);
		boolean has_initialized = x.initializer != null;
		
		if (has_initialized)
		{
			Type init_type = checkInitializer(x.initializer);

			if (!real_type.isAssignableWith(init_type))
				panic("Invalid assignment when initializing variable: " + x.declarator.plain_declarator.identifier);
		}

		env.put(csym, new VarEntry(real_type, has_initialized));
	}

	private Type checkDeclarator(Declarator x, Type def_type) throws Exception
	{
		Type real_type = def_type;
		int n = x.plain_declarator.star_list.cnt;
		for (int i = 0; i < n; i++)
			real_type = new Pointer(real_type);

		if (real_type instanceof Void)
			panic("Variable can not be declared as void!");

		Iterator<Expr> eit = x.dimension.descendingIterator();
		while (eit.hasNext())
		{
			Expr e = eit.next();
			Type dim_type = checkExpr(e);

			boolean ok = dim_type instanceof Int || dim_type instanceof Char;
			if (!ok)
				panic("Constant for dimension must be an integer or a character!");

			if (!e.isConst)
				panic("Dimension of array must be constant!");

			int curdim = (int) e.value;
			if (curdim < 0)
				panic("Dimension in declaration must be Non-negative!");

			real_type = new Array(curdim, real_type);
		}

		return real_type;
	}

	private Type checkInitializer(Initializer x) throws Exception
	{
		if (x.expr != null)// x = a;
		{
			Type ct = checkExpr(x.expr);
			if (!x.expr.hasInitialized)
				panic("Unintialized expr can not be used as an intializer!");

			return ct;
		}
		else// x[3] = {a, b, c}
		{
			ArrayInitializer ret = new ArrayInitializer();

			InitializerList y = x.initializer_list;
			Type et = checkInitializer(y.head);
			ret.add(et);
			y = y.next;
			while (y != null)
			{
				Type ct = checkInitializer(y.head);
				if (ct.equals(et) != true)
					panic("Types in an initializer list must be identical to each other!");

				ret.add(ct);
				y = y.next;
			}

			return ret;
		}
	}

}
