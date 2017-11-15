package my.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class Delete_Node_Test {

	private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

	private static CuratorFramework framework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
			.sessionTimeoutMs(5000).retryPolicy(retryPolicy).build();

	public static void main(String[] args) throws Exception {
		framework.start();

		String path = "/zk-book/c1";

		/**
		 * 保护措施，只要客户端会话有效，那么Curator后台持续进行删除操作，直到删除成功为止。
		 */
		// framework.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);

		Stat stat = new Stat();

		framework.getData().storingStatIn(stat).forPath(path);

		System.out.println(stat.toString());

		framework.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);

	}
}
