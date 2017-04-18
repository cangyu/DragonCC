package compiler.translate;

public class InReg extends Access
{
	Temp reg = new Temp();
	
	@Override
	public Exp access(Exp fp)
	{
		return reg;
	}

}
