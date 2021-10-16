package ru.evistix28;

import javafx.util.Pair;

import java.util.ArrayList;

public class Data {
    public ArrayList<Pair<String, String>> rewritingRules;

    public Data(ArrayList<String> data) {
        rewritingRules = new ArrayList<>(0);
        for(int i = 0; i < data.size(); ++i) {
            readRewritingRule(data.get(i));
        }
    }

    private void readRewritingRule(String data) {
        data = data.replaceAll(" ", "");
        int start = 0;
        int end = 0;
        int itr = 0;
        String leftPart;
        String rightPart;
        while(data.charAt(itr) != '-') {
            itr++;
        }
        end = itr++;
        leftPart = data.substring(start, end);
        start = ++itr;
        while(itr < data.length() && data.charAt(itr) != '\n') {
            itr++;
        }
        end = itr;
        rightPart = data.substring(start, end);
        rewritingRules.add(new Pair<>(leftPart, rightPart));
    }
}
