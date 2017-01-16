package compiler.ast;

import java.util.LinkedList;

public class NonInitDecls
{
	public LinkedList<NonInitDecl> comp;
	
	public NonInitDecls(NonInitDecl _nid)
	{
		comp = new LinkedList<NonInitDecl>();
		comp.add(_nid);
	}
}
