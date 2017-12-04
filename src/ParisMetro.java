import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.PriorityQueue;


/**
 * Created by mackenzie on 11/30/2017.
 */
public class ParisMetro {

    private static ArrayList<Node> nodes = new ArrayList<Node>();
    private static ArrayList<Edge> edges = new ArrayList<Edge>();

    private static Node[] nodesArray;
	
	final static int WALK = 90;




// based on content of lab 10
    public static void readMetro() throws FileNotFoundException {
        Scanner inputSc = new Scanner(new FileReader("Resources/metro.txt"));
        String numOfNodeEdg = inputSc.nextLine();

        String[] arr = numOfNodeEdg.split(" ");

        ParisMetro.nodesArray = new Node[Integer.parseInt(arr[0])]; // initialize array to hold nodes

        while (inputSc.hasNextLine()) {

            String temp = inputSc.nextLine();

            if (temp.equals("$")) {
                break;
            } // end of subway station list

            String[] tempArr = new String[2];      //create node array containing id and name
            tempArr[0] = temp.substring(0, 4);
            tempArr[1] = temp.substring(4, temp.length());

            Node tempN = new Node(tempArr[0], tempArr[1]);//(id, name)
            ParisMetro.nodesArray[Integer.parseInt(tempN.getId())] = tempN;//put into array of nodes

            ParisMetro.nodes.add(tempN);//add Node to the graph

        }


        while (inputSc.hasNextLine()) {
            String temp = inputSc.nextLine();

            String[] tempArr = temp.split(" ");//create array of edges ignore weight for now
            Edge tempEdge = new Edge(ParisMetro.nodesArray[Integer.parseInt(tempArr[0])], ParisMetro.nodesArray[Integer.parseInt(tempArr[1])], Integer.parseInt(tempArr[2]));//create a new edge

            ParisMetro.edges.add(tempEdge); //add edge to the graph

            ParisMetro.nodesArray[Integer.parseInt(tempArr[0])].addOutGoingEdge(tempEdge);//add edge to the outgoing edge list of a node
            ParisMetro.nodesArray[Integer.parseInt(tempArr[1])].addInComingEdge(tempEdge);//add edge to the incoming edge list of a node
        }
    }


    private static void getData(){
            try{
                readMetro();
            }catch(FileNotFoundException e){
                System.out.println("File not found");
        }


    }

    public static ArrayList<Node> sameLine(int id) {
        ArrayList<Node> lineNodes = new ArrayList<Node>(); // add all stations in same line into lineNodes
        ArrayList<Node> tmpLineNodes = new ArrayList<Node>(); // for the for loop

        Node start = nodesArray[id];
        lineNodes.add(start);
        start.setVisited(true);

        for (Edge e : start.getOutGoingEdges()) {
            if (e.getTime() != -1 && e.getFinishNode().isVisited()==false) {

                tmpLineNodes = sameLine(e.getFinishNode(), tmpLineNodes);
                lineNodes.addAll(tmpLineNodes);

                tmpLineNodes.clear();


            }


        }
        reset();
        return lineNodes;

    }




    private static ArrayList<Node> sameLine(Node curNode, ArrayList<Node> nodeArr){ // helper method
        curNode.setVisited(true);
        nodeArr.add(curNode);

        for(Edge e : curNode.getOutGoingEdges()){
            if(e.getTime()!=-1 && e.getFinishNode().isVisited()==false){
                    sameLine(e.getFinishNode(),nodeArr);

                }
            }

        return nodeArr;




    }
/*
    private static int shortestPath(int id1, int id2){
        // d.get(v) is upper bound on distance from src to v
        Map<Vertex<V>, Integer> d = new ProbeHashMap<>();
        // map reachable v to its d value
        Map<Vertex<V>, Integer> cloud = new ProbeHashMap<>();
        // pq will have vertices as elements, with d.get(v) as key
        AdaptablePriorityQueue<Integer, Vertex<V>> pq;
        pq = new HeapAdaptablePriorityQueue<>();
        // maps from vertex to its pq locator
        Map<Vertex<V>, Entry<Integer,Vertex<V>>> pqTokens;
        pqTokens = new ProbeHashMap<>();

        // for each vertex v of the graph, add an entry to the priority queue, with
        // the source having distance 0 and all others having infinite distance
        for (Vertex<V> v : g.vertices()) {
            if (v == src)
                d.put(v,0);
            else
                d.put(v, Integer.MAX_VALUE);
            pqTokens.put(v, pq.insert(d.get(v), v));       // save entry for future updates
        }
        // now begin adding reachable vertices to the cloud
        while (!pq.isEmpty()) {
            Entry<Integer, Vertex<V>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u, key);                             // this is actual distance to u
            pqTokens.remove(u);                            // u is no longer in pq
            for (Edge<Integer> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u,e);
                if (cloud.get(v) == null) {
                    // perform relaxation step on edge (u,v)
                    int wgt = e.getElement();
                    if (d.get(u) + wgt < d.get(v)) {              // better path to v?
                        d.put(v, d.get(u) + wgt);                   // update the distance
                        pq.replaceKey(pqTokens.get(v), d.get(v));   // update the pq entry
                    }
                }
            }
        }
        return cloud;         // this only includes reachable vertices

    }
/*
	/*
      private static int shortestPath(int ID1,int ID2){

        Node N1 = nodesArray[ID1];
        Node N2 = nodesArray[ID2];

		ArrayList<Node> insideCloud = new ArrayList<Node>();
		PriorityQueue<Node> outsideCloud = new PriorityQueue();
		N1.setTimer(0);
		outsideCloud.add(N1);
		
		
		Node nextNode;
		while(outsideCloud.size() > 0) {
            nextNode = outsideCloud.poll();
            if (insideCloud.size() - 1 == -1){
                nextNode.setPre(null);
                insideCloud.add(nextNode);
            }else{
                // nextNode.setPre(insideCloud.get(insideCloud.size() - 1));
                insideCloud.add(nextNode);
            }
			
			update(nextNode,insideCloud,outsideCloud);
			
		}

        ArrayList<Node> printArray = new ArrayList<Node>();
        int distanceTime = 0;
        Node pointer = N2;

		while(pointer.getPre() != null){
		    printArray.add(pointer);
		    pointer = pointer.getPre();
        }

        for(int i = printArray.size()-1; i >=0; i-- ){
		    System.out.println(printArray.get(i).getName());
        }

		return(N2.getTimer());
	}
	
	private static void update(Node n, ArrayList<Node> insideCloud, PriorityQueue<Node> outsideCloud){
		//Based on http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
		
		int newDistance;
		int distance;
		for(Edge e: n.getOutGoingEdges()){
			Node adjacentNode = e.getFinishNode();


			if(!insideCloud.contains(adjacentNode)){
				distance = e.getTime();
				if(distance == -1){
					distance = WALK;
				}
				newDistance = n.getTimer() + distance;
				
				if(adjacentNode.getTimer() > newDistance){
					adjacentNode.setTimer(newDistance); 
					outsideCloud.add(adjacentNode);
				}
			}
		}
	}
*/
 // based on lab 10 graphAlgorithms
    public static ArrayList <Node> shortestPath(int id1,int id2){
        reset();


        PriorityQueue<Node> Queue = new PriorityQueue<Node>();      //outside cloud
        ArrayList <Node> currentPath = new ArrayList<Node>();       //save the predecessor
        Node from = nodesArray[id1];                                //first node is id1
        int walkingTime=90;                                         // constant walk time

        from.setTimer(0);                                           //set start node distance (time) to 0
        Integer currentTime=from.getTimer();

        System.out.print(currentTime);
        Node current = from;
        int numOfStationsVisited=0;

        Queue.add(from);

        while (numOfStationsVisited<nodes.size()){
            current = Queue.remove(); //get the nearest node (node with lowest time outside the cloud)

            current.setVisited(true); //put the node inside the cloud
            currentPath = current.getPre(); //save the path

            currentPath.add(current);       //save the path

            currentTime = current.getTimer(); //get the timer for the current node, update surrounding nodes

            for (Edge e:current.getOutGoingEdges()){
                if (e.getTime()==-1){ //walking edge case
                    if(((walkingTime+currentTime)<e.getFinishNode().getTimer())&&(e.getFinishNode().isUsable()))
                    {
                        e.getFinishNode().setTimer(walkingTime+currentTime); //update times
                        e.getFinishNode().setPre(currentPath);
                    }
                }
                else{ //line edge case
                    if(((e.getTime()+currentTime)<e.getFinishNode().getTimer())&&(e.getFinishNode().isUsable()))
                    {
                        e.getFinishNode().setTimer(e.getTime()+currentTime); // update times
                        e.getFinishNode().setPre(currentPath);
                    }
                }
                e.getFinishNode().setPre(currentPath); //save predecessor
                if(!e.getFinishNode().isVisited()){Queue.add(e.getFinishNode());} //update outside cloud
            }

            numOfStationsVisited++; //counter
            if (Integer.parseInt(current.getId())==id2){current.setPre(currentPath);break;}// if node found, end
        }

        reset(); //to be safe

        return nodesArray[id2].getPre();

    }


    private static void reset(){ // reset all nodes
        for(Node node: nodes){
            node.setVisited(false);
        }
    }
    public static String toString(ArrayList<Node> input) {
        String out = "";
        for (Node n : input) {
            if (n != input.get(input.size()-1)) {
                out += n.getName() + " - ";
            } else {
                out += n.getName();
            }
        }
        return "# of stations" + input.size() + "\n" + out;
    }

    public static ArrayList<Node> shortestPathBrokenLine(int id1,int id2, int id3){
        ArrayList<Node> line= sameLine(id3);
        for(Node n: line){
            n.setUsable(false);
        }


        reset();
        ArrayList<Node> output = shortestPath(id1, id2);
        reset();
        for(Node n:line){
            n.setUsable(true);
        }
        return output;
    }


    public static void main(String args[]) {
        getData();

        if (args.length == 1) {
            ArrayList<Node> line = sameLine(Integer.parseInt(args[0]));
            System.out.println(toString(line));


        } else if (args.length == 2) {
            ArrayList<Node> temp = shortestPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println(temp.get(temp.size() - 1).getTimer() + toString(temp));
            System.out.println("Timer:" + temp.get(temp.size() - 1).getTimer() +"\n" + "Shortest path "  + ParisMetro.toString(temp));
        } else if(args.length==3){
            ArrayList<Node> tmp = shortestPathBrokenLine(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
            System.out.println("Timer "+tmp.get(tmp.size()-1).getTimer() +"\n" +"Shortest path "+ParisMetro.toString(tmp));}
        }
}







