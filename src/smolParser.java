import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.System.exit;
import static java.lang.System.in;

import java.util.HashMap;


public class smolParser {
    enum tokenType {
        R_PAREN,
        L_PAREN,
        SCOLON,
        NEQ,
        GEQ,
        NOT,
        GREATER,
        LESS,
        LEQ,
        INT,
        ID,
        INVALID,
        IF,
        WHILE,
        EQ,
        EQEQ,
        PLUS,
        MINUS,
        DIV,
        MUL,
        EOF
    }

    public static void main(String[] args) {
        HashMap<String, String> TYPETOSTRING = new HashMap<String, String>();
        TYPETOSTRING.put(tokenType.R_PAREN.ordinal()+"",")");
        TYPETOSTRING.put(tokenType.L_PAREN.ordinal()+"","(");
        TYPETOSTRING.put(tokenType.SCOLON.ordinal()+"","$");
        TYPETOSTRING.put(tokenType.NEQ.ordinal()+"","!=");
        TYPETOSTRING.put(tokenType.GEQ.ordinal()+"",">=");
        TYPETOSTRING.put(tokenType.NOT.ordinal()+"","!");
        TYPETOSTRING.put(tokenType.GREATER.ordinal()+"",">");
        TYPETOSTRING.put(tokenType.LESS.ordinal()+"","<");
        TYPETOSTRING.put(tokenType.LEQ.ordinal()+"","<=");
        TYPETOSTRING.put(tokenType.INT.ordinal()+"","1");
        TYPETOSTRING.put(tokenType.ID.ordinal()+"","a");
        TYPETOSTRING.put(tokenType.INVALID.ordinal()+"","Invalid");
        TYPETOSTRING.put(tokenType.IF.ordinal()+"","if");
        TYPETOSTRING.put(tokenType.WHILE.ordinal()+"","while");
        TYPETOSTRING.put(tokenType.EQ.ordinal()+"","=");
        TYPETOSTRING.put(tokenType.EQEQ.ordinal()+"","=");
        TYPETOSTRING.put(tokenType.PLUS.ordinal()+"","+");
        TYPETOSTRING.put(tokenType.MINUS.ordinal()+"","-");
        TYPETOSTRING.put(tokenType.DIV.ordinal()+"","/");
        TYPETOSTRING.put(tokenType.MUL.ordinal()+"","*");
        TYPETOSTRING.put(tokenType.EOF.ordinal()+"","$");

        parser CompilerParser=new parser();
        String[][] transitionTable=CompilerParser.populateTransitionTable();
        CompilerParser.printTransitionTable();
        CompilerParser.printGrammarRules();
        System.out.println("\nTransition Table & Grammar Rules Specified\n\nEnter an input String : ");
            //String inputString = input.nextLine();
            int returnValue = CompilerParser.LrParser(TYPETOSTRING);
            if(returnValue==1){
                System.out.println("Valid String");
            }
            else{
                System.out.println("Invalid String");
            }
        }
}
class parser{
    String[][] transitionTable;
    String[][] grammarRules= new String[32][2];
    String[] random;
    token[] tokenArray;
    HashMap<String, String> TYPETOSTRING;
    public int LrParser(HashMap<String, String> TYPETOSTRING){
        Stack<String> stack= new Stack<>();
        this.TYPETOSTRING = TYPETOSTRING;
        tokenArray= compiilerScanner.main(random);
        int tokenCount=0;
        String operation;
        String token = getToken(tokenCount++);
        if(token.equals("\0")){
            return -1;
        }
        stack.push("1");
        while(true){
            //System.out.println(stack.toString());
            int currentState= Integer.parseInt(stack.peek());
            //currentState--;
            int tokenColumn = columnOf(transitionTable[0],""+token);
            if(transitionTable[currentState][tokenColumn]==null){
                System.out.println("Error at : " + token);
                exit(0);
            }
            if(transitionTable[currentState][tokenColumn].charAt(0)=='s'){          //shift
                operation = transitionTable[currentState][tokenColumn];
                stack.push(""+token);
                if(operation.length()>2){
                    stack.push(""+operation.charAt(1)+""+operation.charAt(2));
                }
                else {
                    stack.push("" + operation.charAt(1));
                }
                token = getToken(tokenCount++);
                if(token.equals("\0")){
                    return -1;
                }
                //System.out.println(stack.toString());
            }
            else if(transitionTable[currentState][tokenColumn].charAt(0)=='r'){
                operation = transitionTable[currentState][tokenColumn];
                int grammarRuleNumber;
                if(operation.length()>2) {
                    grammarRuleNumber = Integer.parseInt(""+operation.charAt(1)+""+operation.charAt(2));
                }
                else{
                    grammarRuleNumber = Integer.parseInt(""+operation.charAt(1));
                }
                grammarRuleNumber--;
                String LHS = grammarRules[grammarRuleNumber][0];
                String RHS = grammarRules[grammarRuleNumber][1];
                for (int i = 0; i < 2* RHS.length() ; i++) {                    //Pop the Character & the state
                    stack.pop();
                }
                currentState = Integer.parseInt(stack.peek());
                tokenColumn = columnOf(transitionTable[0],LHS);
                stack.push(LHS);
                System.out.println(currentState);
                System.out.println(tokenColumn);
                if(transitionTable[currentState][tokenColumn].length()>2) {
                    stack.push(""+transitionTable[currentState][tokenColumn].charAt(1)+""+transitionTable[currentState][tokenColumn].charAt(2));     //Transition on State when char = reduction char
                }
                else{
                    //System.out.println(token);
                    stack.push(""+transitionTable[currentState][tokenColumn].charAt(1));     //Transition on State when char = reduction char
                }
            }
            else if(transitionTable[currentState][tokenColumn].equals("acc")) {
                return 1;
            }
            else{       //Token not found
                return -1;
            }
        }
    }

    public String[][] populateTransitionTable(){
        try {
            //open a file
            File file = new File("C:\\Users\\Laraib Zafar\\IdeaProjects\\Compile-Scanner-Parser\\src\\UpdatedGrammar.txt");
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer sb=new StringBuffer();
            String readLine;

            //Symbols
            readLine=br.readLine();
            String[] terminalSymbols = readLine.split(" ");
            readLine=br.readLine();
            String[] nonTerminalSymbols = readLine.split(" ");

            transitionTable = new String[70][terminalSymbols.length+nonTerminalSymbols.length];
            transitionTable = populateSymbols(transitionTable,terminalSymbols, nonTerminalSymbols);

            //State Transition
            br.readLine();
            readLine=br.readLine();
            while(readLine!=null &&readLine.length()>0){
                String[] stateTransition = readLine.split(" ");
                int initialState = Integer.parseInt(stateTransition[0]);
                initialState++;
                String transitionSymbol = stateTransition[1];
                String action = stateTransition[2];
                if(!action.equals("acc")){
                String actionNumber =action.substring(1);
                int actualActionNumber = Integer.parseInt(actionNumber);
                actualActionNumber++;
                    int columnOfSymbol = columnOf(transitionTable[0],transitionSymbol);
                    transitionTable[initialState][columnOfSymbol]=action.charAt(0)+""+actualActionNumber;
                }
               else{
                    int columnOfSymbol = columnOf(transitionTable[0],transitionSymbol);
                    transitionTable[initialState][columnOfSymbol]=action;

                }
                readLine=br.readLine();
            }
            //Grammar Rules
            readLine=br.readLine();
            int i=0;
            while(readLine!=null &&readLine.length()>0){
                String[] Rule = readLine.split(" ");
                 grammarRules[i][0]=Rule[0];
                 grammarRules[i][1]=Rule[1];
                 i++;
                 readLine=br.readLine();
            }
            return transitionTable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String[][] populateSymbols(String[][] transitionTable, String[] terminalSymbols,String[] nonTerminalSymbols){
        int i = 0;
        for (String symbol: terminalSymbols) {
            transitionTable[0][i]=symbol;
            i++;
        }
        for (String symbol: nonTerminalSymbols) {
            transitionTable[0][i]=symbol;
            i++;
        }
        return transitionTable;
    }
    public void printTransitionTable(){
        System.out.println("Transition Table:");
        for (String[] row : transitionTable)
        {
            for (String text : row)
            {
                if(text == null){
                    text ="-";
                }
                    System.out.print(text + "\t");
            }

            System.out.println();
        }
        System.out.println();
    }
    public void printGrammarRules(){
        System.out.println("Grammar Rules :");
        for (String[] row : grammarRules)
        {
            for (String text : row)
            {
                System.out.print(text + "\t=\t");
            }

            System.out.println();
        }
        System.out.println();
    }
    public int columnOf(String[] transitionTable,String transitionSymbol){
        int index=-1;
        for (int i = 0; i < transitionTable.length ; i++) {
            if(transitionTable[i].equals(transitionSymbol)){
                index = i;
                break;
            }
        }
        if(index ==-1){
            System.out.println(transitionSymbol);
            System.out.println("Unknown Symbol encountered in a transition");
            exit(0);
        }
        return index;
    }

    public String getToken(int tokenCount) {
        if (tokenCount < tokenArray.length) {
                //System.out.println(TYPETOSTRING.get(tokenArray[tokenCount].tokenType.ordinal() + ""));
                return TYPETOSTRING.get(tokenArray[tokenCount].tokenType.ordinal() + "");

        } else {
            return "\0";
        }
    }
}



