package my.file.oper;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.List;

/**
 * @author zbk
 * @date 2020/4/30 9:42
 */
public class FileHandleUtils {
    public static void main(String[] args){
        renameSuffix("D:\\BaiduNetdiskDownload",".mp4v",".mp4");
    }

    /**
     * 批量替换后缀名
     * @param path
     */
    public static void renameSuffix(String path,String oldSuffix,String newSuffix) {
        List<File> fileList = FileUtil.loopFiles(path);
        fileList.forEach(f->{
            FileUtil.rename(f,f.getName().replaceAll(oldSuffix,newSuffix),false,false);
        });
    }
}
