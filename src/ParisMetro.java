import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mackenzie on 11/30/2017.
 */
public class ParisMetro {

    private static ArrayList<Node> nodes = new ArrayList<Node>();
    private static ArrayList<Edge> edges = new ArrayList<Edge>();

    private static Node[] nodesArray;




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

    private static void reset(){ // reset all nodes
        for(Node node: nodes){
            node.setvisited(false);
        }
    }


    public static void main(String args[]){
        getData();
        ArrayList<Node> sameLineNodes = sameLine(111);
        for(Node node: sameLineNodes){
            System.out.println(node.getName());
        }
    }
 }

