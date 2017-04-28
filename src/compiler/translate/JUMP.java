package compiler.translate;

public class JUMP extends Quad 
{
	public Label label;

	public JUMP(Label l) 
	{
		label = l;
	}
	
	@Override
	public String toString() 
	{
		return "j " + label.toString();
	}
}
