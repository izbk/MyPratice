package my.file.pdf;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Binke Zhang
 * @date 2020/4/1 12:26
 */
public class PdfMerge {

     public static void main(String[] args) throws Exception {
         String sourceDir = "D:\\360安全浏览器下载\\highlights";
         String destPath = sourceDir + "\\AllInOne.pdf";
         merge(sourceDir,destPath);
     }

    private static void merge(String sourceDir,String destPath) throws IOException {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        String[] filesInFolder = getFiles(sourceDir);
        Arrays.sort(filesInFolder);
        for (int i = 0; i < filesInFolder.length; i++){
            if(filesInFolder[i].endsWith(".pdf")){
                mergePdf.addSource(sourceDir + File.separator + filesInFolder[i]);
            }
        }
        mergePdf.setDestinationFileName(destPath);
        mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        System.out.print("合并完成");
    }

    private static String[] getFiles(String folder) throws IOException {
        File _folder = new File(folder);
        String[] filesInFolder;

        if (_folder.isDirectory()) {
            filesInFolder = _folder.list();
            return filesInFolder;
        } else {
            throw new IOException("路径不对");
        }
    }

}
