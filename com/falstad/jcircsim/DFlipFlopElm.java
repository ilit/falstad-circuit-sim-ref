package com.falstad.jcircsim;

import java.awt.*;
import java.util.StringTokenizer;

public class DFlipFlopElm extends ChipElm
{
    public final int FLAG_RESET = 2;

    public boolean hasReset()
    {
        return (flags & FLAG_RESET) != 0;
    }

    public DFlipFlopElm(int xx, int yy)
    {
        super(xx, yy);
    }

    public DFlipFlopElm(int xa, int ya, int xb, int yb, int f, StringTokenizer st)
    {
        super(xa, ya, xb, yb, f, st);
        pins[2].value = !pins[1].value;
    }

    public String getChipName()
    {
        return "D flip-flop";
    }

    public void setupPins()
    {
        sizeX = 2;
        sizeY = 3;
        pins = new Pin[getPostCount()];
        pins[0] = new Pin(0, SIDE_W, "D");
        pins[1] = new Pin(0, SIDE_E, "Q");
        pins[1].output = pins[1].state = true;
        pins[2] = new Pin(2, SIDE_E, "Q");
        pins[2].output = true;
        pins[2].lineOver = true;
        pins[3] = new Pin(1, SIDE_W, "");
        pins[3].clock = true;
        if (hasReset())
            pins[4] = new Pin(2, SIDE_W, "R");
    }

    public int getPostCount()
    {
        return hasReset() ? 5 : 4;
    }

    public int getVoltageSourceCount()
    {
        return 2;
    }

    public void execute()
    {
        if (pins[3].value && !lastClock)
        {
            pins[1].value = pins[0].value;
            pins[2].value = !pins[0].value;
        }
        if (pins.length > 4 && pins[4].value)
        {
            pins[1].value = false;
            pins[2].value = true;
        }
        lastClock = pins[3].value;
    }

    public int getDumpType()
    {
        return 155;
    }

    public EditInfo getEditInfo(int n)
    {
        if (n == 2)
        {
            EditInfo ei = new EditInfo("", 0, -1, -1);
            ei.checkbox = new Checkbox("Reset Pin", hasReset());
            return ei;
        }
        return super.getEditInfo(n);
    }

    public void setEditValue(int n, EditInfo ei)
    {
        if (n == 2)
        {
            if (ei.checkbox.getState())
                flags |= FLAG_RESET;
            else
                flags &= ~FLAG_RESET;
            setupPins();
            allocNodes();
            setPoints();
        }
        super.setEditValue(n, ei);
    }
}
