package compiler.ast;

import java.util.LinkedList;

public class Program 
{
	public String CN = getClass().getName();
	public LinkedList<DeclOrFuncDef> comp;
	
	public Program(DeclOrFuncDef _df)
	{
		comp = new LinkedList<DeclOrFuncDef>();
		comp.add(_df);
	}
}
