package compiler.ast;

import java.util.LinkedList;

public class Initializers 
{
	public LinkedList<Initializer> initializer_list;
	
	public Initializers(Initializer _i)
	{
		initializer_list=new LinkedList<Initializer>();
		initializer_list.add(_i);
	}
}
