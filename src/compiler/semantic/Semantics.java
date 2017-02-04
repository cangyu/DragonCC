package compiler.semantic;

import java.util.*;
import compiler.ast.*;

public class Semantics
{
	public Program prog;
	public Table env;
	
	public Semantics(Object _obj) throws Exception
	{
		if(_obj instanceof Program)
			prog = (Program)_obj;
		else
			throw new Exception("Invalid Program!");
	}
	
	public void check() throws Exception
	{
		env = new Table();
		checkProgram(prog);
	}
	
	public void checkProgram(Program _prog) throws Exception
	{
		for(GeneralDeclaration gd : _prog.comp)
		{
			if(gd instanceof Decl)
				checkDecl((Decl)gd);
			else
				checkFuncDef((FuncDef)gd);
		}
	}
	
	public void checkDecl(Decl _decl) throws Exception
	{
		Type type = checkTypeSpecifier(_decl.type_specifier);
		
		//只是类型声明
		if(_decl.init_declarators == null)
			return;
		
		//逐一检查
		for(InitDeclarator elem : _decl.init_declarators.comp)
		{
			Type cur_type = checkDeclarator(elem.declarator);
			Type init_type = checkInitializer(elem.initializer);
			
		}	
	}
	
	public Type checkTypeSpecifier(TypeSpecifier _ts) throws Exception
	{
		switch(_ts.type)
		{
		case VOID:
			return new Void();
		case INT:
			return new Int();
		case CHAR:
			return new Char();
		case STRUCT:
			if(_ts.non_init_decls == null)
			{
				String u = ("struct "+_ts.id).intern();
				Symbol sym = new Symbol(u);
				Object r = env.get(sym);
				if(r == null)
					return new Name();
				else
					return new Struct();
			}
			else
			{
				String u = ("struct "+_ts.id).intern();
				Symbol sym = new Symbol(u);
				Object r = env.get(sym);
				if(r != null)
					throw new Exception("struct " + _ts.id + " has already been declared in this scope!");
			}
		case UNION:
			if(_ts.non_init_decls == null)
			{
				String u = ("union "+_ts.id).intern();
				Symbol sym = new Symbol(u);
				Object r = env.get(sym);
				if(r == null)
					return new Name();
				else
					return new Union();	
			}
			else
			{
				String u = ("union "+_ts.id).intern();
				Symbol sym = new Symbol(u);
				Object r = env.get(sym);
				if(r != null)
					throw new Exception("union " + _ts.id + " has already been declared in this scope!");
			}
		default:
			return null;
		}
	}
	
	public Type checkDeclarator(Declarator _dr) throws Exception
	{
		Type cur_type = checkPlainDeclarator(_dr.plain_declarator);
		
		//check other part
		
	}
	
	public Type checkInitializer(Initializer _ir) throws Exception
	{
		Type type=null;
		
		if(_ir == null)
			return null;
		
		if(_ir.value != null)
			return checkExpr(_ir.value);
		else
		{			
			for(Initializer elem: _ir.initializers.comp)
			{
				Type cur_type = checkInitializer(elem);
				if(cur_type.equals(type))
					throw new Exception("Different types in one bracket");
			}
			
			return type;
		}
	}
	
	public Type checkExpr(Expr e)
	{
		
	}
	
	public Type checkPlainDeclarator(PlainDeclarator _pdr) throws Exception
	{
		int num = _pdr.stars.cnt;
		Type cur_type = null;
		
		for(int i=0; i<num; i++)
			cur_type = new Pointer(cur_type);
		
		return null;
	}
	
	public void checkFuncDef(FuncDef _func) throws Exception
	{
		
	}
}
