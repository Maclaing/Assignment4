import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

            ParisMetro.nodes.add(tempN);//add vertex to the graph

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
        start.setvisited(true);

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
        curNode.setvisited(true);
        nodeArr.add(curNode);

        for(Edge e : curNode.getOutGoingEdges()){
            if(e.getTime()!=-1 && e.getFinishNode().isVisited()==false){
                    sameLine(e.getFinishNode(),nodeArr);

                }
            }

        return nodeArr;




    }
	
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
                nextNode.setPre(insideCloud.get(insideCloud.size() - 1));
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
		
		
		for(Edge e: n.getOutGoingEdges()){
			Node adjacentNode = e.getFinishNode();
			
			
			
			if(!insideCloud.contains(adjacentNode)){
				int distance = e.getTime();
				if(distance == -1){
					distance = WALK;
				}
				int newDistance = n.getTimer() + distance;
				
				if(adjacentNode.getTimer() > newDistance){
					adjacentNode.setTimer(newDistance); 
					outsideCloud.add(adjacentNode);
				}
			}
		}
	}

    private static void reset(){ // reset all nodes
        for(Node node: nodes){
            node.setvisited(false);
        }
    }


    public static void main(String args[]){
        getData();


        System.out.println(shortestPath(132, 327));
    }
 }

