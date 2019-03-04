package org.senani.sachith.story2.Other;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sachith on 12/31/17.
 */

public class User implements Serializable {

    private String name;
    private ArrayList<String> story;

    public User(String name) {
        this.name = name;
        setStory(new ArrayList<String>());
        getStory().add("");
    }

    public User() {

    }

    public User(String name, ArrayList<String> story) {
        this.name = name;
        this.story = story;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getStory() {
        return story;
    }

    public void setStory(ArrayList<String> story) {
        this.story = story;
    }
}
