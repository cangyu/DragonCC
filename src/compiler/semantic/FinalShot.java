package compiler.semantic;

import compiler.ast.*;
import java.util.*;

public class FinalShot implements ASTNodeVisitor
{
	Table env, tag_env, local_env;
	int loop_cnt;
	int nest_cnt;

	private static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}

	public FinalShot(Table _e, Table _te)
	{
		env = _e;
		tag_env = _te;
		local_env = null;
		loop_cnt = 0;
		nest_cnt = 0;
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
		return;
	}

	@Override
	public void visit(Declaration x) throws Exception
	{	
		InitDeclaratorList y = x.init_declarator_list;
		if(y == null)// TypeSpecifier;
		{
			switch (x.type_specifier.type)
			{
			case VOID:
			case INT:
			case CHAR:
				panic("Meaningless declaration of intrinsic type!");
			default:
				break;
			}
		}
		else// TypeSpecifier InitDeclaratorList
		{
			check_complete_type(x.type_specifier);
			x.init_declarator_list.accept(this);

			while (y != null)
			{
				Declarator p = y.head.declarator;
				Initializer q = y.head.initializer;
				boolean has_initializer = q != null;

				if(p instanceof VarDeclarator)
				{
					VarDeclarator vp = (VarDeclarator) p;
					String cvn = vp.plain_declarator.identifier;
					Symbol csym = Symbol.getSymbol(cvn);
					
					if(env.get(csym) != null)
						panic("Variable: " + cvn + " has been defined before!");
					
					Type real_type = get_type(vp, x.type_specifier.detail);
					if(has_initializer)
					{
						Type init_type = get_type(q);
						if(!real_type.isAssignableWith(init_type))
							panic("Failed to initialize variable: " + cvn);
					}

					env.put(csym, new VarEntry(real_type, has_initializer));
				}
				else if(p instanceof FuncDeclarator)
				{
					if(has_initializer)
						panic("Can not initialize a function!");

					FuncDeclarator fp = (FuncDeclarator) p;
					String cfn = fp.plain_declarator.identifier;
					Symbol csym = Symbol.getSymbol(cfn);
					Type real_type = get_type(fp, x.type_specifier.detail);

					if(env.get(csym) == null)
					{
						env.put(csym, new Function());
					}
					else
					{

					}
				}
				else
					panic("Internal Error!");

				y = y.next;
			}
		}
	}
	
	private Type get_type(FuncDeclarator x, Type y)
	{
		Type ret = y;
		int n = x.plain_declarator.star_list.cnt;
		for(int i = 0;i < n; i++)
			ret = new Pointer(ret);
		
		return ret;
	}
	
	private Type get_type(Initializer x) throws Exception
	{
		if(x.expr!=null)
			return x.expr.type;
		else
		{
			ArrayInitializer ret = new ArrayInitializer();
			
			InitializerList y = x.initializer_list;
			Type ft = get_type(y.head);
			ret.add(ft);
			
			while(y.next!=null)
			{
				y = y.next;
				
				Type ct = get_type(y.head);
				if(!ct.equals(ft))
					panic("Types in an initializer list must be identical to each other!");
				
				ret.add(ct);
			}
			
			return ret;
		}
	}

	@Override
	public void visit(FuncDeclarator x) throws Exception
	{
		x.plain_declarator.accept(this);
		
		local_env = new Table();
		x.param.accept(this);
	}

	@Override
	public void visit(VarDeclarator x) throws Exception
	{
		x.plain_declarator.accept(this);
		Iterator<Expr> eit = x.dimension.descendingIterator();
		while(eit.hasNext())
		{
			Expr e = eit.next();
			e.accept(this);
		}
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

		if (x.declarator instanceof FuncDeclarator && x.initializer != null)
			panic("FuncDeclarator shall not have initializer!");
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
		if (x == null)
			return;

		if (x.expr != null)
		{
			x.expr.accept(this);
			if (nest_cnt == 0 && !x.expr.isConst)
				panic("Initializer element is not constant!");
		}
		else if (x.initializer_list != null)
			x.initializer_list.accept(this);
		else
			panic("Internal Error!");
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
		check_complete_type(x.type_specifier);
		x.declarator_list.accept(this);

		if (x.type_specifier.type == TypeSpecifier.Type.VOID)
		{
			DeclaratorList y = x.declarator_list;
			while (y != null)
			{
				Declarator dr = y.head;
				if (dr instanceof FuncDeclarator)
					panic("FuncDeclarator can not be a member of a record!");

				check_void_var(dr);

				y = y.next;
			}
		}
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
		check_complete_type(x.type_specifier);
		x.declarator.accept(this);

		if (x.type_specifier.type == TypeSpecifier.Type.VOID)
			check_void_var(x.declarator);
	}

	private void check_void_var(Declarator x) throws Exception
	{
		if (x instanceof VarDeclarator)
		{
			VarDeclarator vdr = (VarDeclarator) x;
			if (vdr.plain_declarator.star_list.cnt == 0)
				panic("Variable: " + vdr.plain_declarator.identifier + " can not be declared as void");
		}
	}

	@Override
	public void visit(PlainDeclarator x) throws Exception
	{
		x.star_list.accept(this);
	}

	@Override
	public void visit(FuncDef x) throws Exception
	{
		check_complete_type(x.type_specifier);
		env.beginScope();
		local_env = env;
		x.params.accept(this);
		x.comp_stmt.accept(this);
		local_env = null;
		env.endScope();
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
		check_parameters(x, local_env);
	}

	private void check_parameters(ParameterList x, Table y) throws Exception
	{
		// y is provided outside

		ParameterList z = x;
		while (z != null)
		{
			check_plain_declaration(z.head, y);
			z = z.next;
		}
	}

	private void check_plain_declaration(PlainDeclaration x, Table y) throws Exception
	{
		check_complete_type(x.type_specifier);

		Type ct = x.type_specifier.detail;

		if (x.declarator instanceof FuncDeclarator)
			panic("Can not set FuncDeclarator to be params of Function!");

		VarDeclarator vdr = (VarDeclarator) x.declarator;
		String cvn = vdr.plain_declarator.identifier;
		Symbol csym = Symbol.getSymbol(cvn);

		if (y.get(csym) != null)
			panic("Duplicated param names!");

		ct = get_type(vdr, ct);

		y.put(csym, new TypeEntry(ct));
	}

	private Type get_type(VarDeclarator vdr, Type ct) throws Exception
	{
		int n = vdr.plain_declarator.star_list.cnt;
		for (int i = 0; i < n; i++)
			ct = new Pointer(ct);

		Iterator<Expr> eit = vdr.dimension.descendingIterator();
		while (eit.hasNext())
		{
			Expr e = eit.next();
			e.accept(this);// maybe sizeof

			if (!e.isConst)
				panic("Dimension should be a constant!");

			ct = new Array((int) e.value, ct);
		}

		return ct;
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
		// may be some record is just declared but not defined
		// just let it go and left further judging to context

		if (x.detail == null)
			panic("Internal Error!");
	}

	private void check_complete_type(TypeSpecifier x) throws Exception
	{
		x.accept(this);
		if (!x.detail.complete)
			panic("Incomplete type!");
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
