package compiler.semantic;

import java.util.LinkedList;
import compiler.translate.Quad;

public final class FuncEntry extends Entry 
{
    public LinkedList<Quad> code;
    public LinkedList<Integer> offset;
    
    public FuncEntry(Function x)
	{
		super(x, false);
		code = null;
	}
}
