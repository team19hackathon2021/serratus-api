package io.serratus.api.enus.wrap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Keyword: classSimpleNameWrap
 * Map.hackathonMission: to create a new Java class Wrap to help with initialization of Java objects and pass back newly initialized class objects or null. 
 * Map.hackathonColumn: Develop Base Classes
 * Map.hackathonLabels: Java
 **/

public class Wrap<DEV> {
    public String var;
    public DEV o;
    public Boolean alreadyInitialized = false;

    public Wrap<DEV> var(String o) {
        var = o;
        return this;
    }

    public Wrap<DEV> o(DEV o) {
        this.o = o;
        return this;
    }

    public Wrap<DEV> alreadyInitialized(Boolean o) {
        alreadyInitialized = o;
        return this;
    }
}