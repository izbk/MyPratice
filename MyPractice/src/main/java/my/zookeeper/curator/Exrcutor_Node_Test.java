package my.zookeeper.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Exrcutor_Node_Test {

	static String path = "/zk-book";

	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
			.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
	static CountDownLatch semaphore = new CountDownLatch(2);
	static ExecutorService tp = Executors.newFixedThreadPool(2);

	public static void main(String[] args) throws Exception {
		client.start();

		// created
		System.out.println("Main thread: " + Thread.currentThread().getName());
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {

			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println("event[code: " + event.getResultCode() + ", type: " + event.getType() + "]");
				System.out.println("Thread of processResult: " + Thread.currentThread().getName());
				semaphore.countDown();
			}
		}, tp).forPath(path);

		// 此处没有传入自定义的Executor
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println("event[code: " + event.getResultCode() + ", type: " + event.getType() + "]");
				System.out.println("Thread of processResult: " + Thread.currentThread().getName());
				semaphore.countDown();
			}
		}).forPath(path, "init".getBytes());

		semaphore.await();
		tp.shutdown();
	}

}
