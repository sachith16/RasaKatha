package org.senani.sachith.story2.Other;

import java.io.Serializable;

/**
 * Created by sachith on 9/9/17.
 */

public class Story implements Serializable{

    private long l;
    private String b;
    private String w;
    private String t;

    public Story(String t,String b, String w) {
        this.l=0;
        this.b = b;
        this.w = w;
        this.t = t;
    }

    public Story() {
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

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }
}

