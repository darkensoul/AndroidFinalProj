package com.example.brittany.hcd;

/**
 * Created by Britt-Britt on 11/7/2015.
 */
public class Symptom  extends Entry {

    private int pain;
    private int duration;
    private String id;
    private String date;

    public int getPainLevel(){return pain;}
    public void setPainLevel(int p){this.pain=p;}

    public int getDuration(){return duration;}
    public void setDuration(int d){this.duration=d;}

    public String getId() {return id;}

    public String getDate(){return date;}

    public String roll(){

        return getName()+","+getDescription()+","+Integer.toString(getPainLevel())+","+Integer.toString(getDuration())+","+getId()+","+getDate();
    }

    public Symptom(String n, String d, int p, int dur, String i, String day){
        this.setName(n);
        this.setDescription(d);
        this.pain=p;
        this.duration=dur;
        this.id=i;
        this.date=day;
    }

}
