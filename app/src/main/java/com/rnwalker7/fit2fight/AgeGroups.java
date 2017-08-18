package com.rnwalker7.fit2fight;

/**
 * Created by rnwalker7 on 12/30/16.
 */

public class AgeGroups {
    private String sAbCirc[] = new String[11];
    private double dAbCirc[] = new double[11];
    private String sPushups[] = new String[45];
    private double dPushups[] = new double[45];
    private String sCrunches[] = new String[30];
    private double dCrunches[] = new double[30];
    private int nMaxTime[] = new int[30];
    private double dRunPts[] = new double[30];
    private int nWalkTime;
    private int nAbRecs = 0;
    private int nPushupRecs = 0;
    private int nCrunchRecs = 0;
    private int nRunRecs = 0;

    // Abdominal Circumference stuff
    public void setAbCircString(String s) {
        sAbCirc[nAbRecs] = s;
    }

    public void setAbCircPts(double d) {
        dAbCirc[nAbRecs] = d;
        nAbRecs++;
    }

    public String getAbCircString(int x) {
        return sAbCirc[x];
    }

    public double getAbCircPts(int x) {
        return dAbCirc[x];
    }

    public int getAbRecs() {
        return nAbRecs;
    }

    // Pushups stuff

    public void setPushupString(String s) {
        sPushups[nPushupRecs] = s;
    }

    public void setPushupPts(double d) {
        dPushups[nPushupRecs] = d;
        nPushupRecs++;
    }

    public String getPushupString(int x) {
        return sPushups[x];
    }

    public double getPushupPts(int x) {
        return dPushups[x];
    }

    public int getPushupRecs() {
        return nPushupRecs;
    }

    // Crunches stuff

    public void setCrunchString(String s) {
        sCrunches[nCrunchRecs] = s;
    }

    public void setCrunchPts(double d) {
        dCrunches[nCrunchRecs] = d;
        nCrunchRecs++;
    }

    public String getCrunchString(int x) {
        return sCrunches[x];
    }

    public double getCrunchPts(int x) {
        return dCrunches[x];
    }

    public int getCrunchRecs() {
        return nCrunchRecs;
    }

    // Run stuff

    public void setMaxTime(int i) {
        nMaxTime[nRunRecs] = i;
    }

    public void setRunPts(double d) {
        dRunPts[nRunRecs] = d;
        nRunRecs++;
    }

    public int getMaxTime(int x) {
        return nMaxTime[x];
    }

    public double getRunPts(int x) {
        return dRunPts[x];
    }

    public int getRunRecs() {
        return nRunRecs;
    }

    // Walk Test stuff

    public void setWalkTime(int i) {
        nWalkTime = i;
    }

    public int getWalkTime() {
        return nWalkTime;
    }

}
