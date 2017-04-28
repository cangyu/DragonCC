package compiler.translate;

public class Temp
{
    private static int cnt = 0;
    public int index;

    public Temp()
    {
        index = cnt++;
    }

    @Override
    public String toString()
    {
        return "t" + index;
    }
}
