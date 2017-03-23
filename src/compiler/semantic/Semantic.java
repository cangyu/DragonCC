package compiler.semantic;

import java.io.*;
import java.util.*;
import compiler.ast.*;
import compiler.syntactic.*;

public class Semantic
{
	private Table env, tag_env;
	private Program prog_start;

	public Semantic(Program prog)
	{
		env = new Table();
		tag_env = new Table();
		prog_start = (Program) prog;
	}

	public void check() throws Exception
	{
		check(prog_start);
	}

	private void check(Program x) throws Exception
	{
		Program y = x;
		while (y != null)
		{
			if (y.head instanceof Declaration)
				check((Declaration) y.head);
			else
				check((FuncDef) y.head);

			y = y.next;
		}
	}

	private Type check(TypeSpecifier x, int flag) throws Exception
	{
		TypeSpecifier.Type ct = x.type;

		if (ct == TypeSpecifier.Type.VOID)
			return Void.getInstance();
		else if (ct == TypeSpecifier.Type.INT)
			return Int.getInstance();
		else if (ct == TypeSpecifier.Type.CHAR)
			return Char.getInstance();
		else if (ct == TypeSpecifier.Type.STRUCT)
		{
			if (x.comp == null)
			{
				if (flag == 0)// struct node;
				{
					Symbol ss = Symbol.getSymbol(x.tag);
					Entry ce = (Entry) tag_env.get(ss);

					if (ce != null)
					{
						if (ce.type instanceof Struct)
							return ce.type;
						else
							throw new Exception(x.tag + " is not defined as struct!");
					}
					else
					{
						Struct ret = new Struct(x.tag, null);
						tag_env.put(ss, new TypeEntry(ret));
						return ret;
					}
				}
				else if (flag == 1)// struct node a;
				{
					Symbol ss = Symbol.getSymbol(x.tag);
					Entry ce = (Entry) tag_env.get(ss);

					if (ce != null)
					{
						if (ce.type instanceof Struct)
							return ce.type;
						else
							throw new Exception(x.tag + " is not defined as struct!");
					}
					else
						throw new Exception("struct " + x.tag + " is undefined!");
				}
				else if (flag == 2)// struct node *a;
				{
					Symbol ss = Symbol.getSymbol(x.tag);
					Entry ce = (Entry) tag_env.get(ss);

					if (ce != null)
					{
						if (ce.type instanceof Struct)
							return ce.type;
						else
							throw new Exception(x.tag + " is not defined as struct!");
					}
					else
					{
						Name ret = new Name(true, x.tag);
						return ret;
					}
				}
				else
					throw new Exception("Internal Error!");
			}
			else
			{
				if (x.tag == null)// struct { ... }
				{
					Table ccomp = new Table();
					check(x.comp, ccomp);
					return new Struct(x.tag, ccomp);
				}
				else// struct node { ... }
				{
					Symbol csym = Symbol.getSymbol(x.tag);
					Entry ce = (Entry) tag_env.get(csym);
					Table ccomp = null;

					if (ce != null)// may be declared before
					{
						if (ce.type instanceof Struct)
						{
							Struct cst = (Struct) ce.type;
							if (cst.comp != null)
								throw new Exception("struct " + x.tag + " has already been defined!");
							else
							{
								cst.comp = new Table();
								check(x.comp, cst.comp);
								return ce.type;
							}
						}
						else
							throw new Exception(x.tag + " has been declared differently!");
					}
					else// first time meet
					{
						ccomp = new Table();
						check(x.comp, ccomp);
						Struct ret = new Struct(x.tag, ccomp);
						tag_env.put(csym, ret);
						return ret;
					}
				}
			}
		}
		else if (ct == TypeSpecifier.Type.UNION)
		{
			// TODO
			return null;
		}
		else
			throw new Exception("Internal Error!");
	}

	private void check(NonInitDeclarationList x, Table ccomp) throws Exception
	{
		NonInitDeclarationList y = x;
		while (y != null)
		{
			NonInitDeclaration z = y.head;
			TypeSpecifier cts = z.type_specifier;
			DeclaratorList w = z.declarator_list;
			while (w != null)
			{
				if (w.head instanceof VarDeclarator)
				{
					VarDeclarator vdr = (VarDeclarator) w.head;

					String vdrr = vdr.plain_declarator.identifier;
					int sc = vdr.plain_declarator.star_list.cnt;
					Symbol csym = Symbol.getSymbol(vdrr);
					if (ccomp.get(csym) != null)
						throw new Exception(vdrr + " has already been defined in this scope!");

					Type def_type = sc == 0 ? check(cts, 1) : check(cts, 2);
					Type real_type = check(vdr.plain_declarator, def_type);
					if(real_type instanceof Void)
						throw new Exception("Variable " + vdrr + " can not be declared as void");

					ccomp.put(csym, new TypeEntry(real_type));
				}
				else
				{
					String fcn = ((FuncDeclarator) w.head).plain_declarator.identifier;
					throw new Exception("Function: " + fcn + " can not be declared here!");
				}

				w = w.next;
			}

			y = y.next;
		}
	}

	private Type check(PlainDeclarator x, Type dt)
	{
		Type ret = dt;

		int sc = x.star_list.cnt;
		for (int i = 0; i < sc; i++)
			ret = new Pointer(ret);

		return ret;
	}

	private void check(Declaration x) throws Exception
	{
		InitDeclaratorList y = x.init_declarator_list;

		if (y == null)// TypeSpecifier;
		{
			switch (x.type_specifier.type)
			{
			case STRUCT:
			case UNION:
				check(x.type_specifier, 0);
				break;
			case VOID:
			case INT:
			case CHAR:
				throw new Exception("Meaningless declaration of intrinsic type!");
			default:
				break;
			}
		}
		else// TypeSpecifier InitDeclaratorList
		{
			while (y != null)
			{
				InitDeclarator z = y.head;

				Declarator p = z.declarator;
				Initializer q = z.initializer;

				if (p instanceof VarDeclarator)
				{
					VarDeclarator vp = (VarDeclarator) p;

					Type real_type = check(vp, x.type_specifier);
					Type init_type = check(q);

					// TODO
					// check isAssignable

					Symbol csym = Symbol.getSymbol(vp.plain_declarator.identifier);

					env.put(csym, new VarEntry(real_type));
				}
				else if (p instanceof FuncDeclarator)
				{
					if (q != null)
						throw new Exception("Can not initialize a function!");

					FuncDeclarator fp = (FuncDeclarator) p;

					Type real_type = check(fp, x.type_specifier);// ???

					Symbol csym = Symbol.getSymbol(fp.plain_declarator.identifier);

					env.put(csym, new FuncEntry(real_type));// ???
				}
				else
					throw new Exception("Internal Error!");

				y = y.next;
			}
		}
	}

	private void check(FuncDef x) throws Exception
	{

	}

	private Type check(VarDeclarator x, TypeSpecifier y) throws Exception
	{
		int sc = x.plain_declarator.star_list.cnt;
		Type def_type = sc == 0 ? check(y, 1) : check(y, 2);
		Type real_type = check(x.plain_declarator, def_type);

		if(real_type instanceof Void)
			throw new Exception("Variable " + x.plain_declarator.identifier + " can not be declared as void");
		
		// TODO
		// check dimension

		return real_type;
	}

	private Type check(Initializer x) throws Exception
	{

		return null;
	}

	private Type check(FuncDeclarator x, TypeSpecifier y) throws Exception
	{

		return null;
	}
}
