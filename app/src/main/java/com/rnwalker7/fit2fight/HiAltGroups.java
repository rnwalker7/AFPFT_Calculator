package com.rnwalker7.fit2fight;

/**
 * Created by rnwalker7 on 1/5/17.
 */

public class HiAltGroups {
    private int nMaxTime[] = new int[30];
    private int nAltAdj[] = new int[30];
    private int nAltRecs = 0;
    private int nWalkTime[] = new int[10];
    private int nWalkRecs = 0;

    public void HiAltGroups() {

    }

    public void setMaxTime(int x) {
        nMaxTime[nAltRecs] = x;
    }

    public void setAltAdj(int x) {
        nAltAdj[nAltRecs] = x;
        nAltRecs++;
    }

    public void setWalkTime(int x) {
        nWalkTime[nWalkRecs] = x;
        nWalkRecs++;
    }

    public int getAltRecs() {
        return nAltRecs;
    }

    public int getMaxTime(int x) {
        return nMaxTime[x];
    }

    public int getAltAdj(int x) {
        return nAltAdj[x];
    }

}
