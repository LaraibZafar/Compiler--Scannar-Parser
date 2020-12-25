import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Stack;
import java.util.ArrayList;
import static java.lang.System.exit;

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
        System.out.println("\nTransition Table & Grammar Rules Specified\n\n");
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
    ArrayList<String> poppedTokens =new ArrayList<String>();
    ArrayList<tokenNode> poppedTokens2 =new ArrayList<tokenNode>();
    HashMap<String, String> TYPETOSTRING;
    public int LrParser(HashMap<String, String> TYPETOSTRING){
        Stack<String> stack= new Stack<>();
        Stack<tokenNode> stack2= new Stack<>();
        this.TYPETOSTRING = TYPETOSTRING;
        tokenArray= compiilerScanner.main(random);
        //printTokenArray(tokenArray);
        int tokenCount=0;
        String operation;
        //String token = getToken(tokenCount++);
        tokenNode token2 = new tokenNode(getToken2(tokenCount++));
//        if(token.equals("\0")){
//            return -1;
//        }
        if(token2.scannerToken ==null){
            return -1;
        }
//        stack.push("1");
        stack2.push(new tokenNode(1));
        while(true){
//            int currentState= Integer.parseInt(stack.peek());
                int currentState2 = stack2.peek().stateNumber;
            //currentState--;
//            int tokenColumn = columnOf(transitionTable[0],""+token);
            int tokenColumn2 = columnOf(transitionTable[0],""+token2.name);
//            if(transitionTable[currentState][tokenColumn]==null){
//                System.out.println("Error at : " + token);
//                exit(0);
//            }
            if(transitionTable[currentState2][tokenColumn2].charAt(0)=='s'){          //shift
                operation = transitionTable[currentState2][tokenColumn2];
//                stack.push(""+token);
                stack2.push(token2);
                if(operation.length()>2){
//                    stack.push(""+operation.charAt(1)+""+operation.charAt(2));
                    stack2.push(new tokenNode(Integer.parseInt(""+operation.charAt(1)+""+operation.charAt(2))));
                }
                else {
//                    stack.push("" + operation.charAt(1));
                    stack2.push(new tokenNode(Integer.parseInt("" + operation.charAt(1))));
                }
//                token = getToken(tokenCount++);
                token2 = new tokenNode(getToken2(tokenCount++));
//                if(token.equals("\0")){
//                    return -1;
//                }
                if(token2.scannerToken ==null){
                    return -1;
                }
                //System.out.println(stack.toString());
            }
            else if(transitionTable[currentState2][tokenColumn2].charAt(0)=='r'){
                operation = transitionTable[currentState2][tokenColumn2];
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
                    if(i%2==0) {      //Don't want the index numbers
//                        stack.pop();
                        stack2.pop();
                    }
                    else{
//                        poppedTokens.add(stack.pop());
                        poppedTokens2.add(stack2.pop());
                    }
                }
                printPoppedTokens();
//                currentState = Integer.parseInt(stack.peek());
                currentState2 = stack2.peek().stateNumber;
                tokenColumn2 = columnOf(transitionTable[0],LHS);
                stack2.push(new tokenNode(LHS));
                //System.out.println(currentState);
                //System.out.println(tokenColumn);
                if(transitionTable[currentState2][tokenColumn2].length()>2) {
                    stack2.push(new tokenNode(Integer.parseInt(""+transitionTable[currentState2][tokenColumn2].charAt(1)+""+transitionTable[currentState2][tokenColumn2].charAt(2))));     //Transition on State when char = reduction char
                }
                else{
                    //System.out.println(token);
                    stack2.push(new tokenNode(Integer.parseInt(""+transitionTable[currentState2][tokenColumn2].charAt(1))));     //Transition on State when char = reduction char
                }
            }
            else if(transitionTable[currentState2][tokenColumn2].equals("acc")) {
                printTokenArray(tokenArray);
                return 1;
            }
            else{       //Token not found
                printTokenArray(tokenArray);
                return -1;
            }
        }
    }

    public Node initializeNode(ArrayList<tokenNode> poppedTokens, String LHS, Stack<tokenNode> stack2){
        if(LHS.equals("P")){
            ProgramNode PNode = new ProgramNode((StatementNode) poppedTokens.get(0).node);
            stack2.push(new tokenNode(PNode,LHS));
        }
        else if(LHS.equals("S")){
            StatementNode SNode=null;
            if(poppedTokens.size()<2) {
                if(poppedTokens.get(0).name.equals("S"))
                    SNode = new StatementNode((StatementNode) poppedTokens.get(0).node);
                else if (poppedTokens.get(0).name.equals("A"))
                    SNode = new StatementNode((AssignStatementNode) poppedTokens.get(0).node);
            }
            else{
                SNode = new StatementNode((StatementNode) poppedTokens.get(0).node,(StatementNode) poppedTokens.get(1).node);
            }
            stack2.push(new tokenNode(SNode,LHS));

        }
        else if(LHS.equals("A")){}
        else if(LHS.equals("X")){}
        else if(LHS.equals("L")){}
        else if(LHS.equals("W")){}
        else if(LHS.equals("Y")){}
        else if(LHS.equals("C")){}
        else if(LHS.equals("E")){}
        else if(LHS.equals("O")){}
        else if(LHS.equals("T")){}
        else if(LHS.equals("U")){}
        else if(LHS.equals("F")){}
        else if(LHS.equals("I")){}
        else if(LHS.equals("N")){}



        return null;
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
            String actionTableValue = TYPETOSTRING.get(tokenArray[tokenCount].tokenType.ordinal() + "");
            tokenArray[tokenCount].actionTableValue = actionTableValue;
            return actionTableValue;

        } else {
            return "\0";
        }
    }
    public token getToken2(int tokenCount) {
        if (tokenCount < tokenArray.length) {
            //System.out.println(TYPETOSTRING.get(tokenArray[tokenCount].tokenType.ordinal() + ""));
            String actionTableValue = TYPETOSTRING.get(tokenArray[tokenCount].tokenType.ordinal() + "");
            tokenArray[tokenCount].actionTableValue = actionTableValue;
            return tokenArray[tokenCount];

        } else {
            return null;
        }
    }

    public void printTokenArray(token[] tokenArray){
        System.out.println("\n\nSCANNER => TOKEN ARRAY:");
        for(token currentToken : tokenArray){
            if(currentToken != null)
                System.out.println(currentToken.tokenType + " : "+currentToken.identifierName+ " : "+currentToken.actionTableValue);
        }
        System.out.println("");
    }
    public void printPoppedTokens(){
        System.out.println("\n Popped Tokens Array");
        for(String currentToken : poppedTokens){
            System.out.println(currentToken);
        }
        poppedTokens.clear();
        System.out.println("");
    }
}

class tokenNode{
    token scannerToken;
    int stateNumber;
    Node node;
    String type;
    String name;

    public tokenNode(token scannerToken){
        this.scannerToken = scannerToken;
        name = scannerToken.actionTableValue;
        type = "token";
    }
    public tokenNode(int stateNumber){
        this.stateNumber = stateNumber;
        type = "token";
    }
    public tokenNode(Node node, String name){
        this.node = node;
        type = "int";
        this.name = name; //left side of production
    }


}


