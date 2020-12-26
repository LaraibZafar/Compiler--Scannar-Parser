abstract class Node{
    Node childNode[];
}

public class ProgramNode extends Node {
    StatementNode statementNode;
    public ProgramNode(StatementNode statementNode){
        this.statementNode = statementNode;
    }
}

class StatementNode extends Node{
    StatementNode statementNode1;
    StatementNode statementNode2;
    AssignStatementNode assignStatementNode;
    IfNode IfStatementNode;
    WhileNode WhileStatementNode;

    public StatementNode(StatementNode statementNode1, StatementNode statementNode2){
        this.statementNode1 = statementNode1;
        this.statementNode2 = statementNode2;
    }
    public StatementNode(StatementNode statementNode1){
        this.statementNode1 = statementNode1;
    }
    public StatementNode(AssignStatementNode assignStatementNode){
        this.assignStatementNode = assignStatementNode;
    }
    public StatementNode(IfNode IfStatementNode){
        this.IfStatementNode = IfStatementNode;
    }
    public StatementNode(WhileNode WhileStatementNode){
        this.WhileStatementNode = WhileStatementNode;
    }
}


class AssignStatementNode extends Node{
    IdentifierNode identifierNode;
    EqualNode equalNode;
    ExpressionNode expressionNode;
    public AssignStatementNode(IdentifierNode identifierNode,EqualNode equalNode,ExpressionNode expressionNode){
        this.identifierNode = identifierNode;
        this.equalNode = equalNode;
        this.expressionNode = expressionNode;
    }
}

class EqualNode extends Node{
    String name;
    public EqualNode(){
        this.name = "=";
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
        this.expressionNode = expressionNode;
        this.operation1Node = operation1Node;
        this.termNode = termNode;
    }
    public ExpressionNode(TermNode termNode){
        this.termNode = termNode;
    }
}

class Operation1Node extends Node{
    String operator;
    public Operation1Node(String operator){
        this.operator = operator;
    }
}

class Operation2Node extends Node{
    String operator;
    public Operation2Node(String operator){
        this.operator = operator;
    }
}

class TermNode extends Node{
    TermNode termNode;
    Operation2Node operation2Node;
    FactorNode factorNode;
    public TermNode(TermNode termNode,Operation2Node operation2Node,FactorNode factorNode){
        this.termNode = termNode;
        this.operation2Node = operation2Node;
        this.factorNode = factorNode;
    }
    public TermNode(FactorNode factorNode){
        this.factorNode = factorNode;
    }
}

class FactorNode extends Node{
    ExpressionNode expressionNode;
    IdentifierNode identifierNode;
    NumberNode numberNode;
    public FactorNode(ExpressionNode expressionNode){
        this.expressionNode=expressionNode;
    }
    public FactorNode(IdentifierNode identifierNode){
        this.identifierNode = identifierNode;
    }
    public FactorNode(NumberNode numberNode){
        this.numberNode = numberNode;
    }

}

class NumberNode extends Node{
    String number;
    public NumberNode(String number){
        this.number = number;
    }
}

class IdentifierNode extends Node{
    String identifierName;
    public IdentifierNode(String identifierName){
        this.identifierName = identifierName;
    }
}
