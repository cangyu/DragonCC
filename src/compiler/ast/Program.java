package compiler.ast;

import java.util.LinkedList;

public class Program 
{
	public LinkedList<GeneralDeclaration> comp;
	
	public Program(GeneralDeclaration _df)
	{
		comp = new LinkedList<GeneralDeclaration>();
		comp.add(_df);
	}
}
