package ru.evistix28;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static Pair<ArrayList<String>, String> unificate(Data.Term t1, Data.Term t2) {
        ArrayList<String> sub = new ArrayList<>(0);
        String unificator = "";
        if(t1.name != t2.name) {
            if(t1.type == 'v') {
                sub.add("Substitution in first Term: " + t1.name + ":= " + t2.toString());
                return new Pair<>(sub, t2.toString());
            } else if(t2.type == 'v') {
                sub.add("Substitution in second Term " + t2.name + ":= " + t1.toString());
                return new Pair<>(sub, t1.toString());
            } else {
                System.out.println("Unable unificate");
                System.exit(0);
            }
        }
        unificator += t1.name;
        if(t1.type == 's') {
            unificator += "(";
            for(int i = 0; i < t1.numOfArgs; ++i) {
                Pair<ArrayList<String>, String> res = unificate(t1.args.get(i), t2.args.get(i));
                unificator += res.getValue();
                sub.addAll(res.getKey());
                if(i != t1.numOfArgs - 1) {
                    unificator += ",";
                }
            }
            unificator += ")";
        }
        return new Pair<>(sub, unificator);
    }


    public static void unification(Data trs) {
        Pair<ArrayList<String>, String> res = unificate(trs.firstTerm, trs.secondTerm);
        System.out.println("Substitutions: ");
        for(int i = 0; i < res.getKey().size(); ++i) {
            System.out.println(res.getKey().get(i));
        }
        System.out.println("Unifier: " + res.getValue());
    }

    public static void main(String[]args) throws FileNotFoundException{
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter path to test: ");
            String pathToTest = in.nextLine();
            Scanner inputData = new Scanner(new FileReader(pathToTest));
            ArrayList<String> data = new ArrayList<>(0);
            while (inputData.hasNext()) {
                data.add(inputData.nextLine().replaceAll(" ", ""));
            }
            Data trs = new Data(data);
            unification(trs);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
