package my.zookeeper.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 异步创建节点接口
 */
public class ZooKeeper_Create_Sync implements Watcher {

	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) {
		try {
			ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 500, new ZooKeeper_Create_Sync());
			System.out.println(zooKeeper.getState());
			countDownLatch.await();

			// 临时节点
			zooKeeper.create("/zk-test-1", "xxoo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
					new IStringCallback(), "Hello word");

			zooKeeper.create("/zk-test-1", "xxoo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
					new IStringCallback(), "Hello word");

			zooKeeper.create("/zk-test-1", "xxoo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL,
					new IStringCallback(), "Hello word");

			System.out.println("-----------------");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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

class IStringCallback implements AsyncCallback.StringCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("Create path result: 服务响应码=" + rc + " 节点参数值:" + path + " ctx参数值：" + ctx + " 节点名=" + name);
	}

}