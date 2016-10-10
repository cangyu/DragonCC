package compiler.ast;

import java.util.LinkedList;

public class NonInitDecls
{
	public LinkedList<NonInitDecl> nids;
	
	public NonInitDecls(NonInitDecl _nid)
	{
		nids=new LinkedList<NonInitDecl>();
		nids.add(_nid);
	}
}
