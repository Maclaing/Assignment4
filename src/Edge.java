/**
 * Created by mackenzie on 11/30/2017.
 */
public class Edge {
    private int time;
    private Node startNode;
    private Node finishNode;

    public Edge(Node startNode,Node finishNode,int time){
        this.startNode=startNode;
        this.finishNode=finishNode;
        this.time = time;
    }

    public int getTime(){return this.time;}
    public Node getStartNode(){return this.startNode;}
    public Node getFinishNode(){return this.finishNode;}

}
