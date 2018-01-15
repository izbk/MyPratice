package my.netty.util;

import java.io.File;

public class ChangeName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getFileName();
	}

	/*
	 * 文件重命名
	 */
	public static boolean renameFile(String file, String toFile) {

		File toBeRenamed = new File(file);
		// 检查要重命名的文件是否存在，是否是文件
		if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {

			System.out.println("文件不存在: " + file);
			return false;
		}

		File newFile = new File(toFile);

		// 修改文件名
		if (toBeRenamed.renameTo(newFile)) {
			System.out.println("重命名成功.");
			return true;
		} else {
			System.out.println("重命名失败");
			return false;
		}

	}

	/*
	 * 文件夹下文件所有文件展示
	 */
	public static void getFileName() {
		String path = "D:/CmsListBitmap5/"; // 路径
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " 不存在");
			return;
		}

		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [目录]");
			} else {
				String nameString = fs.getName();
				if (nameString.indexOf("B0") > -1) {
					// 部分文件名修改
					nameString = nameString.replaceAll("B0", "Ba");
					if (renameFile(path + fs.getName(), path + nameString)) {
						System.out.println(fs.getName() + "  重命名为 ： " + nameString);
					}
				}
			}
		}
	}
}
