package my.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkClient_DataChange {

	public static void main(String[] args) throws InterruptedException {

		String path = "/zk-book";
		ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
		zkClient.subscribeDataChanges(path, new IZkDataListener() {

			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("Node " + dataPath + " deleted.");
			}

			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("Node " + dataPath + " changed, new data: " + data);
			}
		});
		// zkClient.deleteRecursive(path);
		zkClient.createPersistent(path);
		zkClient.writeData(path, "456");
		System.out.println(zkClient.readData(path).toString());
		zkClient.writeData(path, "4567");
		Thread.sleep(1000);
		zkClient.deleteRecursive(path);
		

	}
}
