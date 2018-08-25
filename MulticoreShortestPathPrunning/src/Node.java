import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Vector;


public class Node  implements Serializable{
	// smallest cost of the node
	private String m_name;
	private double m_value;
	private String m_path = null;
	private boolean m_completecycle = false;
	
	// array of edges that can be include.
	private Vector<String> m_edges = new Vector<String>();
	private int [] [] m_value_matric;
	private int value_matric_row;
	private int value_matric_col;
	private int maxExcludedCount = 0;
	
	// 0 is not visited, 1 is included, -1 is excluded.

	private int [] [] ie_matric;
	
	// the constructor take in the graph and the array of remaining edges for choosing.
	Node (int [] [] valueMatric, int r, int c, Vector<String> e, int [] [] ie, String n) {
		// set the value matric
		m_name = n;
		setM_value_matric(valueMatric);
		value_matric_row = r;
		value_matric_col = c;
		// set the edges array
		setM_edges(e);
		setIe_matric(ie);
		maxExcludedCount = 2-(value_matric_col-1);
	}
	/*
	 * this function calculate the cost of the node, by traversing the
	 * include/exclude matrix, if no edge included then we add all undecided
	 * edge cost (marked 0 edge) to the priority queue and pop the first 2.
	 * 
	 * if 1 edge included, we search for the first edge that is marked 1 
	 * get it cost and add all edge mark 0 to the priority queue. Pop the queue
	 * one time and add the values to total.
	 * 
	 * if 2 included edge we search for both and add the values to total.
	 * 
	 * This function make use of 2 matrix, the include/exclude matrix and
	 * the value matrix. 
	 * 
	 * INPUT: none
	 * OUTPUT: the node m_value is updated.
	 */
	public void calculate (){
		double total = 0.0;
		String s1;
		PriorityQueue pq1 = new PriorityQueue();
		//System.out.println("this is calculate function displaying node matric");
		//PrintMatrix();
		
		// loop through all the row (for each row)
		for (int i = 0; i < value_matric_row; i++){			
			// case 1 no include edge 
			if (getIe_matric()[i][value_matric_col] == 0){
				// get 2 smallest edge cost and add to total
				for(int j = 0; j < value_matric_col; j++){
					// this condition use to avoid the diagonal
					if(getIe_matric()[i][j] == 0){
						pq1.add(getM_value_matric()[i][j]);
					}
				}
				
				int temp = (int) pq1.remove();
				total = total + temp;
				temp = (int) pq1.remove();
				total = total + temp;
				// clear the que.
				pq1.clear();
			}
			// case 2 we have 1 included edge
			else if(getIe_matric()[i][value_matric_col] == 1){
				// get the included edge cost and add to total
				for(int j = 0; j < value_matric_col; j++){
					// 1 is included
					if(getIe_matric()[i][j] == 1){
						total = total + getM_value_matric()[i][j];
					}
					// 0 is neither included or excluded.
					// use to avoid diagonal 
					else if(getIe_matric()[i][j] == 0){
						pq1.add(getM_value_matric()[i][j]);
					}
				}
				int temp = (int) pq1.remove();
				total = total + temp;
				// clear the queue
				pq1.clear();
			}
			// case 3 we have 2 included edge
			else if(getIe_matric()[i][value_matric_col] == 2){
				// get the included edges cost and add to total
				for(int j = 0; j < value_matric_col; j++){
					// 1 is included
					if(getIe_matric()[i][j] == 1){
						total = total + getM_value_matric()[i][j];
					}
				}
			}
		}
		m_value = total/2;
	}
	
	// when called the function will traverse the current iematric
	// get an edge that is included then from that edge traverse the
	// possible path. If a path with length 5 is found
	// the path will be saved and the completecycle will be set to true.
	public void isCompleteCycle (){
		// save the iematric
	    int [][] temp1 = new int[getValue_matric_row()+2][getValue_matric_col()+2];
	    for (int i = 0; i < getIe_matric().length; i++) {
	    	for(int j = 0; j < getIe_matric()[i].length;j++){
	    		temp1[i][j] = getIe_matric()[i][j];
		    }
	    }
		String des = "0";
		String sor = "0";
		// traverse the iematric and get a starting edge
		for(int i = 0; i < value_matric_row; i++){
			for(int j = 0; j < value_matric_col; j++){
				if(getIe_matric()[i][j] == 1){
					des = ""+i;
					sor = ""+j;
					break;
				}
			}
		}
		// if the iematric is all 0 
		if(Integer.parseInt(sor) == Integer.parseInt(des)){
			return;
		}
		
		int edgecount = 1;
		int src = Integer.parseInt(sor);
		String path = "";
		path = path +des+sor;
		
		for(int i = 0; i < value_matric_col; i++){
			// found complete cycle
			// the number of col in value matric is the number of edge
			// for a complete cycle
			if(src == Integer.parseInt(des) && edgecount == value_matric_col){
				setM_completecycle(true);
				setM_path(path);
			}
			if(getIe_matric()[src][i] == 1){
				getIe_matric()[src][i] = 0;
				getIe_matric()[i][src] = 0;
				src = i;
				i = -1;
				edgecount++;
				path = path+src;
			}
		}
		// reverse the matrix back to what it was.
	    for (int r = 0; r < temp1.length; r++) {
	    	for(int t = 0; t < temp1[r].length; t++){
	    		getIe_matric()[r][t] = temp1[r][t];
		    }
	    }
	}
	
	// return true for premature and false for non-cycle
	// this check when we add an edge to see if it make a premature cycle
	public boolean isPrematureCycle (String des, String sor){
		int edgecount = 1;
		int src = Integer.parseInt(sor);
		
		// save the iematric
	    int [][] temp1 = new int[getValue_matric_row()+2][getValue_matric_col()+2];
	    for (int i = 0; i < getIe_matric().length; i++) {
	    	for(int j = 0; j < getIe_matric()[i].length;j++){
	    		temp1[i][j] = getIe_matric()[i][j];
		    }
	    }
	    
		for(int i = 0; i < value_matric_col; i++){
			// found premature cycle
			// the number of col in value matric is the number of edge
			// for a complete cycle
			if(src == Integer.parseInt(des) && edgecount < value_matric_col){
				// reverse the matric back to what it was.
			    for (int r = 0; r < temp1.length; r++) {
			    	for(int t = 0; t < temp1[r].length; t++){
			    		getIe_matric()[r][t] = temp1[r][t];
				    }
			    }
				return true;
			}
			if(getIe_matric()[src][i] == 1){
				getIe_matric()[src][i] = 0;
				getIe_matric()[i][src] = 0;
				src = i;
				i = -1;
				edgecount++;
			}
		}
		// found no premature cycle
		// reverse the matric back to what it was.
	    for (int r = 0; r < temp1.length; r++) {
	    	for(int t = 0; t < temp1[r].length; t++){
	    		getIe_matric()[r][t] = temp1[r][t];
		    }
	    }
		return false;
	}
	
	// this function take in a string that represent a vertices
	// wipe out all edge in vector of edges that contain the
	// targeted vertices.
	public void removeEdges (String target){
		String temp;
		for (int i = 0; i < getM_edges().size(); i++){
			if(m_edges.elementAt(i).contains(target)){
				// save the edge to temp
				temp = m_edges.elementAt(i);
				// remove the edge from vector of edges
				m_edges.remove(i);
				// reset i 
				i = -1;
			}
		}
	}
	// print the matrix of this node
	public void PrintMatrix (){
		for(int x = 0; x < getIe_matric().length;x++){
			for(int j = 0; j < getIe_matric()[x].length;j++){
				System.out.format("%3d", getIe_matric()[x][j]);
			}
	    	System.out.println();
	    }
	}
	// fill the matrix run in pair with IsMaxIncludeFillOut
	// to fill out the matrix base on the current 1/0/-1 information
	// in the matrix.
	public boolean IsMaxExcludeFillOut (int x){
		if(getIe_matric()[x][value_matric_col+1] == maxExcludedCount){
			// if we get here we have maxed the exclude 
			// the rest that is undecide (0) will be set to 1.
			// set both xv and vx to 1 and increase both row x and row v
			// include count by 1
			for(int v = 0; v < value_matric_col; v++){
				if(getIe_matric()[x][v] == 0){
					getIe_matric()[x][v] = 1;
					getIe_matric()[v][x] = 1;
					getIe_matric()[x][value_matric_col]++;
					getIe_matric()[v][value_matric_col]++;
					// because i increase v row by 1
					// now i need to check for max include == 2
					IsMaxIncludeFillOut(v);
				}
			}
			// remove all edge with x (des) in it.
			removeEdges(x+"");
		}
		if(CheckFillOutMatrix() == false){
			return false;
		}
		return true;
	}
	// fill the matrix run in pair with IsMaxIncludeFillOut
	// to fill out the matrix base on the current 1/0/-1 information
	// in the matrix.
	public boolean IsMaxIncludeFillOut (int y){
		if(getIe_matric()[y][value_matric_col] == 2){
			// if we get here we have maxed the include for the row
			// the rest that is undecide (0) will be set to -1.
			// set both yu and uy to -1 and decrease both row x and row v
			// exclude count by 1
			for(int u = 0; u < value_matric_col; u++){
				if(getIe_matric()[y][u] == 0){
					getIe_matric()[y][u] = -1;
					getIe_matric()[u][y] = -1;
					getIe_matric()[y][value_matric_col+1]--;
					getIe_matric()[u][value_matric_col+1]--;
					// because we decrease u row by 1
					// now i need to check for max exclude
					IsMaxExcludeFillOut(u);					
				}
			}
			// remove all edge with x (des) in it.
			removeEdges(y+"");
		}
		if(CheckFillOutMatrix() == false){
			return false;
		}
		return true;
	}
	
	/*
	 * This function loop through the matrix include and exclude
	 * column and check for any include value >2 
	 * and any exclude value < maxExcludedCount
	 * if found That mean the Maxincluded and Maxexcluded Fill out
	 * function fail to generate a positive cycle.
	 * NOTE this functino is extra consideration for instant in which
	 * the 2 fill function select edge that lead to premature cycle
	 * and lead to cutting off vertices. which is the same as
	 * not having a complete cycle. However, This can only happen when
	 * the 2 fill function hit a certain fill in size. So the issue can not
	 * be check by complete cycle function.
	 * INPUT: none
	 * OUTPUT: True for no value > 2 and < maxexcluded
	 * 			false if one value > 2 or < max excluded is found.
	 */
	public boolean CheckFillOutMatrix (){
		for(int i = 0; i < value_matric_row; i++){
			if(getIe_matric()[i][value_matric_col] > 2){
				return false;
			}
			else if(getIe_matric()[i][value_matric_col+1] < maxExcludedCount){
				return false;
			}
	    }
		return true;
	}
	
	public double getM_value() {
		return m_value;
	}

	public void setM_value(double m_value) {
		this.m_value = m_value;
	}

	public int getValue_matric_row() {
		return value_matric_row;
	}

	public void setValue_matric_row(int value_matric_row) {
		this.value_matric_row = value_matric_row;
	}

	public int getValue_matric_col() {
		return value_matric_col;
	}

	public void setValue_matric_col(int value_matric_col) {
		this.value_matric_col = value_matric_col;
	}

	public Vector<String> getM_edges() {
		return m_edges;
	}

	public void setM_edges(Vector<String> e) {
		for (int i = 0; i < e.size(); i++) {
			m_edges.addElement(e.get(i));
		}
	}

	public int [] [] getIe_matric() {
		return ie_matric;
	}

	public void setIe_matric(int [] [] ie) {
		int [] [] iematric = new int[getValue_matric_row()+2][getValue_matric_col()+2];
	    for (int i = 0; i < ie.length; i++) {
	    	for(int j = 0; j < ie[i].length;j++){
	    		iematric[i][j] = ie[i][j];
		    }
	    }
	    ie_matric = iematric;
	}

	public String getM_path() {
		return m_path;
	}

	public void setM_path(String m_path) {
		this.m_path = m_path;
	}

	public boolean isM_completecycle() {
		return m_completecycle;
	}

	public void setM_completecycle(boolean completecycle) {
		this.m_completecycle = completecycle;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public int [] [] getM_value_matric() {
		return m_value_matric;
	}
	public void setM_value_matric(int [] [] m_value_matric) {
		this.m_value_matric = m_value_matric;
	}
	
}
