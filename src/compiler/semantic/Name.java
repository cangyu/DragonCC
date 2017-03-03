package compiler.semantic;

public final class Name extends Type 
{
	String name;
	Table env;
	
	public Name(String _name, Table _env)
	{
		name = _name;
		env = _env;
	}
	
	@Override
    public boolean equals(Type rhs)
    {
        if(this == rhs)
            return true;
        else if(rhs instanceof Name)
            return this.name.intern() == (((Name) rhs).name).intern();
        else
            return false;
    }
	
	@Override
    public boolean isAssignable(Type rhs)
    {
        return false;
    }
}
