package my.zookeeper.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 同步接口
 */
public class ZooKeeper_Create_ASync implements Watcher {

	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) {
		try {
			ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 500, new ZooKeeper_Create_ASync());
			System.out.println(zooKeeper.getState());
			countDownLatch.await();

			// 临时节点
			String path = zooKeeper.create("/zk-book", "xxoo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			System.out.println("success create znode:" + path);

			// 临时顺序节点
			String pat2h = zooKeeper.create("/zk-book", "xxoo".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			System.out.println("success create znode:" + pat2h);

			/*
			 * ---------------receive watch event :WatchedEvent
			 * state:SyncConnected type:None path:null success create
			 * znode:/zk-test-1 success create znode:/zk-test-10000000002
			 */
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("---------------receive watch event :" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			countDownLatch.countDown();
		}
	}

}
