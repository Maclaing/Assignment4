import java.util.ArrayList;

/**
 * Created by mackenzie on 11/30/2017.
 */
public class Node implements Comparable<Node> {

    private String id;
    private String name;
    private boolean visited;
    private int timer = Integer.MAX_VALUE;
	private Node predecessor;
	private boolean isUsable;

    private ArrayList<Edge> inComingEdges = new ArrayList<Edge>();
    private ArrayList<Edge> outGoingEdges = new ArrayList<Edge>();

    public Node(String id, String name){
        this.name=name;
        this.id=id;
        this.visited=false;
        this.isUsable = true;


    }
    public int compareTo(Node n2){
//                         if true return 1 else                 if true return -1 else return 0
        return this.getTimer()>n2.getTimer()?1:(this.getTimer()<n2.getTimer()?-1:0);
    }


    // auto generated getters and setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return visited;
    }

    public int getTimer() {
        return timer;
    }


    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public ArrayList<Edge> getInComingEdges() {
        return inComingEdges;
    }

    public ArrayList<Edge> getOutGoingEdges() {
        return outGoingEdges;
    }

	public Node getPre(){
		return(predecessor);
	}
	
	public void setPre(Node pre){
		predecessor = pre; 
	}




    public void setvisited(boolean visited) {
        this.visited = visited;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }


    public void addInComingEdge(Edge inComingEdge) {
        this.inComingEdges.add(inComingEdge);
    }

    public void addOutGoingEdge(Edge outGoingEdge) {
        this.outGoingEdges.add(outGoingEdge);
    }
}
