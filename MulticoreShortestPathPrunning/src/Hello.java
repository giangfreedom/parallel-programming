//Hello.java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Vector;

import mpi.*;
public class Hello {
  public static void main(String args[]) throws Exception {
    try {
      MPI.Init(args);
      int rank = MPI.COMM_WORLD.Rank();
      int size= MPI.COMM_WORLD.Size();
      
      if(rank == 0){
    	  ProgInit();
      }
      else{
    	  MyReceive(rank);
      }
      
      System.out.println("Hi from <" + rank +" >" );
      MPI.Finalize();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

	public static void ProgInit(){
		// graph 1 in class       a  b  c  d  e 
		int [] [] graph1 = {	{ 0, 3, 4, 2, 7},
								{ 3, 0, 4, 6, 3},
								{ 4, 4, 0, 5, 8},
								{ 2, 6, 5, 0, 6},
								{ 7, 3, 8, 6, 0},								
						   };
		// include/exclude matrix 0 for undecided
		// 1 for include and -1 for exclude
		// 9 mean no edge.
		int [] [] iematric1 = {	{ 9, 0, 0, 0, 0, 0, 0},
								{ 0, 9, 0, 0, 0, 0, 0},
								{ 0, 0, 9, 0, 0, 0, 0},
								{ 0, 0, 0, 9, 0, 0, 0},
								{ 0, 0, 0, 0, 9, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0}
							};
		

		int [] [] graph4 = {    { 0, 3, 4, 2, 7, 1, 1, 1, 1, 1, 1, 1},
								{ 3, 0, 4, 6, 3, 1, 1, 1, 1, 1, 1, 1},
								{ 4, 4, 0, 5, 8, 1, 1, 1, 1, 1, 1, 1},
								{ 2, 6, 5, 0, 6, 1, 1, 1, 1, 1, 1, 1},
								{ 7, 3, 8, 6, 0, 1, 1, 1, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
							};		

		int [] [] iematric4 = {	{ 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							};
		// graph 2 self create    a  b  c  d  e  f
		int [] [] graph2 = {    { 0, 3, 4, 2, 7, 1},
				 				{ 3, 0, 4, 6, 3, 1},
				 				{ 4, 4, 0, 5, 8, 1},
				 				{ 2, 6, 5, 0, 6, 1},
				 				{ 7, 3, 8, 6, 0, 1},
				 				{ 1, 1, 1, 1, 1, 0},				
			 			   };
		// include/exclude matrix 0 for undecided
		// 1 for include and -1 for exclude
		// 9 mean no edge.
		int [] [] iematric2 = {	{ 9, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 9, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 9, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 9, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 9, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 9, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0}
							  };
		// graph2 set of edges for root node  ab   ac   ad   ae   af   bc   bd   be   bf   cd   ce   cf   de   df   ef
		//									 "01","02","03","04","05","12","13","14","15","23","24","25","34","35","45"
		int l = 1;
		Vector<String> edges2 = new Vector<String>();
		for(int i = 0; i < 5; i++){
			for(int j = l; j < 6; j++){
				String s = ""+i+","+j;
				edges2.add(s);
			}
			l++;
		}
		
		// array of edge that we will be include every round of the algorithm.
		Vector<String> edges1 = new Vector<String>();
		
		// graph1 set of edges for root node  ab   ac   ad   ae   bc   bd   be   cd   ce   de
		//									 "01","02","03","04","12","13","14","23","24","34"
		int k = 1;
		for(int i = 0; i < 4; i++){
			for(int j = k; j < 5; j++){
				String s = ""+i+","+j;
				edges1.add(s);
			}
			k++;
		}
		
		// graph 4
		int v = 1;
		Vector<String> edges4 = new Vector<String>();
		for(int i = 0; i < 11; i++){
			for(int j = v; j < 12; j++){
				String s = ""+i+","+j;
				edges4.add(s);
			}
			v++;
		}	
		//MySend(graph4, 12, 12, edges4, iematric4);
		MySend(graph1, 5, 5, edges1, iematric1);
		//MySend(graph2, 6, 6, edges2, iematric2);

	} // end of ProgInit

	public static void MySend(int [] [] inputGraph, int row, int col, Vector<String> SetofEdges, int [] [] ie){
		// create ShortestPath object and initialize the root node
		// add the root node to the queue in constructor
		ShortestPath SP = new ShortestPath(inputGraph, row, col, SetofEdges, ie);
		
		// split the 1st node into 2 new node
		SP.NodeSplit();
		
		// pop the first node off and make a new shortestpath object using the 1st node information.
		// this new shortest path object will be send to processor 1 and become it root node
		// then processor 1 can call shortestpath search and begin the traversing
		Node root1 = SP.getM_ThreadQueue().poll();
		ShortestPath SP1 = new ShortestPath(root1.getM_value_matric(), root1.getValue_matric_row(), root1.getValue_matric_col(), root1.getM_edges(), root1.getIe_matric());
		
		//convert the object into char string to send
		try {
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();

		    ObjectOutputStream oos = new ObjectOutputStream(baos);

		    oos.writeObject(SP1);

		    //oos.flush();
		    //serialized = baos.toString();
		    byte[] bytes = baos.toByteArray();

            System.out.println("Serialized to " + bytes.length);

            MPI.COMM_WORLD.Isend(bytes, 0, bytes.length, MPI.BYTE, 1, 0);
            
			//char [] message = serialized.toCharArray() ;
			
			//void Send(Object buf, int offset, int count, Datatype type,int dst, int tag) ;
			//MPI.COMM_WORLD.Send(message,0,message.length,MPI.CHAR,1,99);
			//Status Recv(Object buf, int offset, int count, Datatype type,int src, int tag) ;
			//System.out.println("send 1 test");
		} 
		catch (Exception e) {
		    System.out.println(e);    
		    System.out.println("send 1 exception");
		}
		

		// pop the second node off and this one is send to processor 2
		Node root2 = SP.getM_ThreadQueue().poll();
		ShortestPath SP2 = new ShortestPath(root2.getM_value_matric(), root2.getValue_matric_row(), root2.getValue_matric_col(), root2.getM_edges(), root2.getIe_matric());
		
		//convert the object into char string to send
		try {
		    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		    ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
		    oos2.writeObject(SP2);
		    //oos.flush();
		    //serialized2 = baos.toString();
		    byte[] bytes2 = baos2.toByteArray();

            System.out.println("Serialized to " + bytes2.length);

            MPI.COMM_WORLD.Isend(bytes2, 0, bytes2.length, MPI.BYTE, 2, 0);
		    
			//char [] message2 = serialized2.toCharArray() ;
			
			//void Send(Object buf, int offset, int count, Datatype type,int dst, int tag) ;
			//MPI.COMM_WORLD.Send(message2,0,message2.length,MPI.CHAR,2,99);
			//System.out.println("send 2 test");
		} 
		catch (Exception e) {
		    System.out.println(e);    
		    System.out.println("send 2 exception");
		}
	}	
	
	public static void MyReceive(int CurrentProcessRank){
		//char [] message = new char[2000];
		
		byte[] bytes = new byte[3000];
		ShortestPath recv = null;
		
		//Status Recv(Object buf, int offset, int count, Datatype type,int src, int tag) ;
		//MPI.COMM_WORLD.Recv(message,0,message.length,MPI.CHAR,0,0);
		
		MPI.COMM_WORLD.Recv(bytes, 0, 2000, MPI.BYTE, 0, 0);
		
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
		//System.out.println("receive test");
		
		//convert the array of char back to string
		//String serializedObject =  message.toString();
		// now take that string and revert it into byte array
		try {
            in = new ObjectInputStream(bis);
            Object obj = in.readObject();
            recv = (ShortestPath)obj;
			//byte b[] = serializedObject.getBytes(); 
		    //ByteArrayInputStream bis = new ByteArrayInputStream(b);
		    //ObjectInputStream ois = new ObjectInputStream(bis);
		    //ShortestPath objSP = (ShortestPath) ois.readObject();
		    
		    // now do the search normally
		    //objSP.ShortestPathSearch(CurrentProcessRank);
            recv.ShortestPathSearch(CurrentProcessRank);
		} catch (Exception e) {
		    System.out.println(e);
		    System.out.println("receive exception");
		}
	}
}	