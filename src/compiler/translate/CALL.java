package compiler.translate;

public class CALL extends Quad 
{
    public Label func_name;
    public Temp[] params;
    public Temp result;
    
	public CALL(Label name, Temp[] p, Temp r) 
	{
	    func_name = name;
		params = p;
		result = r;
	}

	@Override
	public String toString() 
	{
		String call = result.toString() + " <- " + func_name + "(";
		if (params != null)
		{
		    call += params[0].toString();
			for (int i = 1; i < params.length; i++) 
				call += ',' + params[i].toString();
		}
		call += ")";
		return call;
	}

}
