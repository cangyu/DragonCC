package compiler.semantic;

public final class VarEntry extends Entry 
{
	public boolean hasInitialized;
	
	public VarEntry(Type t, boolean hi)
	{
		super(t, true);
		hasInitialized = hi;
	}
}
