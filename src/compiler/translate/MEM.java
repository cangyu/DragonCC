package compiler.translate;

public class MEM extends Oprand
{
    public int offset = 0;
    public int length = 4;
    public Temp base;

    public MEM(Temp t)
    {
        base = t;
    }

    public MEM(Temp t, int o)
    {
        base = t;
        offset = o;
    }

    public MEM(int o)
    {
        this(null, o);
    }

    @Override
    public String toString()
    {
        if (base == null)
            return offset + "($zero)";
        else
            return offset + "(" + base.toString() + ")";
    }
}
