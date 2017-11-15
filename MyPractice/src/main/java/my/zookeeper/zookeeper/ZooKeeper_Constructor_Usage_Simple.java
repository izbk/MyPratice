package my.zookeeper.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;


public class ZooKeeper_Constructor_Usage_Simple implements Watcher {

	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	
	public static void main(String[] args) {
		try {
			ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181", 500, new ZooKeeper_Constructor_Usage_Simple());
		    System.out.println(zooKeeper.getState());
		    countDownLatch.await();
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
