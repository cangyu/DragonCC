package compiler.ast;

import java.util.LinkedList;

public class Program 
{
	public LinkedList<DeclOrFuncDef> components;
	
	public Program(DeclOrFuncDef dof)
	{
		components=new LinkedList<DeclOrFuncDef>();
		components.add(dof);
	}
}
