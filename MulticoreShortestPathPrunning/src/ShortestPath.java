/*
 * Author: Giang Truong
 * date: 9/21/2017
 * Class: CECS 570
 * Project: This project purpose is to find the path (tour) that have the
 * least cost in the graph using branch and bound method
 * that we learn in class. Once a tour is found all of the Node/Tour
 * that have cost >= to the found tour will be pruned.
 */
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.Vector;

import mpi.MPI;

public class ShortestPath implements Serializable{
	private int m_WaitingCount = 0;
	private boolean m_Thread_Stop_Condition = false;
	// create priority queue
	private Comparator<Node> comparator = new NodeComparator();
	private PriorityQueue<Node> m_ThreadQueue = new PriorityQueue<Node>(10, comparator);
	//private Queue<Node> m_ThreadQueue= new LinkedList<Node>();
	private double SmallestCost = 0.0;
	private int m_MaxExcludedCount = 0;
	private String m_Path = null;
	
	ShortestPath(int [] [] inputGraph, int row, int col, Vector<String> SetofEdges, int [] [] ie){
		// create root node
		Node root = new Node(inputGraph, row, col, SetofEdges,ie, "root");
		// calculate the value of root node
		root.calculate();
		// add the root node to m_ThreadQueue
		getM_ThreadQueue().add(root);
		// initialize the max excluded count
		m_MaxExcludedCount = (2-(col-1));
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
	public void ShortestPathSearch (int CurrentProcessRank){
		// pick up the root from the synchronized queue.

		Node root = StaticQueueRemove(); 

		if(root == null){
			//System.out.println("thread terminated due to condition set to true");
			return;
		}
		
		// create priority queue
		Comparator<Node> comparator = new NodeComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(10, comparator);		

		// add the root to the queue
		pq.add(root);
		
		Node temp;
		// while the queue is not empty
		while(!pq.isEmpty()){
			if(pq.size() == 0){
				break;
			}
			// remove the first item in the queue
			temp = pq.remove();
			//check for complete cycle and pruned
			if(temp.isM_completecycle() || Pruning(temp)){
				// check to see if the node we have just remove have a complete cycle.
				if(temp.isM_completecycle()){
					// synchronized function that check whether the complete
					// cycle that we detected in this node have a cost lesser
					// than smallestcost or not, also update the path
					// if it is lesser than current smallest cost
					// if SmalletcostCheck(node) return true then we have updated
					// the smallest cost go ahead and send the new smallest cost 
					// to the other processor
					
					if(SmalletcostCheck(temp)){
						//found a complete cycle send the smallest cost to process 2 if you are process 1 vice versa
						double smallest[] = {getSmallestCost()};
						// send the smallest cost to other process
						if(CurrentProcessRank == 1){
							try{
								//void Send(Object buf, int offset, int count, Datatype type,int dst, int tag) ;
								MPI.COMM_WORLD.Isend(smallest,0,1,MPI.DOUBLE,2,98);
							}
							catch (Exception e) {
							    System.out.println(e);    
							    System.out.println("send 1 exception");
							}
						}
						else{
							try{
								//void Send(Object buf, int offset, int count, Datatype type,int dst, int tag) ;
								MPI.COMM_WORLD.Isend(smallest,0,1,MPI.DOUBLE,1,98);
							}
							catch (Exception e) {
							    System.out.println(e);    
							    System.out.println("send 1 exception");
							}
						}	
						// receiving smallest cost from other project
						double externalSmallestCost[] = {0.0};
						if(CurrentProcessRank == 1){
							try{
								MPI.COMM_WORLD.Recv(externalSmallestCost,0,1,MPI.DOUBLE,2,98);
								// check to see if the external smallest cost better than current smallest cost
								//System.out.println("external smallest cost is "+externalSmallestCost[0]); 
								SmalletcostCheckUsingDouble(externalSmallestCost[0]);
							}
							catch (Exception e) {
							    System.out.println(e);    
							    System.out.println("send 1 exception");
							}
						}
						else{
							try{
								MPI.COMM_WORLD.Recv(externalSmallestCost,0,1,MPI.DOUBLE,1,98);
								// check to see if the external smallest cost better than current smallest cost
								//System.out.println("external smallest cost is "+externalSmallestCost[0]); 
								SmalletcostCheckUsingDouble(externalSmallestCost[0]);
							}
							catch (Exception e) {
							    System.out.println(e);    
							    System.out.println("send 1 exception");
							}
						}						
					}
					
					// if queue is not empty.
					if(pq.size() != 0){
						continue;
					}
				}
							
				// pruning all path that have cost >= smallest cost
				// if smallest cost is not at default 0.0 value.
				if(Pruning(temp)){
					// the path is pruned
					if(pq.size() != 0){
						continue;
					}
				}	
			}
			// not complete cycle or pruned
			else{
				Vector<Node> ChildList = new Vector<Node>();
				ChildList = MakeChild(temp);
				// we successfully make 1 to 2 child
				if(!ChildList.isEmpty()){
					for(int i = 0; i < ChildList.size(); i++){
						pq.add(ChildList.get(i));
					}
				}
				else{
					//System.out.println("no child have been make");
				}				
			}
		}// end of while
		
		System.out.println("Smallest cost is "+ getSmallestCost() + " found by process "+CurrentProcessRank);
	}// end of shortestpath search
	
	/*
	 * NodeSplit function pop an item from the m_thread_queue and 
	 * make 2 child from that item if possible then add both child
	 * back to the m_thread_queue.
	 * input: none
	 * output: 2 child added to the queue.
	 */
	public void NodeSplit (){
		// call queue remove synchronize function to obtain the node
		// on the queue.
		// temporary node that store the node that just got remove from the queue
		Node temp = QueueRemove();
		if(temp == null){
			//System.out.println("thread reach the end since the condition is set to true");
			return;
		}
		// check to see if the node we have just remove have a complete cycle.
		if(temp.isM_completecycle()){
			// synchronized function that check whether the complete
			// cycle that we detected in this node have a cost lesser
			// than smallestcost or not, also update the path
			// if it is lesser than current smallest cost
			SmalletcostCheck(temp);
			return;
		}
	
		// pruning all path that have cost >= smallest cost
		// if smallest cost is not at default 0.0 value.
		if(Pruning(temp)){
			// the path is pruned
			return;
		}
		
		Vector<Node> ChildList = new Vector<Node>();
		ChildList = MakeChild(temp);
		// we successfully make 1 to 2 child
		if(!ChildList.isEmpty()){
			for(int i = 0; i < ChildList.size(); i++){
				QueueAdd(ChildList.get(i));
			}
		}
		else{
			//System.out.println("no child have been make");
		}

	}// end of Nodesplit
	
	
	public synchronized Node QueueRemove(){
		// this condition is for dynamic thread only
		while(IsQueueEmpty()){
			//System.out.println("share priority queue is empty "
			//		+ "remove is not possible");
			try {
				increase_WaitingCount();
				wait();
				decrease_WaitingCount();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(m_Thread_Stop_Condition == true){
				notify();
				return null;
			}
		}
		// static thread should go straigh to remove 
		// since we make enough node for each static thread at start
		// so the share queue for static thread can't be empty
		return getM_ThreadQueue().remove();
	}
	
	public synchronized Node StaticQueueRemove(){
		// this condition is for dynamic thread only
		if(IsQueueEmpty()){
			System.out.println("share queue is empty static thread cant get root");
			return null;
		}
		// static thread should go straigh to remove 
		// since we make enough node for each static thread at start
		// so the share queue for static thread can't be empty
		return getM_ThreadQueue().remove();
	}
	
	public synchronized void QueueAdd(Node child){
		m_ThreadQueue.add(child);
		// wake 1 thread up if there are threads in wait;
		notify();
	}
	// wake all thread in wait() state up
	public synchronized void WakeOneThreadUp(){
		notify();
	}
	
	public synchronized boolean IsQueueEmpty(){
		return m_ThreadQueue.isEmpty();
	}
	
	// return true if we updated the smallest cost
	// return false if no update happened.
	public synchronized boolean SmalletcostCheck(Node temp){
		if(getSmallestCost() == 0.0){
			setSmallestCost(temp.getM_value());
			setM_Path(temp.getM_path());
			return true;
		}
		// check if the new complete cycle have cost < current smallest cost
		// if it is then update smallest cost and the smallest cost path
		else{
			if(temp.getM_value() < getSmallestCost()){
				setSmallestCost(temp.getM_value());
				setM_Path(temp.getM_path());
				return true;
			}
			return false;
		}
	}
	
	public synchronized void SmalletcostCheckUsingDouble(double temp){
		if(getSmallestCost() == 0.0){
			setSmallestCost(temp);
		}
		// check if the new complete cycle have cost < current smallest cost
		// if it is then update smallest cost and the smallest cost path
		else{
			// if temp == 0.0 mean we do not receive anything from the non blocking ireceive
			// else we got a new smallest cost from the other processor and now we want to check
			// is it smaller than our current smallest cost.
			if((temp != 0.0) && temp < getSmallestCost()){
				setSmallestCost(temp);
			}
		}
	}
	
	public synchronized boolean Pruning (Node temp){
		if(getSmallestCost() != 0.0 && temp.getM_value() >= getSmallestCost()){
			return true;
		}
		return false;
	}
	
	// take the root in and make excluded and include child
	public Vector<Node> MakeChild(Node temp){
		
		Vector<Node> ChildArray = new Vector<Node>();
		
		// the first vertice in the edge we are including or excluding
		String des;
		// the second vertice in the edge we are including or excluding
		String sor;
		// the edge we are including/excluding
		// the first vertice in the edge we are including or excluding (integer)
		int x;
		// the second vertice in the edge we are including or excluding (integer)
		int y;
		String topEdge;
		//number of col
		int col = temp.getValue_matric_col();
		// number of row
		int row = temp.getValue_matric_row();
		
		// the include/exclude matrix for node 1
		int [] [] iematric1 = new int [row+2] [col+2];	
		// the include/exclude matrix for node 2
		int [] [] iematric2 = new int [row+2] [col+2];

		// the number that label the node (so i can track where am i in the graph
		// for debugging purpose.
		int counter = 0;
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
		    //====================================================//
		    // child 2 exclude node
		    // check to see if we reach the maximum excluded for
		    // x vertices.
		    if(iematric2[x][col+1] > m_MaxExcludedCount && iematric2[y][col+1] > m_MaxExcludedCount){
		    	Node c2 = new Node(temp.getM_value_matric(), row, col, temp.getM_edges(), iematric2, "c2-"+counter);
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
					ChildArray.add(c2);
				   	counter++;					   	
				}				
			}		    			    
			//================================================================================
			// child 1 include node
		   	Node c1 = new Node(temp.getM_value_matric(), row, col, temp.getM_edges(), iematric1, "c1-"+counter);
		   				   	
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
					ChildArray.add(c1);
					counter++;					
				}
		    }
			//=======================================================================
		}// end of inner while
		
		return 	ChildArray;
	}

	public double getSmallestCost() {
		return SmallestCost;
	}

	public void setSmallestCost(double smallestCost) {
		SmallestCost = smallestCost;
	}

	public String getM_Path() {
		return m_Path;
	}

	public void setM_Path(String m_Path) {
		this.m_Path = m_Path;
	}

	public PriorityQueue<Node> getM_ThreadQueue() {
		return m_ThreadQueue;
	}

	public void setM_ThreadQueue(PriorityQueue<Node> m_ThreadQueue) {
		this.m_ThreadQueue = m_ThreadQueue;
	}

	public boolean isM_Thread_Stop_Condition() {
		return m_Thread_Stop_Condition;
	}

	public void setM_Thread_Stop_Condition(boolean m_Thread_Stop_Condition) {
		this.m_Thread_Stop_Condition = m_Thread_Stop_Condition;
	}

	public int getM_WaitingCount() {
		return m_WaitingCount;
	}
	
	public synchronized void increase_WaitingCount() {
		m_WaitingCount++;
	}
	
	public synchronized void decrease_WaitingCount() {
		m_WaitingCount--;
	}

	public void setM_WaitingCount(int m_WaitingCount) {
		this.m_WaitingCount = m_WaitingCount;
	}
	
}// end of class


