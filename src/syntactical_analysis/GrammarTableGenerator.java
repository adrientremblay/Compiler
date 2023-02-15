package syntactical_analysis;

import lexical_analysis.Token;
import util.Util;

import java.util.*;

// todo: right know the grammar table is generated dynamically, implement saving and loading later
public class GrammarTableGenerator {
    HashMap<String, HashMap<String, String>> grammarTable;
    HashMap<String, HashSet<String[]>> rules;
    HashMap<String, HashSet<String>> firstSets;
    HashMap<String, HashSet<String>> followSets;

    public HashMap<String, HashMap<Token, String>> generateGrammarTable() {
        grammarTable = new HashMap<String, HashMap<String, String>>();

        // Read the grammar file
        List<String> lines = Util.readFileForLines("grammar/grammar.grm");

        // Reading rules
        rules = new HashMap<String, HashSet<String[]>>();

        for (String line : lines) {
            if (line.length() == 0)
                continue;

            String[] stringSplit = line.split(" ");

            if (stringSplit.length < 3 || stringSplit[0].length() == 0 || (!stringSplit[1].equals("->"))) {
                System.err.println("Invalid rule : " + line);
                continue;
            }

            String leftHandSide = stringSplit[0];
            String[] rightHandSide = Arrays.<String>copyOfRange(stringSplit, 2, stringSplit.length);

            if (!rules.containsKey(leftHandSide))
                rules.put(leftHandSide, new HashSet<String[]>());

            rules.get(leftHandSide).add(rightHandSide);
        }

        // Generating first sets
        firstSets = new HashMap<String, HashSet<String>>();
        for (String rule : rules.keySet())
            generateFirstSet(rule);

        // Generating follow sets (important to do this after the first sets are generated cause they are used)
        followSets = new HashMap<String, HashSet<String>>();
        for (String rule : rules.keySet())
            generateFollowSet(rule);

        // Generating the table
        for (String line : lines) {
            if (line.length() == 0)
                continue;

            String[] stringSplit = line.split(" ");

            if (stringSplit.length < 3 || stringSplit[0].length() == 0 || (!stringSplit[1].equals("->"))) {
                System.err.println("Invalid rule : " + line);
                continue;
            }

            String leftHandSide = stringSplit[0];
            String[] rightHandSide = Arrays.<String>copyOfRange(stringSplit, 2, stringSplit.length);

            if (! grammarTable.containsKey(leftHandSide))
                grammarTable.put(leftHandSide, new HashMap<String, String>());

            if (rightHandSide.length == 1 && rightHandSide[0] == "EPSILON") {
                Set<String> lineFollowSet = followSets.get(leftHandSide);

                for (String terminal : lineFollowSet)
                    grammarTable.get(leftHandSide).put(terminal, line);
            } else {
                Set<String> lineFirstSet = firstSets.get(leftHandSide);

                for (String terminal : lineFirstSet)
                    grammarTable.get(leftHandSide).put(terminal, line);
            }
        }

        return null;
    };

    private void generateFirstSet(String rule) {
        if (firstSets.containsKey(rule))
            return; // no need to redo work, first set for this rule already generated

        firstSets.put(rule, new HashSet<String>());

        for (String[] rhs : rules.get(rule)) {
            if (isTerminal(rhs[0])) {
                firstSets.get(rule).add(rhs[0].substring(1, rhs[0].length() - 1));
            } else if (!rhs[0].equals("EPSILON")) {
                generateFirstSet(rhs[0]);
                firstSets.get(rule).addAll(firstSets.get(rhs[0]));

                // might need to do the next one
                for (int i = 0; i < rhs.length - 1; i++) {
                    if ((!isTerminal(rhs[i])) && rules.get(rhs[i]).contains("EPSILON")) {
                        generateFirstSet(rhs[i + 1]);
                        firstSets.get(rule).addAll(firstSets.get(rhs[i + 1]));
                    }
                }

            }
        }
    }

    private void generateFollowSet(String rule) {
        if (followSets.containsKey(rule))
            return; // no need to redo work, first set for this rule already generated

        followSets.put(rule, new HashSet<String>());

        if (rule.equals("START")) {
            followSets.get(rule).add("$");
            return;
        }

        // scan all other rules to try and add to follow set
        for (String foundRule : rules.keySet()) {
            for (String[] rhs : rules.get(foundRule)) {
                for (int i = 0 ; i < rhs.length ; i++) {
                   if (rhs[i].equals(rule)) {
                       if (i < rhs.length - 1) {
                           if (isTerminal(rhs[i+1]) && (!rhs[i+1].equals("EPSILON"))) {
                              followSets.get(rule).add(rhs[i+1]);
                           } else {
                               generateFollowSet(rhs[i+1]);
                               followSets.get(rule).addAll(followSets.get(rhs[i+1]));

                               if (firstSets.get(rhs[i+1]).contains("EPSILON")) {
                                   if (i < rhs.length - 2) {
                                       generateFollowSet(rhs[i+2]);
                                       followSets.get(rule).addAll(followSets.get(rhs[i+2]));
                                   } else {
                                       generateFollowSet(foundRule);
                                       followSets.get(rule).addAll(followSets.get(foundRule));
                                   }
                               }
                           }
                       } else {
                           generateFollowSet(foundRule);
                           followSets.get(rule).addAll(followSets.get(foundRule));
                       }
                   }
                }
            }
        }
    }

    private static boolean isTerminal(String s) {
        if (s.length() <= 2)
            return false;

        return (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'');
    }
}
