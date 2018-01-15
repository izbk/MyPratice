package my.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * createPersistent 递归创建父节点
 * @author Tony
 *
 */
public class Zkclient_Create {

	public static void main(String[] args) {
		ZkClient zkClient=new ZkClient("127.0.0.1:2181", 5000);
		String path="/zk-book/c1";
		zkClient.createPersistent(path, true);
	}
}
