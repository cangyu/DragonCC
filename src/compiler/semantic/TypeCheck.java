package compiler.semantic;

import java.util.*;
import compiler.ast.*;

public class TypeCheck implements ASTNodeVisitor
{
	public int phase;
	public Table rte;

	public static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}
	
	public TypeCheck(Table _te)
	{
		phase = 0;
		rte = _te;
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
	}

	@Override
	public void visit(PostfixExpr x) throws Exception
	{
		return;
	}

	@Override
	public void visit(PrimaryExpr x) throws Exception
	{
		return;
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
		if (phase == 1)
			ts_1pass(x);
		else if (phase == 2)
			ts_2pass(x);
		else if(phase == 3)
			ts_3pass(x);
		else
			panic("Invalid phase number!");
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

	private void ts_1pass(TypeSpecifier x) throws Exception
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
			ts_1pass_struct(x);
			break;
		case UNION:
			ts_1pass_union(x);
			break;
		default:
			panic("Internal Error!");
		}
	}

	private void ts_1pass_struct(TypeSpecifier x) throws Exception
	{
		if (x.comp == null)// struct node
		{
			Symbol ss = Symbol.getSymbol(x.tag);
			Entry ce = (Entry) rte.get(ss);

			if (ce != null)
			{
				if (!(ce.type instanceof Struct))
					panic(x.tag + " is not declared as struct!");
				
				x.detail = ce.type;
			}
			else
			{
				x.detail = new Struct(x.tag, null);
				rte.put(ss, new TypeEntry(x.detail));
			}
		}
		else
		{
			if (x.tag == null) // struct { ... }
			{
				x.detail = new Struct(null, new Table());
				x.comp.accept(this);
			}
			else// struct node { ... }
			{
				Symbol csym = Symbol.getSymbol(x.tag);
				TypeEntry ce = (TypeEntry) rte.get(csym);

				if (ce != null) // may be declared before
				{
					if (!(ce.type instanceof Struct))
						panic(x.tag + " is not declared as struct!");

					Struct cst = (Struct) ce.type;
					if (cst.comp != null)
						panic("struct " + x.tag + " has been declared before!");

					cst.comp = new Table();
					x.detail = cst;
					x.comp.accept(this);
				}
				else// first time meet
				{
					x.detail = new Struct(x.tag, new Table());
					rte.put(csym, new TypeEntry(x.detail));
					x.comp.accept(this);
				}
			}
		}
	}

	private void ts_1pass_union(TypeSpecifier x) throws Exception
	{
		if (x.comp == null)// union node
		{
			Symbol ss = Symbol.getSymbol(x.tag);
			Entry ce = (Entry) rte.get(ss);

			if (ce != null)
			{
				if (!(ce.type instanceof Union))
					panic(x.tag + " is not declared as union!");
				
				x.detail = ce.type;
			}
			else
			{
				x.detail = new Union(x.tag, null);
				rte.put(ss, new TypeEntry(x.detail));
			}
		}
		else
		{
			if (x.tag == null) // union { ... }
			{
				x.detail = new Union(null, new Table());
				x.comp.accept(this);
			}
			else// union node { ... }
			{
				Symbol csym = Symbol.getSymbol(x.tag);
				Entry ce = (Entry) rte.get(csym);

				if (ce != null) // may be declared before
				{
					if (!(ce.type instanceof Union))
						panic(x.tag + " is not declared as union!");

					Union cst = (Union) ce.type;
					if (cst.comp != null)
						panic("union " + x.tag + " has been declared before!");

					cst.comp = new Table();
					x.detail = cst;
					x.comp.accept(this);
				}
				else// first time meet
				{
					x.detail = new Union(x.tag, new Table());
					rte.put(csym, new TypeEntry(x.detail));
					x.comp.accept(this);
				}
			}
		}
	}

	private void ts_2pass(TypeSpecifier x) throws Exception
	{
		switch (x.type)
		{
		case VOID:
		case INT:
		case CHAR:
			break;
		case STRUCT:
			ts_2pass_struct(x);
			break;
		case UNION:
			ts_2pass_union(x);
			break;
		default:
			panic("Internal Error!");
		}
	}

	private void ts_2pass_struct(TypeSpecifier x) throws Exception
	{
		if (x.comp != null)
		{
			Struct cst = (Struct) x.detail;
			x.comp.accept(this);
			check_contents(x.comp, cst.comp);
		}
	}

	private void ts_2pass_union(TypeSpecifier x) throws Exception
	{
		if (x.comp != null)
		{
			Union cst = (Union) x.detail;
			x.comp.accept(this);
			check_contents(x.comp, cst.comp);
		}
	}

	private void check_contents(NonInitDeclarationList x, Table ccomp) throws Exception
	{
		NonInitDeclarationList y = x;
		while (y != null)
		{
			TypeSpecifier cts = y.head.type_specifier;
			DeclaratorList w = y.head.declarator_list;
			while (w != null)
			{
				if (w.head instanceof VarDeclarator)
				{
					VarDeclarator vdr = (VarDeclarator) w.head;
					String vdrr = vdr.plain_declarator.identifier;
					Symbol csym = Symbol.getSymbol(vdrr);
					if (ccomp.get(csym) != null)
						panic(vdrr + " has already been defined in this scope!");

					// int **a
					Type real_type = cts.detail;
					int sc = vdr.plain_declarator.star_list.cnt;
					for(int i=0;i<sc; i++)
						real_type = new Pointer(real_type);

					// int **a[...][...]
					Iterator<Expr> eit = vdr.dimension.descendingIterator();
					while(eit.hasNext())
					{
						Expr ce = eit.next();
						if(!ce.isConst)
							panic("Dimension of an array shall be constant!");
						
						int n = (int)ce.value;
						real_type = new Array(n, real_type);
					}	
					
					ccomp.put(csym, new TypeEntry(real_type));
				}
				else
				{
					String fcn = ((FuncDeclarator) w.head).plain_declarator.identifier;
					panic("Function: " + fcn + " can not be declared here!");
				}

				w = w.next;
			}

			y = y.next;
		}
	}
	
	private void ts_3pass(TypeSpecifier x) throws Exception
	{
		switch(x.type)
		{
		case VOID:
		case CHAR:
		case INT:
			return;
		default:
			break;
		}
		
		if(x.detail.complete)
			return;
		
		Record crt = (Record)x.detail;
		detect_circuit(crt);
		
		x.detail.complete = true;
	}
	
	private void detect_circuit(Record x) throws Exception
	{
		if(x.visited)
			panic("Incomplete record type detected!");
		else
			x.visited = true;
		
		Enumeration<Symbol> es = x.comp.keys();
		while(es.hasMoreElements())
		{
			Type te = ((TypeEntry)x.comp.get(es.nextElement())).type;
			if(te instanceof Pointer)
				continue;
			
			if(te instanceof Array)
				te = get_base(te);
			
			if(te instanceof Record)
				detect_circuit((Record) te);
		}
		
		es = x.comp.keys();
		while(es.hasMoreElements())
		{
			Type te = ((TypeEntry)x.comp.get(es.nextElement())).type;
			if(te instanceof Pointer)
				continue;
			
			if(te instanceof Array)
				te = get_base(te);
			
			if(te instanceof Record && !te.complete)
				panic("Incomplete Record detected!");
		}
		
		x.complete = true;
	}
	
	private static Type get_base(Type x)
	{
		if(x instanceof Array)
			return get_base(((Array)x).elem_type);
		else
			return x;
	}
}
