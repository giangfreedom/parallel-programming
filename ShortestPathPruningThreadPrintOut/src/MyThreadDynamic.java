
public class MyThreadDynamic extends Thread {
	private ShortestPath m_SP;
	//private int m_Count;
	
	MyThreadDynamic(ShortestPath SP){
		m_SP = SP;
		//m_Count = c;
	}
	
    public void run(){
    	// while the share queue is nnot empty
    	// pick up a node from the share queue
    	// split it and add the child into the
    	// share queue.
    	while((!m_SP.IsQueueEmpty()) || (m_SP.isM_Thread_Stop_Condition() == false)){
    		m_SP.NodeSplit();
    	}
    	//System.out.println("Dynamic thread terminated successfully");
    }
 }