package cc.mrbird.febs.common.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.word.Word07Writer;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.w3c.dom.Node;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DocUtil<T> {
    public static final String BOOKMARK_START_TAG = "w:bookmarkStart";

    public static final String BOOKMARK_END_TAG = "w:bookmarkEnd";

    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
    private  void replaceInTables(T customApplyFirst, List<XWPFTable> tables) {
        for (XWPFTable table : tables) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    replaceInParagraphs(customApplyFirst, paragraphs);
                }
            }
        }

    }
    private  void replaceInParagraphs(T customApplyFirst, List<XWPFParagraph> paragraphList) {

        for (XWPFParagraph xwpfParagraph : paragraphList) {
            CTP ctp = xwpfParagraph.getCTP();
            String fontFamily="宋体";
            int fontSize=12;

            for (int dwI = 0; dwI < ctp.sizeOfBookmarkStartArray(); dwI++) {
                log.info(fontFamily);
                CTBookmark bookmark = ctp.getBookmarkStartArray(dwI);

                XWPFRun run = xwpfParagraph.createRun();
                run.setFontFamily(fontFamily);
                run.setFontSize(fontSize);
                String bookmark_title = bookmark.getName();
                if(bookmark_title.equals("_GoBack"))
                {
                    continue;
                }
                String bookValue = "";
               // log.info(bookmark_title);
                if (bookmark_title.contains("_check")) {//checkbox 选中
                    String[] booksm = bookmark_title.split("_");

                   String markTi=  ReflectUtil.getFieldValue(customApplyFirst,booksm[0]).toString();
                   if(booksm[1].equals(markTi)){
                       handleCheckBox(run);
                   }
                   else{
                       continue;
                   }
                }
               else if (bookmark_title.contains("_underLine")) {//checkbox 选中
                    String[] booksm = bookmark_title.split("_");
                    String markTi=  ReflectUtil.getFieldValue(customApplyFirst,booksm[0]).toString();
                    handleUNderLine(markTi,run);
                }
                else if (bookmark_title.contains("_")) {
                    String[] booksm = bookmark_title.split("_");

                    for (String item : booksm
                    ) {

                        handleStrObject(ReflectUtil.getFieldValue(customApplyFirst,item),run);
                    }
                }
                else {

                        run.setFontSize(fontSize);
                        handleStrObject(ReflectUtil.getFieldValue(customApplyFirst, bookmark_title), run);

                }
              //  run.setText(bookValue);

                Node firstNode = bookmark.getDomNode();
                Node nextNode = firstNode.getNextSibling();


                while (nextNode != null) {
                    // 循环查找结束符
                    String nodeName = nextNode.getNodeName();
                    if (nodeName.equals(BOOKMARK_END_TAG)) {
                        break;
                    }

                    // 删除中间的非结束节点，即删除原书签内容
                    Node delNode = nextNode;
                    nextNode = nextNode.getNextSibling();

                    ctp.getDomNode().removeChild(delNode);
                }

                if (nextNode == null) {
                    // 始终找不到结束标识的，就在书签前面添加
                    ctp.getDomNode().insertBefore(run.getCTR().getDomNode(), firstNode);
                } else {
                    // 找到结束符，将新内容添加到结束符之前，即内容写入bookmark中间
                    ctp.getDomNode().insertBefore(run.getCTR().getDomNode(), nextNode);
                }
            }

        }
    }


    public  void writeDoc1(T customApplyFirst, InputStream inputStream, OutputStream outputStream) throws Exception {
        XWPFDocument document = new XWPFDocument(inputStream).getXWPFDocument();
        List<XWPFParagraph> paragraphList = document.getParagraphs();
       List<XWPFTable> tables= document.getTables();
        replaceInParagraphs(customApplyFirst,paragraphList);
        replaceInTables(customApplyFirst,tables);
        //document.enforceReadonlyProtection();//只读word 20210827
        document.write(outputStream);
        document.close();

    }

    private  void handleCheckBox(XWPFRun run){
       // RichTextString richTextString =new HSSFRichTextString("\u0052");

        // Font font= workbook.createFont();
        run.setFontFamily("Wingdings 2");

        run.setText("\u0052");
    }
    private void handleUNderLine(Object obj,XWPFRun run){
        run.setUnderline(UnderlinePatterns.THICK);
        run.setText(obj.toString());
    }
    private   void handleStrObject(Object obj, XWPFRun run){
        if(obj==null) {
            run.setText("");

        }
        else {
            String[] dataStr = obj.toString().split("#");
            for (int i = 0; i < dataStr.length; i++) {
                run.setText(dataStr[i]);
                if (i < (dataStr.length - 1)) {
                    //实现换行
                    run.addBreak();
                    //实现缩进
                    //run.addTab();
                }

            }
        }
       // return  obj.toString().replace("#","\r");
    }
}
