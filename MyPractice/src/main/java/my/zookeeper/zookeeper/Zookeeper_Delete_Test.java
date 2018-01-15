package my.zookeeper.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Zookeeper_Delete_Test implements Watcher {


	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk;

	public static void main(String[] args) throws Exception {

		String path = "/zk-book";
		zk = new ZooKeeper("127.0.0.1:2181", 5000, //
				new Zookeeper_Delete_Test());
		connectedSemaphore.await();

		//zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE,
			//	CreateMode.EPHEMERAL);
		zk.delete(path, -1);

		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
			if (EventType.None == event.getType() && null == event.getPath()) {
				connectedSemaphore.countDown();
			}
		}
	}

}
