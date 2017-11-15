package my.zookeeper.zookeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 * // ZooKeeper API 获取子节点列表，使用异步接口。
 *
 */
public class Zookeeper_GetChildren_ASync implements Watcher {

	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
	
    public static void main(String[] args) throws Exception {
    	String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 
				5000, //
				new Zookeeper_GetChildren_ASync());
        connectedSemaphore.await();  //awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
        List<String> childrenList=null;
        childrenList = zk.getChildren(path, true);
        System.out.println(childrenList);
        for (int i = 0; i < childrenList.size(); i++) {
        	String _pString=childrenList.get(i);
        	zk.delete(path+"/"+_pString, -1);
		}
    	zk.delete(path, -1);
        
        /*zk.create(path, "".getBytes(), 
      		  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path+"/c1", "".getBytes(), 
      		  Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        
         zk.getChildren(path, true,new IChildren2Callback(),"Hell word");
        
        zk.create(path+"/c2", "".getBytes(), 
      		  Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);*/
      
        Thread.sleep( Integer.MAX_VALUE );
        
	}
	    
	
	@Override
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
	        if (EventType.None == event.getType() && null == event.getPath()) {
	           connectedSemaphore.countDown();
	        } else if (event.getType() == EventType.NodeChildrenChanged) {
	            try {
	                System.out.println("ReGet Child:"+zk.getChildren(event.getPath(),true));
	            } catch (Exception e) {}
	        }
	      }
	}

}

class IChildren2Callback  implements AsyncCallback.Children2Callback{

	@Override
	public void processResult(int rc, String path, Object ctx,
			List<String> children, Stat stat) {
		System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path
                + ", ctx: " + ctx + ", children list: " + children + ", stat: " + stat);		
	}
	
}
