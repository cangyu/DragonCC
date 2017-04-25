package compiler.translate;

public class LabelQuad extends Quad 
{
    public Label label;
    
	public LabelQuad(Label l)
	{
		label = l;
	}
	
	@Override
	public String toString() 
	{
		return label.toString() + ":";
	}
}
