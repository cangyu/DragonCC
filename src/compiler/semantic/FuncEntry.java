package compiler.semantic;

import java.util.LinkedList;
import compiler.translate.Quad;
import compiler.translate.Frame;

public final class FuncEntry extends Entry 
{
    public Frame frame;
    public LinkedList<Quad> code;
    public LinkedList<Integer> offset;
    
    public FuncEntry(Function x)
	{
		super(x, false);
		frame = null;
		code = null;
	}
}
