package org.senani.sachith.story2.Other;

/**
 * Created by sachith on 11/5/17.
 */

public class StoryR {

    private String i;
    private String b;
    private String w;
    private String t;
    private Long l=new Long(0);

    public StoryR(String i, String t, String b, String w, Long l) {
        this.setI(i);
        this.setB(b);
        this.setW(w);
        this.setT(t);
        this.setL(l);
    }

    public StoryR(String i, String t, String b, String w) {
        this.setI(i);
        this.setB(b);
        this.setW(w);
        this.setT(t);
    }

    public StoryR(String i, String t, String b) {
        this.i = i;
        this.b = b;
        this.t = t;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Long getL() {
        return l;
    }

    public void setL(Long l) {
        this.l = l;
    }
}
