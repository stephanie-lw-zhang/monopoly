package frontend.views;

import configuration.XMLData;
import javafx.event.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class LogView {

    private XMLData myData;
    public ArrayList<String> myOutput;

    public LogView() {
        try {
            myData = new XMLData("OriginalMonopoly.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        myOutput = new ArrayList<>();
        myOutput.add("Welcome to " + myData.getMonopolyType() + ".");
    }

    public void print(){
    }


}

