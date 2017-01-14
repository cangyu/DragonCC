package compiler.ast;

import java.util.LinkedList;

public class Initializers 
{
	public String CN = getClass().getName();
	public LinkedList<Initializer> comp;
	
	public Initializers(Initializer _i)
	{
		comp = new LinkedList<Initializer>();
		comp.add(_i);
	}
}
