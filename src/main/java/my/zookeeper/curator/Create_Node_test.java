package my.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Create_Node_test {

	private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

	private static CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
			.sessionTimeoutMs(5000).retryPolicy(retryPolicy).build();

	private final static String PATH_STRING = "/zk-book/c1";

	public static void main(String[] args) throws Exception {
		curatorFramework.start();
		curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH_STRING);
	}
}
