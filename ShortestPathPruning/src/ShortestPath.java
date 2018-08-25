/*
 * Author: Giang Truong
 * date: 9/21/2017
 * Class: CECS 570
 * Project: This project purpose is to find the path (tour) that have the
 * least cost in the graph using branch and bound method
 * that we learn in class. Once a tour is found all of the Node/Tour
 * that have cost >= to the found tour will be pruned.
 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.Vector;

public class ShortestPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
		
		// graph 2 self create       a  b  c  d  e  f
		int [] [] graph2 = {    { 0, 3, 4, 2, 7, 1},
				 				{ 3, 0, 4, 6, 3, 1},
				 				{ 4, 4, 0, 5, 8, 1},
				 				{ 2, 6, 5, 0, 6, 1},
				 				{ 7, 3, 8, 6, 0, 1},
				 				{ 1, 1, 1, 1, 1, 0},				
			 			   };
		int [] [] graph3 = {    { 0, 3, 4, 2, 7, 1, 1, 1, 1, 1, 1},
								{ 3, 0, 4, 6, 3, 1, 1, 1, 1, 1, 1},
								{ 4, 4, 0, 5, 8, 1, 1, 1, 1, 1, 1},
								{ 2, 6, 5, 0, 6, 1, 1, 1, 1, 1, 1},
								{ 7, 3, 8, 6, 0, 1, 1, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
								{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			   			};		

int [] [] iematric3 = {	{ 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
		// graph 3
		int u = 1;
		Vector<String> edges3 = new Vector<String>();
		for(int i = 0; i < 10; i++){
			for(int j = u; j < 11; j++){
				String s = ""+i+","+j;
				edges3.add(s);
			}
			u++;
		}
		//ShortestPathSearch(graph1, 5, 5, edges1, iematric1);
		//ShortestPathSearch(graph2, 6, 6, edges2, iematric2);	
		ShortestPathSearch(graph3, 11, 11, edges3, iematric3);
	}
	/*
	 * ShortestPathSearch function search for the tour with the minimum cost in the graph
	 * input: a value matrix for calculation (the graph)
	 * 		  the value matrix number of row
	 * 		  the value matrix number of col
	 * 		  the set of edges for that will be adding in every round of algorithm
	 * 		  the include/excluded matrix
	 * output: text on console for the cost and shortest path.
	 */
	public static void ShortestPathSearch (int [] [] inputGraph, int row, int col, Vector<String> SetofEdges, int [] [] ie){
		double smallestCost = 0.0;
		String path = null;
		// create priority queue
		Comparator<Node> comparator = new NodeComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(10, comparator);
		int maxExcludedCount = (2-(col-1));
		System.out.println("maxExcludedCount is : "+maxExcludedCount);
		System.out.println();
		
		// create root node
		Node root = new Node(inputGraph, row, col, SetofEdges,ie, "root"); 
		// calculate the value of root node
		root.calculate();
		// add the root to the queue
		pq.add(root);
		
		// temporary node that store the node that being remove from the queue each round
		Node temp;
		// the first vertice in the edge we are including or excluding
		String des;
		// the second vertice in the edge we are including or excluding
		String sor;
		// the edge we are including/excluding
		String topEdge;
		// the include/exclude matrix for node 1
		int [] [] iematric1 = new int [row+2] [col+2];	
		// the include/exclude matrix for node 2
		int [] [] iematric2 = new int [row+2] [col+2];
		// the first vertice in the edge we are including or excluding (integer)
		int x;
		// the second vertice in the edge we are including or excluding (integer)
		int y;
		// the number that label the node (so i can track where am i in the graph
		// for debugging purpose.
		int counter = 0;
		
		// while the queue is not empty
		while(!pq.isEmpty()){
			// remove the first item in the queue
			temp = pq.remove();
			System.out.println();
			System.out.println("Node removed from the queue name : "+ temp.getM_name());						
			System.out.println("Node removed from the queue cost : "+ temp.getM_value());
			System.out.print ("Node removed from the queue remaining edges: ");
			for (int i = 0; i < temp.getM_edges().size(); i++){
				System.out.print(temp.getM_edges().get(i)+", ");
			}
			System.out.println();
			System.out.println();
			if(temp.isM_completecycle() || (smallestCost != 0.0 && temp.getM_value() >= smallestCost)){
				// check to see if the node we have just remove have a complete cycle.
				if(temp.isM_completecycle()){
					System.err.println("Complete path found");
					System.err.println("complete path cost: "+temp.getM_value());
					System.err.println("complete path: "+temp.getM_path());	
					System.out.println();
					// get it value and compare to smallest cost.
					// update the path if smallest than current smallest cost
					if(smallestCost == 0.0){
						smallestCost = temp.getM_value();
						path = temp.getM_path();
					}
					// check if the new complete cycle have cost < current smallest cost
					// if it is then update smallest cost and the smallest cost path
					else{
						if(temp.getM_value() < smallestCost){
							smallestCost = temp.getM_value();
							path = temp.getM_path();
							System.out.println("update smallest cost: "+temp.getM_value());
							System.out.println();
						}
					}
					if(pq.size() != 0){
						continue;
					}
				}
			
				// pruning all path that have cost >= smallest cost
				// if smallest cost is not at default 0.0 value.
				if(smallestCost != 0.0 && temp.getM_value() >= smallestCost){
					System.out.println("path pruned cost: "+temp.getM_value());
					System.out.println();
					if(pq.size() != 0){
						continue;
					}
				}
			}
			else{
				// check for empty edges set
				if(!temp.getM_edges().isEmpty()){
					// take the top edge in the array of edge of the node just got removed
					// from the queue.
					topEdge = temp.getM_edges().remove(0);
					String[] s = topEdge.split(",");
					des = s[0];
					sor = s[1];
					x = Integer.parseInt(des);
					y = Integer.parseInt(sor);
					
					// copy the temp iematric to iematric1
				    for (int i = 0; i < temp.getIe_matric().length; i++) {
				    	for(int j = 0; j < temp.getIe_matric()[i].length;j++){
					    	iematric1[i][j] = temp.getIe_matric()[i][j];
					    }
				    }
				    // copy the temp iematric to iematric2
				    for (int i = 0; i < temp.getIe_matric().length; i++) {
				    	for(int j = 0; j < temp.getIe_matric()[i].length;j++){
					    	iematric2[i][j] = temp.getIe_matric()[i][j];
					    }
				    }
				    // output the matrix of the parent node (the node that just got removed from the queue)
				    /*
				    System.out.println("temp matrix (also the matrix copied to ie1 and ie2): ");	
				    temp.PrintMatrix();
					System.out.println("temp edge b4 pass in c1 vector of edges: ");
					for (int i = 0; i < temp.getM_edges().size(); i++){
						System.out.print(temp.getM_edges().get(i)+", ");
					}
					System.out.println();
					*/
				    //====================================================//
				    // child 2 exclude node
				    // check to see if we reach the maximum excluded for
				    // x vertices.
				    if(iematric2[x][col+1] > maxExcludedCount && iematric2[y][col+1] > maxExcludedCount){
				    	Node c2 = new Node(inputGraph, row, col, temp.getM_edges(), iematric2, "c2-"+counter);
						// update the iematric for excluded.
						if(c2.getIe_matric()[x][y] == 0){
							c2.getIe_matric()[x][y] = -1;
						}
						if(c2.getIe_matric()[y][x] == 0){
							c2.getIe_matric()[y][x] = -1;
						}
						c2.getIe_matric()[x][col+1]--;
						c2.getIe_matric()[y][col+1]--;
						
						// if fill out function return false
						// that mean it fail to fill out a positive matrix that
						// contain edges that have potential to form a cycle.
						if(c2.IsMaxExcludeFillOut(x) == true && c2.IsMaxExcludeFillOut(y) == true){
							// calculate c2 cost
							c2.calculate();
							// check for complete cycle
							c2.isCompleteCycle();
							
							pq.add(c2);
							System.out.println("c2 (Created Node) cost: "+ c2.getM_value());
							System.out.println("c2 (remaining edges) : ");
							for (int i = 0; i < c2.getM_edges().size(); i++){
								System.out.print(c2.getM_edges().get(i)+", ");
							}
							System.out.println();
							System.out.println();
						   	counter++;	
						}				
					}		    			    
					//================================================================================
					// child 1 include node
				   	Node c1 = new Node(inputGraph, row, col, temp.getM_edges(), iematric1, "c1-"+counter);
				   				   	
					// check premature cycle before add
					// if adding it get a premature cycle and it is the last edge
				    // skip to next condition.
				    // else get the next edge in the edge vector.
				    // also update the iematric1 to exclude the edge.
					while(c1.isPrematureCycle(des, sor)){
						// if the edge give premature cycle then exclude the edge
						// update xy to excluded
						if(c1.getIe_matric()[x][y] == 0){
							c1.getIe_matric()[x][y] = -1;
						}
						if(c1.getIe_matric()[y][x] == 0){
							c1.getIe_matric()[y][x] = -1;
						}
						c1.getIe_matric()[x][col+1]--;
						c1.getIe_matric()[y][col+1]--;
						
						c1.IsMaxExcludeFillOut(x);
						c1.IsMaxExcludeFillOut(y);
						
						if(c1.getM_edges().isEmpty()){
							break;
						}
						// get new edge update des sor x y to new value
						topEdge = c1.getM_edges().remove(0);
						String[] s1 = topEdge.split(",");
						des = s1[0];
						sor = s1[1];
						x = Integer.parseInt(des);
						y = Integer.parseInt(sor);
					}
					// both x and y must not have 2 edge included, and the xy will not give a premature cycle.
				    if(c1.getIe_matric()[x][col] < 2 && c1.getIe_matric()[y][col] < 2 && (c1.isPrematureCycle(des, sor) == false)){
						// update the iematric for included.
						if(c1.getIe_matric()[x][y] == 0){
							c1.getIe_matric()[x][y] = 1;
						}
						if(c1.getIe_matric()[y][x] == 0){
							c1.getIe_matric()[y][x] = 1;
						}
						c1.getIe_matric()[x][col]++;
						c1.getIe_matric()[y][col]++;

						// if fill out function return false
						// that mean it fail to fill out a positive matrix that
						// contain edges that have potential to form a cycle.
						if(c1.IsMaxExcludeFillOut(x) == true && c1.IsMaxExcludeFillOut(y) == true){
							// calculate c1 cost
							c1.calculate();
							// check if c1 contain a complete cycle
							c1.isCompleteCycle();
							
							pq.add(c1);
							System.out.println("c1 (Created Node) cost: "+ c1.getM_value());
							System.out.println("c1 (remaining edges): ");
							for (int i = 0; i < c1.getM_edges().size(); i++){
								System.out.print(c1.getM_edges().get(i)+", ");
							}
							System.out.println();
							System.out.println();
							counter++;
						}
				    }
					//=======================================================================
				}// end of making child
			}// end of else inside while			
		}// end of while
		System.out.println();
		System.err.println("shortestpathsearch ended ended");
		System.err.println("smallest cost is: "+ smallestCost);
		System.err.println("smallest cost path is: "+path);
		System.out.println();
	}// end of shortestpath search
	
}// end of main


