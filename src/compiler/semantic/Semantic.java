package compiler.semantic;

import java.util.*;
import compiler.ast.*;
import compiler.syntactic.*;

public class Semantic
{
	Table tag_env, env;
	Program prog;
	
	private static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}
	
	public Semantic(Program x)
	{
		tag_env=new Table();
		env = new Table();
		prog = x;
	}
	
	public void check() throws Exception
	{
		checkProgram(prog);
	}
	
	private void checkProgram(Program x) throws Exception
	{
		Program y = x;
		while(y!=null)
		{
			checkProgramComp(y.head);
			y=y.next;
		}
	}
	
	private void checkProgramComp(ProgramComp x) throws Exception
	{
		if(x instanceof Declaration)
			checkDeclaration((Declaration)x);
		else
			checkFuncDef((FuncDef)x);
	}
	
	private void checkDeclaration(Declaration x) throws Exception
	{
		Type def_type = checkTypeSpecifier(x.type_specifier);
		
		if(x.init_declarator_list==null)
			return;
		
		
		
	}
	
	private void checkFuncDef(FuncDef x) throws Exception
	{
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
