package ru.evistix28;

import com.sun.security.jgss.InquireType;
import javafx.util.Pair;

import java.util.ArrayList;

public class Data {
    ArrayList<Pair<Character, Integer>> constructors;
    ArrayList<Character> variables;
    Term firstTerm;
    Term secondTerm;

    private void parseConstructors(String str) {
        int itr = 0;
        while(str.charAt(itr) != '=') {
            itr++;
        }
        itr++;
        char nameOfF;
        int numOfArg;
        for(; itr < str.length(); ++itr) {
            if(str.charAt(itr) == '\n') {
                continue;
            }
            nameOfF = str.charAt(itr);
            itr += 2;
            int start = itr;
            while(str.charAt(itr) != ')') {
                itr++;
            }
            numOfArg = Integer.parseInt(str.substring(start, itr));
            constructors.add(new Pair<>(nameOfF, numOfArg));
            itr++;
        }
    }

    private void parseVariables(String str) {
        int itr = 0;
        while(str.charAt(itr) != '=') {
            itr++;
        }
        itr++;
        for(; itr < str.length(); ++itr) {
            if(str.charAt(itr) == '\n') {
                continue;
            }

            variables.add(str.charAt(itr));
            itr++;
        }
    }


    public Data(ArrayList<String> inputData) {
        constructors = new ArrayList<>(0);
        variables = new ArrayList<>(0);
        parseConstructors(inputData.get(0));
        parseVariables(inputData.get(1));
        firstTerm = new Term();
        secondTerm = new Term();
        int itr = 0;
        String first = inputData.get(2).split("=")[1];
        String second = inputData.get(3).split("=")[1];
        firstTerm = firstTerm.parseTerm(first, 0).getKey();
        secondTerm = secondTerm.parseTerm(second, 0).getKey();

    }

    public class Term {
        int numOfArgs;
        char type;
        char name;
        ArrayList<Term> args = new ArrayList<>(0);

        @Override
        public String toString() {
            String res = "" + name;
            if (type == 's') {
                res += "(";
                for (int i = 0; i < args.size(); ++i) {
                    res += args.get(i).toString();
                    if (i != args.size() - 1) {
                        res += ",";
                    }
                }
                res += ")";
            }
            return res;
        }

        private Pair<Term, Integer> parseTerm(String term, int itr) {
            Term t = new Term();
            t.name = term.charAt(itr);
            if (variables.contains(t.name)) {
                t.type = 'v';
                t.numOfArgs = 0;
                return new Pair<>(t, itr);
            } else {
                for (int i = 0; i < constructors.size(); ++i) {
                    if (t.name == constructors.get(i).getKey()) {
                        if (constructors.get(i).getValue() == 0) {
                            t.type = 'c';
                            t.numOfArgs = 0;
                            return new Pair<>(t, itr);
                        } else {
                            t.numOfArgs = constructors.get(i).getValue();
                            t.type = 's';
                            break;
                        }
                    }
                }
            }

            for (int i = itr + 1; i < term.length(); ++i) {
                if (term.charAt(i) == '(') {
                    continue;
                }
                if (term.charAt(i) == ')') {
                    if (t.args.size() != t.numOfArgs) {
                        System.out.println("Wrong input data! Num Of arguments in constructors not valid");
                        System.exit(0);
                    }
                    return new Pair<>(t, i);
                }
                if (term.charAt(i) == ',') {
                    continue;
                }
                Pair<Term, Integer> arg = parseTerm(term, i);
                t.args.add(arg.getKey());
                i = arg.getValue();
            }
            return new Pair<>(t, term.length());
        }

    }
}
