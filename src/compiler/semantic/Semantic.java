package compiler.semantic;

import java.util.*;
import compiler.ast.*;
import compiler.syntactic.*;

public class Semantic
{
	private Table env, tag_env;
	private Program prog_start;
	
	private static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}

	public Semantic(Program prog)
	{
		env = new Table();
		tag_env = new Table();
		prog_start = prog;
	}

	public void check() throws Exception
	{
		// figure out all constants
		ConstExprEval cee = new ConstExprEval();
		prog_start.accept(cee);

		// typing
		// 1pass: put all record into tag_env
		// 2pass: decorate all Types on AST
		// 3pass: detect recursive definition
		TypeCheck tckr = new TypeCheck(tag_env);
		for (int i = 1; i <= 3; i++)
		{
			tckr.phase = i;
			prog_start.accept(tckr);
		}
		
		//sizeof()
		

		// expr & stmt
		//FinalShot fs = new FinalShot(env, tag_env);
		//prog_start.accept(fs);
	}

	private Type check(VarDeclarator x, TypeSpecifier y) throws Exception
	{
		// **v
		int sc = x.plain_declarator.star_list.cnt;
		Type def_type = sc == 0 ? check(y, 1) : check(y, 2);
		Type real_type = check(x.plain_declarator, def_type, 0);

		// v[..][..][..]
		Iterator<Expr> eit = x.dimension.descendingIterator();
		while (eit.hasNext())
		{
			Expr e = eit.next();
			check(e);

			if(e.isConst)
			{
				int curdim = 0;
				if(e.value instanceof Integer)
					curdim = ((Integer) e.value).intValue();
				else if(e.value instanceof Character)
					curdim = (int) ((Character) e.value).charValue();
				else
					panic("The constant for a dimension must be an integer or a character!");

				if(curdim < 0)
					panic("Dimension must be Non-negative!");
				else
					real_type = new Array(curdim, real_type);
			}
			else
				panic("The dimension of an array must be constant!");
		}

		return real_type;
	}

	private Type check(Initializer x) throws Exception
	{
		if(x.expr != null && x.initializer_list == null)
		{
			Type ct = check(x.expr);
			if(!x.expr.hasInitialized)
				panic("Unintialized expr can not be used as an intializer!");
			else
				return ct;
		}
		else if(x.expr == null && x.initializer_list != null)
		{
			ArrayInitializer ret = new ArrayInitializer();

			InitializerList y = x.initializer_list;
			Type et = check(y.head);
			ret.add(et);

			while (y.next != null)
			{
				y = y.next;

				Type ct = check(y.head);
				if(ct.equals(et) != true)
					panic("");

				ret.add(ct);
			}

			return ret;
		}
		else
			panic("Internal Error!");
	}

	private Type check(ArgumentList x, Function f) throws Exception
	{
		ArgumentList y = x;
		LinkedList<Type> ats = new LinkedList<Type>();
		while (y != null)
		{
			ats.add(check(y.head));
			y = y.next;
		}

		int cnt = 0;
		Function z = f;
		while (z != null)
		{
			if(z.arg_type != null)
				++cnt;

			if(z.ret_type instanceof Function)
				z = (Function) z.ret_type;
			else
				break;
		}

		if(cnt < ats.size())
			panic("Too many arguments in function call!");
		if(ats.size() == 0 && cnt > 0)
			panic("Should provide at least 1 argument!");

		Type ret = f.ret_type;
		z = f;
		Iterator<Type> it = ats.iterator();
		while (it.hasNext())
		{
			Type cat = it.next();
			ret = z.ret_type;

			if(cat.equals(z.arg_type))
				z = (Function) z.ret_type;
			else
				panic("Argument type does not match!");
		}

		return ret;
	}

	private Type check(Expr x) throws Exception
	{
		if(x instanceof PrimaryExpr)
			return checkPrimaryExpr((PrimaryExpr) x);
		else if(x instanceof PostfixExpr)
			return checkPostfixExpr((PostfixExpr) x);
		else if(x instanceof UnaryExpr)
			return checkUnaryExpr((UnaryExpr) x);
		else if(x instanceof CastExpr)
			return checkCastExpr((CastExpr) x);
		else if(x instanceof BinaryExpr)
			return checkBinaryExpr((BinaryExpr) x);
		else if(x instanceof AssignmentExpr)
			return checkAssignmentExpr((AssignmentExpr) x);
		else if(x instanceof Expression)
			return checkExpression((Expression) x);
		else
			panic("Internal Error!");
	}

	private Type checkPrimaryExpr(PrimaryExpr x) throws Exception
	{
		if(x.elem_type == PrimaryExpr.ElemType.ID)
		{
			String vn = (String) x.elem;
			Entry ve = (Entry) env.get(Symbol.getSymbol(vn));
			if(ve != null)
			{
				if(ve instanceof VarEntry)
				{
					x.isConst = false;
					x.hasInitialized = ((VarEntry) ve).hasInitialized;
					x.type = ve.type;
					x.isLvalue = ve.isLvalue;
				}
				else if(ve instanceof FuncEntry)
				{
					x.isConst = true;
					x.hasInitialized = true;
					x.type = ve.type;
					x.isLvalue = ve.isLvalue;
				}
				else
					panic("Can not use a type as identifier!");

				return x.type;
			}
			else
				panic("Can not use identifier: " + vn + " before declaration!");
		}
		else if(x.elem_type == PrimaryExpr.ElemType.STRING)
		{
			x.isConst = true;
			x.value = (String) x.elem;
			x.type = new Pointer(Char.getInstance());
			x.hasInitialized = true;
			x.isLvalue = false;

			return x.type;
		}
		else if(x.elem_type == PrimaryExpr.ElemType.CHAR)
		{
			x.isConst = true;
			x.value = (Character) x.elem;
			x.type = Char.getInstance();
			x.hasInitialized = true;
			x.isLvalue = false;

			return x.type;
		}
		else if(x.elem_type == PrimaryExpr.ElemType.INT)
		{
			x.isConst = true;
			x.value = (Integer) x.elem;
			x.type = Int.getInstance();
			x.hasInitialized = true;
			x.isLvalue = false;

			return x.type;
		}
		else
			panic("Internal Error!");
	}

	private Type checkPostfixExpr(PostfixExpr x) throws Exception
	{
		if(x.op == PostfixExpr.Operator.MPAREN)
		{
			Expression pe = (Expression) x.param;
			Type pt = checkExpression(pe);
			if(pt instanceof Int || pt instanceof Char)
			{
				// a[-2] is acceptable, no need to check subscript
				Type et = check(x.expr);
				if(et instanceof Pointer)
				{
					x.isConst = false;
					x.isLvalue = true;
					x.type = ((Pointer) et).elem_type;
					x.hasInitialized = x.expr.hasInitialized;
					return x.type;
				}
				else if(et instanceof Array)
				{
					x.isConst = false;
					x.isLvalue = true;
					x.type = ((Array) et).elem_type;
					x.hasInitialized = x.expr.hasInitialized;
					return x.type;
				}
				else
					panic("Only pointer or array can be indexed!");
			}
			else
				panic("Invalid index type!");
		}
		else if(x.op == PostfixExpr.Operator.PAREN)
		{
			// currently, function overloading is not supported
			Type ft = check(x);
			if(ft instanceof Function)
			{
				Function cft = (Function) ft;
				x.isConst = false;
				x.isLvalue = false;
				x.hasInitialized = true;
				x.type = check((ArgumentList) x.param, cft);
				return x.type;
			}
			else
				panic("Only function can be called!");
		}
		else if(x.op == PostfixExpr.Operator.DOT)
		{
			String p = (String) x.param;
			Symbol csym = Symbol.getSymbol(p);
			Type rt = check(x.expr);
			if(rt instanceof Record)
			{
				Record crt = (Record) rt;
				TypeEntry te = (TypeEntry) crt.comp.get(csym);

				if(te == null)
					panic("No item named: " + p + " in record!");

				x.type = te.type;
				x.isConst = false;
				x.isLvalue = true;
				x.hasInitialized = x.expr.hasInitialized;
				return x.type;
			}
			else
				panic("Not a record!");
		}
		else if(x.op == PostfixExpr.Operator.PTR)
		{
			Type rt = check(x.expr);
			if(rt instanceof Pointer)
			{
				Pointer ptr = (Pointer) rt;
				if(ptr.elem_type instanceof Record)
				{
					String p = (String) x.param;
					Symbol csym = Symbol.getSymbol(p);
					Record crt = (Record) ptr.elem_type;
					TypeEntry te = (TypeEntry) crt.comp.get(csym);

					if(crt.comp.get(csym) == null)
						panic("No item named: " + p + " in record!");

					x.type = te.type;
					x.isConst = false;
					x.isLvalue = true;
					x.hasInitialized = x.expr.hasInitialized;
					return x.type;
				}
				else
					panic("Not a pointer to record!");
			}
			else
				panic("Not a pointer!");
		}
		else if(x.op == PostfixExpr.Operator.INC)
		{
			Type pet = check(x.expr);
			if(!(pet instanceof Pointer || pet instanceof Int || pet instanceof Char))
				panic("Can not be post increased!");

			x.isConst = false;
			x.isLvalue = false;
			x.hasInitialized = x.expr.hasInitialized;
			x.type = pet;
			return x.type;
		}
		else if(x.op == PostfixExpr.Operator.DEC)
		{
			Type pet = check(x.expr);
			if(!(pet instanceof Pointer || pet instanceof Int || pet instanceof Char))
				panic("Can not be post increased!");

			x.isConst = false;
			x.isLvalue = false;
			x.hasInitialized = x.expr.hasInitialized;
			x.type = pet;
			return x.type;
		}
		else
			panic("Internal Error!");
	}

	private Type checkUnaryExpr(UnaryExpr x) throws Exception
	{
		if(x.op == UnaryExpr.Operator.INC) // ++a
		{
			Type xt = check(x.expr);
			if(!(xt instanceof Int || xt instanceof Char || xt instanceof Pointer))
				panic("Can not be increased!");

			x.type = xt;
			x.hasInitialized = x.expr.hasInitialized;
			x.isConst = false;
			x.isLvalue = false;
			return x.type;
		}
		else if(x.op == UnaryExpr.Operator.DEC) // --a
		{
			Type xt = check(x.expr);
			if(!(xt instanceof Int || xt instanceof Char || xt instanceof Pointer))
				panic("Can not be increased!");

			x.type = xt;
			x.hasInitialized = x.expr.hasInitialized;
			x.isConst = false;
			x.isLvalue = false;
			return x.type;
		}
		else if(x.op == UnaryExpr.Operator.BIT_AND) // &a
		{
			Type xt = check(x.expr);
			x.hasInitialized = true;
			x.isConst = true;
			x.isLvalue = false;
			x.type = new Pointer(xt);
			return x.type;
		}
		else if(x.op == UnaryExpr.Operator.STAR) // *a
		{
			Type xt = check(x.expr);
			if(xt instanceof Array)
			{
				x.type = ((Array) xt).elem_type;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = (x.type instanceof Array);
				x.isLvalue = !x.isConst;
				return x.type;
			}
			else if(xt instanceof Pointer)
			{
				x.type = ((Pointer) xt).elem_type;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = false;
				x.isLvalue = false;
				return x.type;
			}
			else
				panic("Only array or pointer can be dereferenced!");
		}
		else if(x.op == UnaryExpr.Operator.POSITIVE) // +a
		{
			Type xt = check(x.expr);
			if(xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;
				return x.type;
			}
			else
				panic("Can not be converted to be positive!");

		}
		else if(x.op == UnaryExpr.Operator.NEGATIVE) // -a
		{
			Type xt = check(x.expr);
			if(xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;
				return x.type;
			}
			else
				panic("Can not be converted to be negative!");
		}
		else if(x.op == UnaryExpr.Operator.BIT_NOT) // ~a
		{
			Type xt = check(x.expr);
			if(xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;
				return x.type;
			}
			else
				panic("Can not be negated!");
		}
		else if(x.op == UnaryExpr.Operator.NOT) // !a
		{
			Type xt = check(x.expr);
			if(xt instanceof Int || xt instanceof Char)
			{
				x.type = xt;
				x.hasInitialized = x.expr.hasInitialized;
				x.isConst = x.expr.isConst;
				x.isLvalue = false;
				return x.type;
			}
			else
				panic("Can not be logically reversed!");
		}
		else if(x.op == UnaryExpr.Operator.SIZEOF) // sizeof(int) sizeof(a)
		{
			if(x.expr != null && x.type_name == null)
			{
				x.type = Int.getInstance();
				x.isConst = true;
				x.isLvalue = false;
				x.hasInitialized = true;
				x.value = (int) 1;

				return x.type;
			}
			else if(x.expr == null && x.type_name != null)
			{
				Type ct = check(x.expr);
				x.type = Int.getInstance();
				x.isConst = true;
				x.isLvalue = false;
				x.hasInitialized = true;
				x.value = ct.size;

				if(x.type_name.star_list.cnt > 0)
					x.value = 4;
				else
				{
					TypeSpecifier ts = x.type_name.type_specifier;
					Type tt = check(ts, 0);
					x.value = tt.size;
				}
				return x.type;
			}
			else
				panic("Internal Error!");
		}
		else
			panic("Internal Error!");
	}

	private Type checkCastExpr(CastExpr x) throws Exception
	{
		Type tt = check(x.target_type.type_specifier, 0);
		int n = x.target_type.star_list.cnt;
		for (int i = 0; i < n; i++)
			tt = new Pointer(tt);

		Type ot = check(x.expr);
		if(!ot.canBeCastTo(tt))
			panic("Invalid Conversion");

		x.hasInitialized = x.expr.hasInitialized;
		x.isConst = x.expr.isConst;
		x.isLvalue = x.expr.isLvalue;
		x.type = tt;
		x.value = x.expr.value;
		return x.type;
	}

	private Type checkBinaryExpr(BinaryExpr x) throws Exception
	{
		Type lt = check(x.left);
		Type rt = check(x.right);

		if(!lt.canOperateWith(x.op, rt))
			panic("Incompatible types in BinaryExpr!");

		x.hasInitialized = (x.left.hasInitialized && x.right.hasInitialized);
		x.isConst = (x.left.isConst && x.right.isConst);
		x.isLvalue = false;
		x.type = rt;
		return x.type;
	}

	private Type checkAssignmentExpr(AssignmentExpr x) throws Exception
	{
		Type lt = check(x.left);
		Type rt = check(x.right);

		if(!lt.isAssignableWith(rt))
			panic("Incompatible assignment!");
		if(!x.left.isLvalue)
			panic("Left Expr can not be assigned!");

		if(x.op == AssignmentExpr.Operator.ASSIGN)
		{
			x.type = rt;
			x.hasInitialized = x.right.hasInitialized;
			x.isConst = false;
			x.isLvalue = false;
			return x.type;
		}
		else
		{
			boolean ok = true;
			if(!(lt instanceof Int) && !(lt instanceof Char))
				ok = false;
			if(!(rt instanceof Int) && !(rt instanceof Char))
				ok = false;

			if(!ok)
				panic("Invalid Assignment!");

			x.type = rt;
			x.hasInitialized = x.right.hasInitialized;
			x.isConst = false;
			x.isLvalue = false;
			return x.type;
		}
	}

	private Type checkExpression(Expression x) throws Exception
	{
		Expression y = x;
		while (y != null)
		{
			check(y.head);
			y = y.next;
		}

		y = x;
		while (y.next != null)
			y = y.next;

		x.isConst = y.head.isConst;
		x.value = x.isConst ? y.head.value : null;
		x.hasInitialized = y.head.hasInitialized;
		x.type = y.head.type;
		x.isLvalue = y.head.isLvalue;

		return x.type;
	}
}
