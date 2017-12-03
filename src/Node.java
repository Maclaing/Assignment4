import java.util.ArrayList;

/**
 * Created by mackenzie on 11/30/2017.
 */
public class Node implements Comparable<Node> {

    private String id;
    private String name;
    private boolean visited;
    private boolean usable;
    private int timer;
	private ArrayList<Node> predecessor;

    private ArrayList<Edge> inComingEdges = new ArrayList<Edge>();
    private ArrayList<Edge> outGoingEdges = new ArrayList<Edge>();

    public Node(String id, String name){
        this.name=name;
        this.id=id;
        this.visited=false;
        predecessor = new ArrayList<Node>();
        usable=true;
        timer = Integer.MAX_VALUE;



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


    public ArrayList<Edge> getInComingEdges() {
        return inComingEdges;
    }

    public ArrayList<Edge> getOutGoingEdges() {
        return outGoingEdges;
    }

	public ArrayList<Node> getPre(){
		return predecessor;
	}
	
	public void setPre(ArrayList<Node> pre){
		predecessor = pre; 
	}




    public void setVisited(boolean visited) {
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

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }
}
