package com.falstad.jcircsim;

public class ACRailElm extends RailElm
{
    public ACRailElm(int xx, int yy)
    {
        super(xx, yy, WF_AC);
    }

    public Class getDumpClass()
    {
        return RailElm.class;
    }
}
