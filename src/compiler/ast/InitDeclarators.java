package compiler.ast;

import java.util.LinkedList;

public class InitDeclarators 
{
	public String CN = getClass().getName();
	public LinkedList<InitDeclarator> comp;
	
	public InitDeclarators(InitDeclarator _idecl)
	{
		comp = new LinkedList<InitDeclarator>();
		comp.add(_idecl);
	}
}
