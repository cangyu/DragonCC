package compiler.translate;

import compiler.translate.ir.Stm;
import compiler.translate.ir.TEMP;
import compiler.translate.ir.MOVE;
import compiler.translate.ir.CONST;
import compiler.translate.ir.SEQ;
import compiler.translate.ir.ESEQ;
import compiler.translate.ir.LABEL;
import compiler.translate.temp.Label;
import compiler.translate.temp.Temp;

public abstract class Cx extends Exp
{
    @Override
    public compiler.translate.ir.Exp unEx()
    {
        Temp r = new Temp();
        Label t = new Label();
        Label f = new Label();

        return new ESEQ(new SEQ(new MOVE(new TEMP(r), 
                                         new CONST(1)),
                                new SEQ(unCx(t, f), 
                                        new SEQ(new LABEL(f), 
                                                new SEQ(new MOVE(new TEMP(r), 
                                                                 new CONST(0)), 
                                                        new LABEL(t))))), 
                        new TEMP(r));
    }

    @Override
    public Stm unNx()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public abstract Stm unCx(Label t, Label f);
}
