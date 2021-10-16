package ru.evistix28;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static boolean checkConfluence(String s1, String s2) {
        int lengthPrefix = 1;
        int length = (s1.length() < s2.length())? s1.length(): s2.length();
        while(lengthPrefix <= length) {
            String prefix = s1.substring(0, lengthPrefix);
            String suffix = s2.substring(s2.length() - lengthPrefix, s2.length());
            if(prefix.equals(suffix)) {
                return false;
            }
            prefix = s2.substring(0, lengthPrefix);
            suffix = s1.substring(s1.length() - lengthPrefix, s1.length());
            if(prefix.equals(suffix)) {
                return false;
            }
            lengthPrefix++;
        }
        return true;
    }
    public static boolean checkConfluence(String str) {
        int lengthPrefix = 1;
        while(lengthPrefix <= str.length() / 2) {
            String prefix = str.substring(0, lengthPrefix);
            String suffix = str.substring(str.length() - lengthPrefix, str.length());
            if(prefix.equals(suffix)) {
                return false;
            }
            lengthPrefix++;
        }
        return true;
    }


    public static void areConfluence(Data s) {
        for (int i = 0; i < s.rewritingRules.size(); ++i) {
            for (int j = i; j < s.rewritingRules.size(); ++j) {
                if (i == j) {
                    if (!checkConfluence(s.rewritingRules.get(i).getKey())) {
                        System.out.println(s.rewritingRules.get(i).getKey() + " -> " + s.rewritingRules.get(i).getValue());
                        System.out.println("System are not confluence!");
                        System.exit(0);
                    }
                } else {
                    if (!checkConfluence(s.rewritingRules.get(i).getKey(), s.rewritingRules.get(j).getKey())) {
                        System.out.println(s.rewritingRules.get(i).getKey() + " -> " + s.rewritingRules.get(i).getValue());
                        System.out.println(s.rewritingRules.get(j).getKey() + " -> " + s.rewritingRules.get(j).getValue());
                        System.out.println("System are not confluence!");
                        System.exit(0);
                    }
                }
            }
        }
        System.out.println("System are confluence!!!");
    }



    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter path to test: ");
        String pathToTest = in.nextLine();
        Scanner inputData = new Scanner(new FileReader(pathToTest));
        ArrayList<String> rules = new ArrayList<>(0);
        while(inputData.hasNext()) {
            rules.add(inputData.nextLine());
        }
        Data s = new Data(rules);
        areConfluence(s);
    }
}
