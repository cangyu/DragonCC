package compiler.semantic;

public final class VarEntry extends Entry 
{
	public boolean hasInitialized = false;
	
	public VarEntry(Type t, boolean hi)
	{
		super(t, true);
	}
}
