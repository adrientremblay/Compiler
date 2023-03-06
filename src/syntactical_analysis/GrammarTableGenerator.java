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
    HashSet<String> followSetKeys;
    HashSet<String> canBeEpsilon;

    public HashMap<String, HashMap<String, String>> generateGrammarTable() {
        grammarTable = new HashMap<String, HashMap<String, String>>();

        // Read the grammar file
        List<String> lines = Util.readFileForLines("grammar/grammar.grm");

        // Reading rules to populate rules map and canBeEpsilon set
        rules = new HashMap<String, HashSet<String[]>>();
        canBeEpsilon = new HashSet<String>();

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

            if (rightHandSide.length == 1 && rightHandSide[0].equals("EPSILON"))
                canBeEpsilon.add(leftHandSide);

            rules.get(leftHandSide).add(rightHandSide);
        }

        // Generating first sets
        firstSets = new HashMap<String, HashSet<String>>();
        for (String rule : rules.keySet())
            generateFirstSet(rule);

        // Generating follow sets (important to do this after the first sets are generated cause they are used)
        followSets = new HashMap<String, HashSet<String>>();
        followSetKeys = new HashSet<String>();
        for (String rule : rules.keySet())
            generateFollowSet(rule);

        // Generate the follow sets for a second time... Lord have mercy on my soul...
        // This is a dumb solution
        followSetKeys = new HashSet<String>();
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

            if (rightHandSide.length == 1 && rightHandSide[0].equals("EPSILON") // the current rule can be EPSILON directly
            || canBeEpsilon.contains(lastNonSemanticItem(rightHandSide))) { // the last symbol of the rhs of the rule can be epsilon, so we take it's follow set
                Set<String> lineFollowSet = followSets.get(leftHandSide);

                for (String terminal : lineFollowSet)
                    grammarTable.get(leftHandSide).put(terminal, line);
            }
        }

        // printFirstAndFollowSets();

        return grammarTable;
    };

    private void generateFirstSet(String rule) {
        if (firstSets.containsKey(rule))
            return; // no need to redo work, first set for this rule already generated

        firstSets.put(rule, new HashSet<String>());

        if (! grammarTable.containsKey(rule))
            grammarTable.put(rule, new HashMap<String, String>());

        for (String[] rhs : rules.get(rule)) {
            for (int i = 0 ; i < rhs.length ; i++) {
                if (isSemanticAction(rhs[i])) {
                    continue;
                } if (isTerminal(rhs[i])) {
                    String terminal = rhs[i].substring(1, rhs[i].length() - 1);
                    firstSets.get(rule).add(terminal);
                    grammarTable.get(rule).put(terminal, rule + " -> " + String.join(" ", rhs));
                } else if (!rhs[i].equals("EPSILON")) {
                    generateFirstSet(rhs[i]);
                    firstSets.get(rule).addAll(firstSets.get(rhs[i]));

                    for (String terminal : firstSets.get(rhs[i])) {
                        grammarTable.get(rule).put(terminal, rule + " -> " + String.join(" ", rhs));
                    }
                }

                if (!canBeEpsilon.contains(rhs[i]))
                    break;
            }
        }
    }

    private void generateFollowSet(String rule) {
        if (followSetKeys.contains(rule))
            return; // no need to redo work, first set for this rule already generated

        if (!followSets.containsKey(rule))
            followSets.put(rule, new HashSet<String>());
        followSetKeys.add(rule);

        if (rule.equals("START")) {
            followSets.get(rule).add("$");
            return;
        }

        // scan all other rules to try and add to follow set
        for (String foundRule : rules.keySet()) {
            for (String[] rhs : rules.get(foundRule)) {
                for (int i = 0 ; i < rhs.length ; i++) {
                   if (!rhs[i].equals(rule))
                       continue;

                   int nextNonSemanticIndex = findNextNonSemanticIndex(i, rhs);

                   if (nextNonSemanticIndex == -1) {
                       generateFollowSet(foundRule);
                       followSets.get(rule).addAll(followSets.get(foundRule));
                       break;
                   }

                   if (isTerminal(rhs[nextNonSemanticIndex])) {
                      followSets.get(rule).add(rhs[nextNonSemanticIndex].substring(1, rhs[nextNonSemanticIndex].length() - 1));
                      continue;
                   }

                   followSets.get(rule).addAll(firstSets.get(rhs[nextNonSemanticIndex]));

                   if (canBeEpsilon.contains(rhs[nextNonSemanticIndex])) {
                       int nextNextNonSemanticIndex = findNextNonSemanticIndex(nextNonSemanticIndex, rhs) ;

                       if (nextNextNonSemanticIndex == -1) {
                           generateFollowSet(foundRule);
                           followSets.get(rule).addAll(followSets.get(foundRule));
                       } else {
                           if (isTerminal(rhs[nextNextNonSemanticIndex])) {
                               followSets.get(rule).add(rhs[nextNextNonSemanticIndex].substring(1, rhs[nextNextNonSemanticIndex].length() - 1));
                           } else {
                               followSets.get(rule).addAll(firstSets.get(rhs[nextNextNonSemanticIndex]));
                           }
                       }
                   }
                }
            }
        }
    }

    public static boolean isTerminal(String s) {
        if (s.length() <= 2)
            return false;

        return (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'');
    }

    public static boolean isSemanticAction(String s) {
        if (s.length() < 1)
            return false;

        return (s.charAt(0) == '!');
    }

    private static String lastNonSemanticItem(String[] rhs) {
        for (int i = rhs.length -1 ; i >= 0 ; i--) {
            if (!isSemanticAction(rhs[i]))
                return rhs[i];
        }

        // This is not supposed to be reached
        return null;
    }

    /**
     * Finds the index of the next non-semantic item AFTER the given index or -1 if it can't if one
     * @param index
     * @param items
     * @return
     */
    private static int findNextNonSemanticIndex(int index, String[] items) {
        for (int i = index+1 ; i < items.length ; i++)
            if (!isSemanticAction(items[i]))
                return i;

        return -1;
    }

    public HashMap<String, HashSet<String>> getFirstSets() {
        return firstSets;
    }

    public HashMap<String, HashSet<String>> getFollowSets() {
        return followSets;
    }

    public void printFirstAndFollowSets() {
        System.out.println("FIRST SETS:");
        System.out.println();
        for (String key : firstSets.keySet()) {
            System.out.print(key + ": ");
            for (String terminal : firstSets.get(key)) {
                System.out.print(terminal + ", ");
            }
            System.out.println();
        }

        System.out.println("FOLLOW SETS:");
        System.out.println();
        for (String key : followSets.keySet()) {
            System.out.print(key + ": ");
            for (String terminal : followSets.get(key)) {
                System.out.print("'" + terminal + "'" +  " ");
            }
            System.out.println();
        }
    }
}
