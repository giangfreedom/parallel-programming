import java.util.Scanner;
import java.util.concurrent.*;
import java.util.Vector;

public class MainThread extends Thread{

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
		while(true){
			Menu(graph4, 12, 12, edges4, iematric4);
			//Menu(graph1, 5, 5, edges1, iematric1);
		}
	}

	public static void Menu(int [] [] inputGraph, int row, int col, Vector<String> SetofEdges, int [] [] ie){
		// create ShortestPath object and initialize the root node
		// add the root node to the queue in constructor
		ShortestPath SP = new ShortestPath(inputGraph, row, col, SetofEdges, ie);
		
		System.out.println("enter your choice");
		System.out.println("1) static 2 threads");
		System.out.println("2) static 4 threads");
		System.out.println("3) dynamic 2 threads");
		System.out.println("4) dynamic 4 threads");
		System.out.println("5) exist");
		
		Scanner sc=new Scanner(System.in);
		int choice = sc.nextInt();
				
		if(choice == 1){	
			long startTime = System.currentTimeMillis();
			// split the 1st node into 2 new node
			SP.NodeSplit();
			// create2 thread;
			MyThread t1 = new MyThread(SP);
			MyThread t2 = new MyThread(SP);
			t1.start();
			t2.start();			
			ThreadComplete(SP, 1);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Static 2 threads Total used time is: "+totalTime + " millisecond");
			System.out.println();
		}
		else if(choice == 2){
			long startTime = System.currentTimeMillis();
			// split the 1st node into 2 new node
			SP.NodeSplit();
			SP.NodeSplit();
			SP.NodeSplit();
			// create2 thread;
			MyThread t1 = new MyThread(SP);
			MyThread t2 = new MyThread(SP);
			MyThread t3 = new MyThread(SP);
			MyThread t4 = new MyThread(SP);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			ThreadComplete(SP, 2);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Static 4 threads Total used time is: "+totalTime + " millisecond");
			System.out.println();
		}
		else if(choice == 3){
			/*
			 * for dynamic i think the main make 2 mythreaddynamic
			 * then within the 2 thread they will each call nodesplit
			 * which will populate the queue to 4 item
			 * then create 2 mythread inside them which now become 4.
			 * then have the 4 thread run as static.
			 */
			long startTime = System.currentTimeMillis();
			// split the 1st node into 2 new node
			SP.NodeSplit();
			// make 2 dynamic thread
			MyThreadDynamic t1 = new MyThreadDynamic(SP);
			MyThreadDynamic t2 = new MyThreadDynamic(SP);
			t1.start();
			t2.start();
			ThreadComplete(SP, 3);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Dynamic 2 threads Total used time is: "+totalTime + " millisecond");
			System.out.println();
		}
		else if(choice == 4){
			/*
			 * for dynamic i think the main make 2 mythreaddynamic
			 * then within the 2 thread they will each call nodesplit
			 * which will populate the queue to 4 item
			 * then create 2 mythread inside them which now become 4.
			 * then have the 4 thread run as static.
			 */
			long startTime = System.currentTimeMillis();
			// split the 1st node into 2 new node
			SP.NodeSplit();
			// make 2 dynamic thread
			MyThreadDynamic t1 = new MyThreadDynamic(SP);
			MyThreadDynamic t2 = new MyThreadDynamic(SP);
			MyThreadDynamic t3 = new MyThreadDynamic(SP);
			MyThreadDynamic t4 = new MyThreadDynamic(SP);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			ThreadComplete(SP, 4);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Dynamic 4 threads Total used time is: "+totalTime + " millisecond");
			System.out.println();
		}
		else if(choice == 5){
			sc.close();
			System.exit(0);
		}
		else{
			System.out.println("invalid input");
			System.out.println();
		}	
	}
	
	public static void ThreadComplete(ShortestPath SP, int choice){
		// static threads
		if(choice == 1 || choice == 2){
			while (Thread.activeCount() > 1) {

			}
		}
		// this section is for dynamic thread only
		else if(choice == 3){
			while(SP.getM_WaitingCount() < 2) {				
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			SP.setM_Thread_Stop_Condition(true);
			// wake all the thread up they will see that
			// the queue is empty and no longer call split
			// terminate eventually.
			SP.WakeOneThreadUp();
		}
		else if(choice == 4){
			while(SP.getM_WaitingCount() < 4) {							
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			SP.setM_Thread_Stop_Condition(true);
			// wake all the thread up they will see that
			// the queue is empty and no longer call split
			// terminate eventually.
			SP.WakeOneThreadUp();
		}

		// check to see if all thread is finished then out put
		System.out.println();
		System.out.println("PROGRAM ENDED");
		System.out.println("smallest cost is: "+ SP.getSmallestCost());
		System.out.println("smallest cost path is: "+SP.getM_Path());
		System.out.println();
	}
}
