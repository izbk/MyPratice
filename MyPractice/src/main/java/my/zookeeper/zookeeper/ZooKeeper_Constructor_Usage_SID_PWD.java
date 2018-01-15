package my.zookeeper.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * Zookeeper 构造方式允许SessionID和SessionPwd，目的是为了复用会话，以维持会话的有效性。
 * 
 */
public class ZooKeeper_Constructor_Usage_SID_PWD implements Watcher {

	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	
	public static void main(String[] args) {
		try {
			ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181", 500, new ZooKeeper_Constructor_Usage_SID_PWD());
		    System.out.println(zooKeeper.getState());
		    countDownLatch.await();
		    
		    long sessionId=zooKeeper.getSessionId();
		    byte[]pwd=zooKeeper.getSessionPasswd();
		    
		    //使用错误的SessionId和Pwd创建Zookeeper创建实例会失败
		    zooKeeper=new ZooKeeper("127.0.0.1:2181", 500, new ZooKeeper_Constructor_Usage_SID_PWD(),1l,"test".getBytes());
		    
		    
		    zooKeeper=new ZooKeeper("127.0.0.1:2181", 500, new ZooKeeper_Constructor_Usage_SID_PWD(),sessionId,pwd);
		    
		    Thread.sleep(Integer.MAX_VALUE);
		    
		    System.out.println("Zookeeper session established.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watch event :"+event);
		if(KeeperState.SyncConnected== event.getState()){
			countDownLatch.countDown();
		}
	}

}
