package compiler.ast;

import java.util.LinkedList;

public class InitDeclarators 
{
	public LinkedList<InitDeclarator> idecl_list;
	
	public InitDeclarators(InitDeclarator _idecl)
	{
		idecl_list=new LinkedList<InitDeclarator>();
		idecl_list.add(_idecl);
	}
}
