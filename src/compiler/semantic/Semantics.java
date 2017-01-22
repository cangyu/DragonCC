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
	
	public void check()
	{
		env = new Table();
		checkProgram(prog);
	}
	
	public void checkProgram(Program _prog)
	{
		for(GeneralDeclaration gd : _prog.comp)
		{
			if(gd instanceof Decl)
				checkDecl((Decl)gd);
			else
				checkFuncDef((FuncDef)gd);
		}
	}
	
	public void checkDecl(Decl _decl)
	{
		
	}
	
	public void checkFuncDef(FuncDef _func)
	{
		
	}
	
}
