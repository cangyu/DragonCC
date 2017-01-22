package compiler.semantic;

import java.util.*;
import compiler.ast.*;

public class Semantics
{
	public Program prog;
	
	public Semantics(Object _obj) throws Exception
	{
		if(_obj instanceof Program)
			prog = (Program)_obj;
		else
			throw new Exception("Invalid Program!");
	}
	
	public void check()
	{
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
