import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;


public class testpq {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		int[] arr = {2,7,10,9,3,1};
		PriorityQueue pq = new PriorityQueue();
		for (int i = 0; i < arr.length; i++){
			if(arr[i] != 0){
				pq.add(arr[i]);
			}
		}
		int temp;
		while(!pq.isEmpty()){
			temp = (int) pq.remove();
			System.out.println(temp);
		}
		
		int [] [] graph1 = {	{ 0, 3, 4, 2, 7},
				{ 3, 0, 4, 6, 3},
				{ 4, 4, 0, 5, 8},
				{ 2, 6, 5, 0, 6},
				{ 7, 3, 8, 6, 0},								
		   };
		int [] a = graph1[0];
		for (int i = 0; i < a.length; i++){
			System.out.println(a[i]);
		}
		*/
		/*
		for(int x = 0; x < getIe_matric().length;x++){
			for(int j = 0; j < getIe_matric()[x].length;j++){
				System.out.print(getIe_matric()[x][j]+" ");
			}
			System.out.println();
	    }
		 */
		/*
		// check if 2 edge have been included
		// 2 included
		if(temp.getIe_matric()[x][col] == 2 || temp.getIe_matric()[row][x] == 2
				|| temp.getIe_matric()[x][col+1] == -2 || temp.getIe_matric()[row+1][x] == -2){
			// included 2 edge
			if(temp.getIe_matric()[x][col] == 2 && temp.getIe_matric()[row][x] == 2){
				// set all 0 to -1 in the row x
				for(int v = 0; v < col; v++){
					if(temp.getIe_matric()[x][v] == 0){
						temp.getIe_matric()[x][v] = -1;
					}
				}
				temp.getIe_matric()[x][col+1] = -2;
				// set all 0 to -1 col x
				for(int u = 0; u < row; u++){
					if(temp.getIe_matric()[u][x] == 0){
						temp.getIe_matric()[u][x] = -1;
					}
				}
				temp.getIe_matric()[row+1][x] = -2;

				// remove all edge with x (des) in it.
				temp.removeEdges(des);
				continue;
			}

			// excluded 2 edge 01 02
			if(temp.getIe_matric()[x][col+1] == -2 && temp.getIe_matric()[row+1][x] == -2){
				// set all 0 to 1 in row x
				for(int v = 0; v < col; v++){
					if(temp.getIe_matric()[x][v] == 0){
						temp.getIe_matric()[x][v] = 1;
					}
				}
				temp.getIe_matric()[x][col] = 2;
				// col x
				for(int u = 0; u < row; u++){
					if(temp.getIe_matric()[u][x] == 0){
						temp.getIe_matric()[u][x] = 1;
					}
				}
				temp.getIe_matric()[row][x] = 2;
				continue;
			}
			*/
		// premature cycle
		/*
		if(r == 1){
			//System.out.println("check 1");
			topEdge = temp.getM_edges().remove(0);
			des = topEdge.charAt(0)+"";
			sor = topEdge.charAt(1)+"";
		}
		// complete cycle
		else if(r == 2){
			//System.out.println("check 2");
			// update the iematric for included.
			iematric1[x][y] = 1;
			iematric1[y][x] = 1;
			iematric1[x][col]++;
			iematric1[col][x]++;
			iematric1[y][col]++;
			iematric1[row][y]++;
			Node c1 = new Node(graph1, row, col, temp.getM_edges(), iematric1, "c1-"+counter);
			
			if(c1.getM_value() > smallestCost && smallestCost != 0.0){
				break;
			}					
			// set the new node m_path to this node m_path because when we
			// check and found that adding an edge give complete cycle we also save the path for that.
			c1.setM_path(temp.getM_path());
			c1.setM_completecycle(true);
			pq.add(c1);
			break;
		}
		// no cycle
		else if(r == 3){

		}// end of else if
	*/
		/*
		public void MarkRemove (String s) {
			// extract the 2 vertices and convert to matric subscript
			String firstV = s.charAt(0)+"";
			String secondV = s.charAt(1)+"";
			int x = Integer.parseInt(firstV);
			int y = Integer.parseInt(secondV);
			// subscript into the matric mark them -1
			// then decrease the excluded by 1
			if(ie_matric[x][y] == 0){
				ie_matric[x][y] = -1;
			}
			if(ie_matric[y][x] == 0){
				ie_matric[y][x] = -1;
			}
			if(ie_matric[x][value_matric_col+1] > (2-(value_matric_col-1)) 
					&& ie_matric[value_matric_row+1][x] > (2-(value_matric_col-1))){
				ie_matric[x][value_matric_col+1]--;
				ie_matric[value_matric_row+1][x]--;
			}
			if(ie_matric[y][value_matric_col+1] > (2-(value_matric_col-1)) 
					&& ie_matric[value_matric_row+1][y] > (2-(value_matric_col-1))){
				ie_matric[y][value_matric_col+1]--;
				ie_matric[value_matric_row+1][y]--;
			}
		}
		
		// the function take 2 integer input represent the
		// edge subscript in the included/excluded matric (iematric)
		// then return true if we can use the edge
		// false if we can't
		public boolean CanUse (int x, int y) {		
			// the edge xy and yx have not been included or excluded
			if(getIe_matric()[x][y] == 0 && getIe_matric()[y][x] == 0){
				// both vertices x and y have not been filled with 2 edge
				if(getIe_matric()[x][getValue_matric_col()] < 2 && getIe_matric()[y][getValue_matric_col()] < 2){
					return true;
				}
			}
			
			if(ie_matric[x][y] == 0){
				ie_matric[x][y] = -1;
			}
			if(ie_matric[y][x] == 0){
				ie_matric[y][x] = -1;
			}
			if(ie_matric[x][value_matric_col+1] > (2-(value_matric_col-1)) 
					&& ie_matric[value_matric_row+1][x] > (2-(value_matric_col-1))){
				ie_matric[x][value_matric_col+1]--;
				ie_matric[value_matric_row+1][x]--;
			}
			if(ie_matric[y][value_matric_col+1] > (2-(value_matric_col-1)) 
					&& ie_matric[value_matric_row+1][y] > (2-(value_matric_col-1))){
				ie_matric[y][value_matric_col+1]--;
				ie_matric[value_matric_row+1][y]--;
			}
			return false;
		}
		*/
		
		/*
		 * ShortestPathSearch function search for the tour with the minimum cost in the graph
		 * input: a value matrix for calculation (the graph)
		 * 		  the value matrix number of row
		 * 		  the value matrix number of col
		 * 		  the set of edges for that will be adding in every round of algorithm
		 * 		  the include/excluded matrix
		 * output: text on console for the cost and shortest path.
		 */
		/*
		public static void ShortestPathSearch (int [] [] inputGraph, int row, int col, Vector<String> SetofEdges, int [] [] ie){
			double smallestCost = 0.0;
			String path = null;
			// create priority queue
			Comparator<Node> comparator = new NodeComparator();
			PriorityQueue<Node> pq = new PriorityQueue<Node>(10, comparator);
			int maxExcludedCount = (2-(col-1));
			System.out.println("maxExcludedCount is : "+maxExcludedCount);
			
			// create root node
			Node root = new Node(inputGraph, row, col, SetofEdges,ie, "root"); 
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
				System.out.println("Removed Node : "+ temp.getM_name());						
				System.out.println("Removed Node cost: "+ temp.getM_value());
				System.out.println("Removed Node vector of edges: ");
				for (int i = 0; i < temp.getM_edges().size(); i++){
					System.out.print(temp.getM_edges().get(i)+", ");
				}
				System.out.println();
				System.out.println();
				System.out.println();
				
				// pruning all path that have cost >= smallest cost
				// if smallest cost is not at default 0.0 value.
				if(smallestCost != 0.0){
					if(temp.getM_value() >= smallestCost){
						System.out.println("path pruned cost: "+temp.getM_value());
						continue;
					}
				}
				// check to see if the node we have just remove have a complete cycle.
				if(temp.isM_completecycle()){
					// get it value and compare to smallest cost.
					// update the path if smallest than current smallest cost
					if(smallestCost == 0.0){
						smallestCost = temp.getM_value();
						path = temp.getM_path();
					}				
					System.out.println("complete path cost: "+temp.getM_value());
					System.out.println("complete path: "+temp.getM_path());	
					continue;
				}
			
				// check for empty edges set
				if(!temp.getM_edges().isEmpty()){
					// take the top edge in the array of edge of the node just got removed
					// from the queue.
					topEdge = temp.getM_edges().remove(0);
					des = topEdge.charAt(0)+"";
					sor = topEdge.charAt(1)+"";
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
				    //System.out.println("temp matrix (also the matrix copied to ie1 and ie2): ");	
				    //temp.PrintMatrix();
				    //====================================================//
				    
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
							System.out.println("c2 Node cost: "+ c2.getM_value());
							System.out.println("c2 vector of edges: ");
							for (int i = 0; i < c2.getM_edges().size(); i++){
								System.out.print(c2.getM_edges().get(i)+", ");
							}
							System.out.println();
							System.out.println();
							System.out.println();
						   	counter++;	
						}				
					}		    			    
					//================================================================================
					// include node
					System.out.println("temp edge b4 pass in c1 vector of edges: ");
					for (int i = 0; i < temp.getM_edges().size(); i++){
						System.out.print(temp.getM_edges().get(i)+", ");
					}
					System.out.println();
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
						des = topEdge.charAt(0)+"";
						sor = topEdge.charAt(1)+"";
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
							System.out.println("c1 Node cost: "+ c1.getM_value());
							System.out.println("c1 vector of edges: ");
							for (int i = 0; i < c1.getM_edges().size(); i++){
								System.out.print(c1.getM_edges().get(i)+", ");
							}
							System.out.println();
							System.out.println();
							System.out.println();
							counter++;
						}
				    }
					//=======================================================================
				}// end of inner while
				
			}// end of outer while
			System.out.println("program ended");
			System.out.println("smallest cost is: "+ smallestCost);
			System.out.println("smallest cost path is: "+path);
		}// end of shortestpath search
		*/
		Vector<String> edges = new Vector<String>();
		int k = 1;
		for(int i = 0; i < 4; i++){
			for(int j = k; j < 5; j++){
				String s = ""+i+""+j;
				edges.add(s);
				System.out.println(s);
			}
			k++;
		}
		removeEdges("0",edges);
	}
	public static void removeEdges (String target, Vector<String> edges){
		System.out.println(target);
		for (int i = 0; i < edges.size(); i++){
			if(edges.elementAt(i).contains(target)){
				System.out.println("removed: "+edges.get(i));
				edges.remove(i);
				i=-1;
			}
		}
		
		for(int j = 0; j < edges.size(); j++){
			System.out.println("remain: "+edges.get(j));
		}
	}

}
