package my.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class Zkclient_Simple {

	
	public static void main(String[] args) {
		new ZkClient("127.0.0.1:2181", 5000);
		System.out.println("zkclient session established ");
	}
}
