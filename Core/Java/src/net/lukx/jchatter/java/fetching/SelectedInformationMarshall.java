package net.lukx.jchatter.java.fetching;

import net.lukx.jchatter.java.supporting.CurrentValues;
import net.lukx.jchatter.java.supporting.Repos;
import sun.util.resources.cldr.et.CurrencyNames_et;

public class SelectedInformationMarshall {

    private Repos repos;
    private CurrentValues currentValues;

    public SelectedInformationMarshall(Repos repos, CurrentValues currentValues){
        this.repos =repos;
        this.currentValues = currentValues;
    }
}
