import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class compiilerScanner {
    public static token[] tokenArray = new token[100];
    public static token[] main(String[] args) {
        tokenizer tokenize = new tokenizer();
        tokenArray=tokenize.fileParser();
        return tokenArray;
    }
}

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
    EOF
}

class tokenizer{
    public  BufferedReader bufferReader;
    public token[] tokenArray = new token[100];

    public tokenizer(){
        try {
            File inputFile=new File("C:\\Users\\Laraib Zafar\\IdeaProjects\\Compile-Scanner-Parser\\src\\input.txt");
            FileReader fileReader = new FileReader(inputFile);
            bufferReader=new BufferedReader(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public token[] fileParser(){
        try {
            int index=0;
            int input = 0;
            while((input =bufferReader.read()) != -1)
            {
                char thisCharacter = (char) input;
                token thisToken = tokenizer(thisCharacter);
                if(thisToken!=null){
                    //System.out.println(thisToken.tokenType+ " "+thisToken.identifierName+ " "+thisToken.intValue+" "+(thisToken.tokenType.ordinal()));
                    tokenArray[index]=thisToken;
                    index++;
            }
            }
            token thisToken=new token(tokenType.EOF);
            thisToken.identifierName="$";
            tokenArray[index]=thisToken;
            index++;
            System.out.println(thisToken.tokenType+ " "+thisToken.identifierName+ " "+thisToken.intValue);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenArray;
    }
    public token tokenizer(char thisCharacter){
        try {
            if (Character.isLetter(thisCharacter)) { //Identifier
                String identifierName = "" + thisCharacter;
                int input;
                while ((input = bufferReader.read()) != -1) {
                    thisCharacter = (char) input;
                    if (thisCharacter == ' ') {
                        if(identifierName.equalsIgnoreCase("if")){
                            token newToken = new token(tokenType.IF);
                            return newToken;
                        }
                        else if(identifierName.equalsIgnoreCase("while")){
                            token newToken = new token(tokenType.WHILE);

                            return newToken;
                        }
                        else {
                            token newToken = new token(tokenType.ID, identifierName);
                            return newToken;
                        }
                    } else if (Character.isLetterOrDigit(thisCharacter) || thisCharacter == '_' )
                        identifierName += thisCharacter;
                    else {
                        token newToken = new token(tokenType.INVALID);
                        return newToken;
                    }
                }
                if(identifierName.equalsIgnoreCase("if")){
                    token newToken = new token(tokenType.IF);
                    return newToken;
                }
                else {
                    token newToken = new token(tokenType.ID, identifierName);
                    return newToken;
                }
            }
            else if(Character.isDigit(thisCharacter)){ //Integer
                String integerString=""+thisCharacter;
                int input;
                while ((input = bufferReader.read()) != -1) {
                    thisCharacter = (char) input;
                    if (thisCharacter == ' ') {
                        int integerValue = Integer.parseInt(integerString);
                        token newToken = new token(tokenType.INT, integerValue);
                        return newToken;
                    } else if (Character.isDigit(thisCharacter))
                        integerString += thisCharacter;
                    else {
                        token newToken = new token(tokenType.INVALID);
                        return newToken;
                    }
                }
                int integerValue = Integer.parseInt(integerString);
                token newToken = new token(tokenType.INT, integerValue);
                return newToken;
            }
            else if (thisCharacter=='('){
                token newToken = new token(tokenType.L_PAREN);
                return newToken;
            }
            else if (thisCharacter==')'){
                token newToken = new token(tokenType.R_PAREN);
                return newToken;
            }
            else if (thisCharacter==';'){
                token newToken = new token(tokenType.SCOLON);
                return newToken;
            }
            else if (thisCharacter=='='){
                int input=bufferReader.read();
                if(input==-1 || (char)input!='='){
                    token newToken=new token(tokenType.EQ);
                    return newToken;
                }
                else{
                    token newToken=new token(tokenType.EQEQ);
                    return newToken;
                }
            }
            else if(thisCharacter=='!'){
                int input=bufferReader.read();
                if(input==-1 || (char)input!='='){
                    token newToken=new token(tokenType.NOT);
                    return newToken;
                }
                else{
                    token newToken=new token(tokenType.NEQ);
                    return newToken;
                }
            }
            else if(thisCharacter=='<'){
                int input=bufferReader.read();
                if(input==-1 || (char)input!='='){
                    token newToken=new token(tokenType.LESS);
                    return newToken;
                }
                else{
                    token newToken=new token(tokenType.LEQ);
                    return newToken;
                }
            }
            else if(thisCharacter=='>'){
                int input=bufferReader.read();
                if(input==-1 || (char)input!='='){
                    token newToken=new token(tokenType.GREATER);
                    return newToken;
                }
                else{
                    token newToken=new token(tokenType.GEQ);
                    return newToken;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

class token{
    tokenType tokenType;
    String identifierName;
    int intValue;
    public token(tokenType tokenType, String identifierName){
        this.tokenType=tokenType;
        this.identifierName=identifierName;
    }
    public token(tokenType tokenType, int integerValue){
        this.tokenType=tokenType;
        this.intValue=integerValue;
    }
    public token(tokenType tokenType){
        this.tokenType=tokenType;
    }
}