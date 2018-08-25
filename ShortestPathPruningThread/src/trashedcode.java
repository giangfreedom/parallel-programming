
public class trashedcode {
	// this check after an edge is added and the graph is auto 
	// populated by the double recursive function do we get
	// a premature cycle
	/*
	public boolean isPrematureCycle (){
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
			return false;
		}		
		int edgecount = 1;
		int src = Integer.parseInt(sor);
		String path = "";
		path = path +des+sor;
		
		for(int i = 0; i < value_matric_col; i++){
			// found complete cycle
			// the number of col in value matric is the number of edge
			// for a complete cycle
			if(src == Integer.parseInt(des) && edgecount < value_matric_col){
				return true;
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
	    return false;
	}
	//========================================================================
	
	if(c2.isPrematureCycle()){
		System.out.println("premature cycle detected after added an edge in c2");
	}
	else{
		// calculate c2 cost
		c2.calculate();
		// check for complete cycle
		c2.isCompleteCycle();
		ChildArray.add(c2);

		System.out.println("c2 (Created Node) cost: "+ c2.getM_value());
		System.out.println("c2 (remaining edges) : ");
		for (int i = 0; i < c2.getM_edges().size(); i++){
			System.out.print(c2.getM_edges().get(i)+", ");
		}
		System.out.println();
		System.out.println();
		
	   	counter++;	
	}
	//=============================================================================
	if(c1.isPrematureCycle()){
		System.out.println("premature cycle detected after added an edge in c1");
	}
	else{
		// calculate c1 cost
		c1.calculate();
		// check if c1 contain a complete cycle
		c1.isCompleteCycle();
		
		ChildArray.add(c1);

		System.out.println("c1 (Created Node) cost: "+ c1.getM_value());
		System.out.println("c1 (remaining edges): ");
		for (int i = 0; i < c1.getM_edges().size(); i++){
			System.out.print(c1.getM_edges().get(i)+", ");
		}
		System.out.println();
		System.out.println();
		
		counter++;
	}
	*/
}
