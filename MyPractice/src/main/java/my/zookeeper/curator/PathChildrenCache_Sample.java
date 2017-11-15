package my.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class PathChildrenCache_Sample {
	static String path = "/zk-book";
	static CuratorFramework client = CuratorFrameworkFactory.builder()
			.connectString("127.0.0.1:2181")
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.sessionTimeoutMs(5000).build();

	public static void main(String[] args) throws Exception {
		client.start();
		PathChildrenCache cache = new PathChildrenCache(client, path, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			public void childEvent(CuratorFramework client,
					PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
				
				case INITIALIZED:
					System.out.println("INITIALIZED,"
							+ event.toString());
					break;
				case CONNECTION_LOST:
					System.out.println("CONNECTION_LOST,"
							+ event.getData().getPath());
					break;
				case CONNECTION_RECONNECTED:
					System.out.println("CONNECTION_RECONNECTED,"
							+ event.getData().getPath());
					break;
				case CONNECTION_SUSPENDED:
					System.out.println("CONNECTION_RECONNECTED,"
							+ event.getData().getPath());
					break;
				case CHILD_ADDED:
					System.out.println("CHILD_ADDED,"
							+ event.getData().getPath());
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATED,"
							+ event.getData().getPath());
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED,"
							+ event.getData().getPath());
					break;
				default:
					break;
				}
			}
		});
		client.create().withMode(CreateMode.PERSISTENT).forPath(path);
		Thread.sleep(1000);
		client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
		Thread.sleep(1000);
		client.delete().forPath(path + "/c1");
		Thread.sleep(1000);
		client.delete().forPath(path);
		Thread.sleep(10000);
		cache.close();
	}
	
	
}
