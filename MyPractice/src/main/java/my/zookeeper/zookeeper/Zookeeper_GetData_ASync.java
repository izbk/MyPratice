package my.zookeeper.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper API 获取节点数据内容，使用异步(async)接口
 * @author Tony
 *
 */
public class Zookeeper_GetData_ASync implements Watcher {

	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
	
    public static void main(String[] args) throws Exception {
        
    	String path = "/zk-book";
    	zk = new ZooKeeper("127.0.0.1:2181", 
				5000, //
				new Zookeeper_GetData_ASync());
    	 connectedSemaphore.await();
    	 
    	 zk.create( path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL );
         
    	 zk.getData(path, true, new IDataCallback(), null );
    	 
    	 zk.setData( path, "123".getBytes(), -1 );
    	 
    	 zk.delete(path, -1);
         
         Thread.sleep( Integer.MAX_VALUE );
	}

	@Override
	public void process(WatchedEvent event) {
		 if (KeeperState.SyncConnected == event.getState()) {
			  if (EventType.None == event.getType() && null == event.getPath()) {
				  connectedSemaphore.countDown();
			  }else if(EventType.NodeDataChanged==event.getType()){
				  try {
		  	        	zk.getData( event.getPath(), true, new IDataCallback(), null );
		  	          } catch (Exception e) {}
			  }
		}
	}

}

/**
 * 异步获取节点数据
 * @author Tony
 *
 */
class IDataCallback implements DataCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, byte[] data,
			Stat stat) {
		  System.out.println(rc + ", " + path + ", " + new String(data));
	        System.out.println(stat.getCzxid()+","+
	                  		   stat.getMzxid()+","+
			                   stat.getVersion());
	}
	
}
