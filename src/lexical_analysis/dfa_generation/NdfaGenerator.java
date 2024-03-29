package lexical_analysis.dfa_generation;

import lexical_analysis.Token;

import java.util.*;

/**
 * Takes a regular expression and generates a NDFA
 */
public class NdfaGenerator {
    private HashMap<String, String> atomicLexicalElementMap;

    public NdfaGenerator() {
        this.atomicLexicalElementMap = new HashMap<String, String>();

        for (Token token : Token.values()) {
            if (token.isExpand())
                atomicLexicalElementMap.put(token.getName(), token.getRegex());
        }
    }

    public State generate() {

        // generate an DfaGeneration.Ndfa for every token
        ArrayList<State> generatedTokenEntryStates = new ArrayList<State>();
        for (Token token : Token.values()) {
            if (!token.isStandalone())
                continue;

            Ndfa newNdfa = generateNdfaFromElement(token.getName(), token);
            generatedTokenEntryStates.add(newNdfa.getEntry());
        }

        // connect them and return the DfaGeneration.Ndfa
        State start = new State();
        for (State entryState : generatedTokenEntryStates)
            start.addEdge("ε", entryState);
        return start;
    }

    private void addElementRegularExpression(String element, String regularExpression) {
        atomicLexicalElementMap.put(element, regularExpression);
    }

    private Ndfa generateNdfaFromElement(String element, Token branchToken) {
        // checking if we can split up the element
        String[] elementSplit = splitIgnoreBrackets(element);

        if (elementSplit.length == 1) {
            // There is only a single element
            if (element.length() != 1 && element.charAt(element.length() - 1) == '*')  {
                // recursive thingy
                State start = new State(branchToken);
                State end = new State(branchToken);
                start.addEdge("ε", end);

                Ndfa repeatedPart = generateNdfaFromElement(element.substring(0, element.length() - 1), branchToken);
                repeatedPart.getExit().addEdge("ε", repeatedPart.getEntry());

                start.addEdge("ε", repeatedPart.getEntry());
                repeatedPart.getExit().addEdge("ε", end);

                return new Ndfa(start, end);
            } else if (element.length() != 1 && element.charAt(element.length() - 1) == '?') {
                // the element is optional so do the recursion and then draw a ε edge from entry to exit
                Ndfa optionalPart = generateNdfaFromElement(element.substring(0, element.length() - 1), branchToken);
                optionalPart.getEntry().addEdge("ε", optionalPart.getExit());
                return optionalPart;
            } else if (element.charAt(0) == '[' && element.charAt(element.length() - 1) == ']') {
                // element is in square brackets. The point of this is to isolate recursions.
                return generateNdfaFromElement(element.substring(1, element.length() - 1), branchToken);
            } else if (atomicLexicalElementMap.containsKey(element)) {
                // Can expand element from map
                return generateNdfaFromElement(atomicLexicalElementMap.get(element), branchToken);
            } else {
                // element cannot be expanded
                State start = new State(branchToken);
                State end = new State(branchToken);
                start.addEdge(element, end);
                return new Ndfa(start, end);
            }
        }

        // construct a combination of elements
        State start = new State(branchToken);
        State branchingPoint = start;
        State cur = start;
        ArrayList<State> tails = new ArrayList<State>();
        for (int i = 0 ; i < elementSplit.length ; i++) {
            Ndfa pathNdfa = generateNdfaFromElement(elementSplit[i], branchToken);
            if ((i < elementSplit.length - 1 && elementSplit[i+1].equals("|")) || (i > 0 && elementSplit[i-1].equals("|"))) {
                // create new branch from start
                if (i < elementSplit.length - 1 && elementSplit[i+1].equals("|"))
                    i++;

                if (cur != start)
                    tails.add(cur);

                branchingPoint.addEdge("ε", pathNdfa.getEntry());
                cur = pathNdfa.getExit();
            } else {
                if (cur == start)
                    branchingPoint = pathNdfa.getExit();

                // continue along whatever branch you're at (or the start)
                cur.addEdge("ε", pathNdfa.getEntry());
                cur = pathNdfa.getExit();
            }
        }

        State end = new State(branchToken);
        cur.addEdge("ε", end);
        for (State tail : tails)
            tail.addEdge("ε", end);

        return new Ndfa(start, end);
    }

    private String[] splitIgnoreBrackets(String s) {
        int left = 0;

        ArrayList<String> ret = new ArrayList<String>();

        int howManyEndBracketsAmIHunting  = 0;
        for (int right = 0 ; right < s.length() ; right++) {
            if (s.charAt(right) == '[') {
                howManyEndBracketsAmIHunting++;
            }  else if (s.charAt(right) == ']') {
                howManyEndBracketsAmIHunting--;
            } else if (howManyEndBracketsAmIHunting > 0) {
                // sigma -> do nothing
            } else if (s.charAt(right) == ' ') {
                ret.add(s.substring(left, right));
                left = right+1;
            }
        }

        ret.add(s.substring(left, s.length()));

        String[] retArr = new String[ret.size()];
        ret.toArray(retArr);
        return retArr;
    }
}
