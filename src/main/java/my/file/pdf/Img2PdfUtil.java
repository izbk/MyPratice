package my.file.pdf;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author Binke Zhang
 * @date 2020/3/18 9:50
 */
@Slf4j
public class Img2PdfUtil {
    private static final int PAGEW = 600;
    private static final int PAGEH = 850;
    private static final int M = 10;

    /**
     *
     * @param outPdfFilepath 生成pdf文件路径
     * @param filePath 图片路径
     */
    public static void imagesToPdf(String outPdfFilepath, String filePath,int size) throws Exception {

        log.info("进入图片合成PDF工具方法");
        File file = new File(outPdfFilepath);
        // 第一步：创建一个document对象。
        Document document = new Document();
        document.setMargins(0, 0, 0, 0);
        // 第二步：
        // 创建一个PdfWriter实例，
        PdfWriter.getInstance(document, new FileOutputStream(file));
        // 第三步：打开文档。
        document.open();
        // 第四步：在文档中增加图片。
        List<File> files = FileUtil.loopFiles(filePath);
        List<File> sortedList = files;
//        List<File> sortedList = files.stream().sorted(Comparator.comparing(f->Integer.valueOf(f.getName().replaceAll("\\D","")))).collect(Collectors.toList());
        int len = sortedList.size();
        for (int i = 0; i < len; i++) {
            File image = sortedList.get(i);
            if (image.getName().toLowerCase().endsWith(".bmp")
                    ||image.getName().toLowerCase().endsWith(".jpg")
                    || image.getName().toLowerCase().endsWith(".jpeg")
                    || image.getName().toLowerCase().endsWith(".gif")
                    || image.getName().toLowerCase().endsWith(".png")) {
                String temp = image.getAbsolutePath();
                log.info("图片路径："+temp);
                Image img = Image.getInstance(temp);
                // 设置每页图片个数为size时，每张图片的尺寸
                int iw = 0;
                int ih = 0;
                switch(size){
                    case 1:
                        iw = PAGEW-2*M;
                        ih = PAGEH-2*M;
                        break;
                    case 2:
                        iw = PAGEW-2*M;
                        ih = (PAGEH-2*M)/2;
                        break;
                    case 4:
                        iw = (PAGEW-2*M)/2;
                        ih = (PAGEH-2*M)/2;
                        break;
                    case 6:
                        iw = (PAGEW-3*M)/3;
                        ih = (PAGEH-2*M)/2;
                        break;
                    case 9:
                        iw = (PAGEW-3*M)/3;
                        ih = (PAGEH-3*M)/3;
                        break;
                    default:
                }
                img.scaleAbsolute(iw, ih);
                int oss = i%size;
                if(oss < size){
                    // 设置每页图片的位置
                    switch(oss){
                        case 0:
                            img.setAbsolutePosition(M,M);
                            break;
                        case 1:
                            img.setAbsolutePosition(iw + 2*M, M);
                            break;
                        case 2:
                            img.setAbsolutePosition(M, ih+2*M);
                            break;
                        case 3:
                            img.setAbsolutePosition(iw + 2*M, ih +2*M);
                            break;
                        default:
                    }
                    document.add(img);
                    if(oss+1 != size){
                        continue;
                    }
                }
                document.setPageSize(new Rectangle(PAGEW, PAGEH));
                document.newPage();
            }
        }
        // 第五步：关闭文档。
        document.close();
        log.info("图片合成PDF完成");
    }

    public static void main(String[] args) throws Exception {
        String outPdfPath = "C:\\Users\\F\\Desktop\\22\\allInOne.pdf";
        String imagesPath = "C:\\Users\\F\\Desktop\\22";
        imagesToPdf(outPdfPath, imagesPath,1);
    }
}
