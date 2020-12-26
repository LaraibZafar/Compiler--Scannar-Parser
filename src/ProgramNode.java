import java.util.ArrayList;

abstract class Node{
    ArrayList<Node> childrenNodes;
}

public class ProgramNode extends Node {
    StatementNode statementNode;
    public ProgramNode(StatementNode statementNode){
        super();
        this.statementNode = statementNode;
        childrenNodes.add(this.statementNode);
    }
    public String toString(){
        return "Program";
    }
}

class StatementNode extends Node{
    StatementNode statementNode1;
    StatementNode statementNode2;
    AssignStatementNode assignStatementNode;
    IfNode IfStatementNode;
    WhileNode WhileStatementNode;

    public StatementNode(StatementNode statementNode1, StatementNode statementNode2){
        super();
        this.statementNode1 = statementNode1;
        this.statementNode2 = statementNode2;
    }
    public StatementNode(StatementNode statementNode1){
        super();
        childrenNodes = new ArrayList<Node>();
        this.statementNode1 = statementNode1;
        childrenNodes.add(statementNode1);
    }
    public StatementNode(AssignStatementNode assignStatementNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.assignStatementNode = assignStatementNode;
        childrenNodes.add(this.assignStatementNode);

    }
    public StatementNode(IfNode IfStatementNode){
        super();
        this.IfStatementNode = IfStatementNode;
    }
    public StatementNode(WhileNode WhileStatementNode){
        super();
        this.WhileStatementNode = WhileStatementNode;
    }
    public String toString(){
        return "Statement";
    }
}


class AssignStatementNode extends Node{
    IdentifierNode identifierNode;
    EqualNode equalNode;
    ExpressionNode expressionNode;
    public AssignStatementNode(IdentifierNode identifierNode,EqualNode equalNode,ExpressionNode expressionNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.identifierNode = identifierNode;
        this.equalNode = equalNode;
        this.expressionNode = expressionNode;
        childrenNodes.add(this.identifierNode);
        childrenNodes.add(this.equalNode);
        childrenNodes.add(this.expressionNode);
    }
    public String toString(){
        return "Assignment";
    }
}

class EqualNode extends Node{
    String name;
    public EqualNode(){
        this.name = "=";
    }
    public String toString(){
        return "=";
    }
}

class IfNode extends Node{
    CompareStatementNode compareStatementNode;
    StatementNode statementNode;
    public IfNode(CompareStatementNode compareStatementNode, StatementNode statementNode){
        this.compareStatementNode = compareStatementNode;
        this.statementNode = statementNode;
    }
}

class WhileNode extends Node{
    CompareStatementNode compareStatementNode;
    StatementNode statementNode;
    public WhileNode(CompareStatementNode compareStatementNode, StatementNode statementNode){
        this.compareStatementNode = compareStatementNode;
        this.statementNode = statementNode;
    }
}

class CompareStatementNode extends Node{ //Y
    IdentifierNode identifierNode1;
    CompareOperatorNode compareOperatorNode;
    NumberNode numberNode;
    IdentifierNode identifierNode2;
    public CompareStatementNode(IdentifierNode identifierNode,CompareOperatorNode compareOperatorNode,NumberNode numberNode){
        this.identifierNode1 = identifierNode;
        this.compareOperatorNode = compareOperatorNode;
        this.numberNode = numberNode;
    }
    public CompareStatementNode(IdentifierNode identifierNode1,CompareOperatorNode compareOperatorNode,IdentifierNode identifierNode2){
        this.identifierNode1 = identifierNode1;
        this.compareOperatorNode = compareOperatorNode;
        this.identifierNode2 = identifierNode2;
    }

}


class CompareOperatorNode extends Node{
    String name;
    public CompareOperatorNode(String name){
        this.name=name;
    }
}

class ExpressionNode extends Node{
    ExpressionNode expressionNode;
    Operation1Node operation1Node;
    TermNode termNode;
    public ExpressionNode(ExpressionNode expressionNode,Operation1Node operation1Node,TermNode termNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.expressionNode = expressionNode;
        this.operation1Node = operation1Node;
        this.termNode = termNode;
        childrenNodes.add(this.expressionNode);
        childrenNodes.add(this.operation1Node);
        childrenNodes.add(this.termNode);
    }
    public ExpressionNode(TermNode termNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.termNode = termNode;
        childrenNodes.add(this.termNode);

    }
    public String toString(){
        return "Expression";
    }
}

class Operation1Node extends Node{
    String operator;
    public Operation1Node(String operator){
        this.operator = operator;
    }
    public String toString(){
        return operator;
    }
}

class Operation2Node extends Node{
    String operator;
    public Operation2Node(String operator){
        this.operator = operator;
    }
    public String toString(){
        return operator;
    }
}

class TermNode extends Node{
    TermNode termNode;
    Operation2Node operation2Node;
    FactorNode factorNode;
    public TermNode(TermNode termNode,Operation2Node operation2Node,FactorNode factorNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.termNode = termNode;
        this.operation2Node = operation2Node;
        this.factorNode = factorNode;
        childrenNodes.add(this.termNode);
        childrenNodes.add(this.operation2Node);
        childrenNodes.add(this.factorNode);

    }
    public TermNode(FactorNode factorNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.factorNode = factorNode;
        childrenNodes.add(this.factorNode);
    }
    public String toString(){
        return "Term";
    }
}

class FactorNode extends Node{
    ExpressionNode expressionNode;
    IdentifierNode identifierNode;
    NumberNode numberNode;
    public FactorNode(ExpressionNode expressionNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.expressionNode=expressionNode;
        childrenNodes.add(this.expressionNode);
    }
    public FactorNode(IdentifierNode identifierNode) {
        super();
        childrenNodes = new ArrayList<Node>();
        this.identifierNode = identifierNode;
        childrenNodes.add(this.identifierNode);
    }
    public FactorNode(NumberNode numberNode){
        super();
        childrenNodes = new ArrayList<Node>();
        this.numberNode = numberNode;
        childrenNodes.add(this.numberNode);
    }
    public String toString(){
        return "Factor";
    }
}

class NumberNode extends Node{
    String number;
    public NumberNode(String number){
        this.number = number;
    }
    public String toString(){
        return number;
    }
}

class IdentifierNode extends Node{
    String identifierName;
    public IdentifierNode(String identifierName){
        this.identifierName = identifierName;
    }
    public String toString(){
        return identifierName;
    }
}
