package my.zookeeper.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 * // ZooKeeper API 获取子节点列表，使用同步(sync)接口。
 *
 */
public class Zookeeper_Exist_Test implements Watcher {

	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
	
    
    public static void main(String[] args) throws Exception {
    	String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 
				5000, //
				new Zookeeper_Exist_Test());
        connectedSemaphore.await();  //awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
        
        zk.exists(path, true);
        
        
        zk.create(path, "".getBytes(),  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        
        zk.setData(path, "123".getBytes(), -1);
        
        zk.create(path+"/c1", "".getBytes(),  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        
        zk.setData(path+"/c1", "23454235".getBytes(),-1);
        
        zk.delete(path+"/c1", -1);
        
        zk.delete(path, -1);
        
        Thread.sleep( Integer.MAX_VALUE );
	}
	    
	
	@SuppressWarnings("unused")
	@Override
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
	        if (EventType.None == event.getType() && null == event.getPath()) {
	           connectedSemaphore.countDown();
	        }
	        else if(event.getType() == EventType.NodeCreated){
	        	System.out.println("node =" +event.getPath() +" create ");
	        	 Stat stat;
				try {
					stat = zk.exists(event.getPath(), true);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }else if(event.getType() == EventType.NodeDeleted){
	        	System.out.println("node =" +event.getPath() +" delete ");
	            try {
					zk.exists(event.getPath(), true);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else if (event.getType() == EventType.NodeChildrenChanged) {
	        	System.out.println("node =" +event.getPath() +" childre chaange ");
	        	 Stat stat;
				try {
					stat = zk.exists(event.getPath(), true);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else if (event.getType() == EventType.NodeDataChanged) {
	        	System.out.println("node =" +event.getPath() +" NodeData Changed ");
	        	 Stat stat;
				try {
					stat = zk.exists(event.getPath(), true);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	      }
	}

}
