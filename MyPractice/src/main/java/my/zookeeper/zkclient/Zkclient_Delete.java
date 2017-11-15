package my.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * deleteRecursive 递归删除节点
 * @author Tony
 *
 */
public class Zkclient_Delete {

	public static void main(String[] args) {
		ZkClient zkClient=new ZkClient("127.0.0.1:2181", 5000);
		String path="/queue";
		zkClient.deleteRecursive(path);
	}
}
