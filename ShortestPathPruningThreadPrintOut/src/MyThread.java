
public class MyThread extends Thread {
	private ShortestPath m_SP;
	
	MyThread(ShortestPath SP){
		m_SP = SP;
	}
	
    public void run(){
       //m_SP.NodeSplit();
    	m_SP.ShortestPathSearch();
    }
 }
