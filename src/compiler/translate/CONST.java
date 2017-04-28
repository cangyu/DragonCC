package compiler.translate;

public class CONST extends Oprand
{
    public int value;

    public CONST(int v)
    {
        value = v;
    }

    @Override
    public String toString()
    {
        String ret = "";
        ret += value;
        return ret;
    }
}
