package cc.mrbird.febs.export.pdf;

import cc.mrbird.febs.xxb.entity.XxbBCheck;
import cc.mrbird.febs.xxb.entity.XxbBCheckD;
import cc.mrbird.febs.xxb.entity.XxbBProjdept;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.RadioCheckField;
import com.ruiyun.jvppeteer.util.StringUtil;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XxbBPdfInfo {
    //    private Color black = new Color(0, 0, 0); // 黑色
//    private Color red = new Color(255, 0, 0); // 红色
//    private Color blue = new Color(0, 0, 255); // 蓝色
    BaseColor black = BaseColor.BLACK;
    BaseColor red = BaseColor.RED;
    BaseColor blue = BaseColor.BLUE;
    private int bold = Font.BOLD; // 粗体
    private int normal = Font.NORMAL; // 正常字体
    private int italic = Font.ITALIC; // 斜体
    private int boldItalic = Font.BOLDITALIC; // 粗斜体
    private float setting = 100; // 首行缩进参数

    public Document createDoc(String filename) throws Exception {
        // 新建document对象
        // 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        return document;
    }

    public Image writeImg(String imgPath) throws Exception {
        Image img = Image.getInstance(imgPath); // 控制图片大小
        img.scaleAbsolute(100, 100);
        return img;
    }

    public boolean checkFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static Paragraph convertParToChinese(String text, int fontsize, int fontStyle, BaseColor color)
            throws Exception {
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(baseFontChinese, fontsize, fontStyle, color);
        Paragraph graph = new Paragraph(text, fontChinese);
        return graph;
    }

    public Chunk convertChunkByChinese(String text, int fontsize, int fontStyle, BaseColor color) throws Exception {
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(baseFontChinese, fontsize, fontStyle, color);
        Chunk chunk = new Chunk(text, fontChinese);
        return chunk;
    }


    public void writeXxbPdf(String fileName, String mergeFileName, List<String> mergeAddPdfList,
                            XxbBCheck check, List<XxbBCheckD> renyList, List<XxbBProjdept> pdList) throws Exception {
        Document document = new Document(PageSize.A4, 20, 20, 30, 30);
        FileOutputStream out = new FileOutputStream(fileName);
        PdfWriter.getInstance(document, out);

        document.open(); // 文档里写入
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        Font fontTitle = new Font(baseFontChinese, 10, normal, black);
        Font fontValue = new Font(baseFontChinese, 9, normal, black);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Float contentHeightStaff = 25f;
        Float faddingSize = 5f;

        Float contentHeight50 = 50f;

        int numColumns = 25;
        int numColumns2 = 25;
        int totalWidth = 520;
        int[] setWids = new int[numColumns];
        PdfPTable table = null;
        PdfPCell cell;

        //region 封面
        int projType = check.getProjectType();
        String titleCover_1 = "华中科技大学同济医学院附属协和医院";
        String titleCover_2 = "新技术新业务申报表";
        String titleCover_3 = projType == 0 ? "（检验检查类）" : projType == 1 ? "（临床类-单科申报版）" : "（临床类-多科联合申报版）";
        String titleCover_4 = "申请科室：";
        String titleCover_5 = "项目名称：";
        String titleCover_6 = "项目级别：";
        String titleCover_7 = "是否为限制类医疗技术：";
        String titleCover_8 = "申请日期：";
        String titleCover_9 = "医务办公室制表";
        String titleCover_10 = "二Ο二Ο年版";
        Font fontCover1 = new Font(baseFontChinese, 18, normal, black);
        Font fontCover2 = new Font(baseFontChinese, 25, bold, black);
        Font fontCover3 = new Font(baseFontChinese, 18, bold, black);

        String valueCover_4 = check.getDeptNew();
        String valueCover_5 = check.getProjectName();
        String valueCover_6 = check.getProjectLevel() == 1 ? "一级" : check.getProjectLevel() == 2 ? "二级" :
                check.getProjectLevel() == 3 ? "三级" : "四级";
        String valueCover_7 = check.getIsxzyljs() == 0 ? "否" : "是";
        String valueCover_8 = dateFormat.format(check.getApplydat());;

        // 1正高级 2副高级以上 3中级以上 4初级以上 5其他
        String lev1 = check.getLev() == 1 ? "√ 正高级": "□正高级";
        String lev2 = check.getLev() == 2 ? "√ 副高级以上": "□副高级以上";
        String lev3 = check.getLev() == 3 ? "√ 中级以上": "□中级以上";
        String lev4 = check.getLev() == 4 ? "√ 初级以上": "□初级以上";
        String lev5 = check.getLev() == 5 ? "√ 其他": "□其他";
        // 1医　　师 2护　　士  3药剂人员 4技术人员 5其他
        String typ1 = check.getTyp() == 1 ? "√ 医　　师": "□医　　师";
        String typ2 = check.getTyp() == 2 ? "√ 护　　士": "□护　　士";
        String typ3 = check.getTyp() == 3 ? "√ 药剂人员": "□药剂人员";
        String typ4 = check.getTyp() == 4 ? "√ 技术人员": "□技术人员";
        String typ5 = check.getTyp() == 5 ? "√ 其他": "□其他";
        document.newPage();
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        //空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(10);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //列一
        //华中科技大学同济医学院附属协和医院
        cell = new PdfPCell(new Phrase(titleCover_1, fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //新技术新业务申报表
        cell = new PdfPCell(new Phrase(titleCover_2, fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //（检验检查类）
        cell = new PdfPCell(new Phrase(titleCover_3, fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(180);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //申请科室
        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(titleCover_4, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
      //  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(valueCover_4, fontCover1));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(1);
        cell.setFixedHeight(40);
        cell.setColspan(11);
        table.addCell(cell);

        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(4);
        table.addCell(cell);

        //项目名称
        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(titleCover_5, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
     //   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(valueCover_5, fontCover1));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(1);
        cell.setFixedHeight(40);
        cell.setColspan(11);
        table.addCell(cell);

        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(4);
        table.addCell(cell);

        //项目级别
        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(titleCover_6, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
      //  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(valueCover_6, fontCover1));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(1);
        cell.setFixedHeight(40);
        cell.setColspan(11);
        table.addCell(cell);

        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(4);
        table.addCell(cell);

        //是否为限制类医疗技术
        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(titleCover_7, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
     //   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(11);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(valueCover_7, fontCover1));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(1);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(4);
        table.addCell(cell);
        //申请日期
        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(titleCover_8, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
     //   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(valueCover_8, fontCover1));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBorderWidthBottom(1);
        cell.setFixedHeight(40);
        cell.setColspan(11);
        table.addCell(cell);

        // 空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setColspan(4);
        table.addCell(cell);

        //空
        cell = new PdfPCell(new Phrase("", fontCover2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(80);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //医务办公室制表
        cell = new PdfPCell(new Phrase(titleCover_9, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        //二Ο二Ο年版
        cell = new PdfPCell(new Phrase(titleCover_10, fontCover3));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(40);
        cell.setColspan(numColumns2);
        table.addCell(cell);

        document.add(table);

        //endregion

        //region 填表说明
        String strKongGe = "   ";
        String strKongGe_1 = "         ";
        String titleExp_1 = "填  表  说  明";
        ArrayList<String> expTitleList = new ArrayList<>();
        String titleExp_2 = strKongGe_1 + "一、申报范围：";
        expTitleList.add(titleExp_2);
        String titleExp_3 = strKongGe_1 + "申报的新技术新业务必须是在我院首次应用于临床，经过临床研究论证且安全性、有效性";
        expTitleList.add(titleExp_3);
        String titleExp_3_1 = "确切的诊断和治疗技术。安全性、有效性不确切的医疗技术，不得申报。";
        expTitleList.add(titleExp_3_1);
        String titleExp_4 = strKongGe_1 + "新技术新项目中，如有涉及使用药品、医疗器械或具有相似属性的相关产品、制剂等的，";
        expTitleList.add(titleExp_4);
        String titleExp_4_1 = "在药品、医疗器械或具有相似属性的相关产品、制剂等未经食品药品监督管理部门批准上市前，";
        expTitleList.add(titleExp_4_1);
        String titleExp_4_2 = "不得申报开展临床应用，应按照临床研究的相关规定执行。";
        expTitleList.add(titleExp_4_2);
        String titleExp_5 = strKongGe_1 + "“临床研究项目不在申报范围之列。";
        expTitleList.add(titleExp_5);
        String titleExp_6 = strKongGe_1 + "二、“项目级别”分为以下四级：";
        expTitleList.add(titleExp_6);
        String titleExp_7 = strKongGe_1 + "一级：是指具有原创性创新、在国际上首先开展的，或领先于国际水平的，代表着本学科";
        expTitleList.add(titleExp_7);
        String titleExp_7_1 = "近年和未来十年发展方向的技术、方法，该技术的开展能够大力促进和带动学科发展；";
        expTitleList.add(titleExp_7_1);
        String titleExp_8 = strKongGe_1 + "二级：指与国际先进水平同步，或紧随开展的项目和技术，或在国内首先开展，领先于国";
        expTitleList.add(titleExp_8);
        String titleExp_8_1 = "内水平的项目和技术。";
        expTitleList.add(titleExp_8_1);
        String titleExp_9 = strKongGe_1 + "三级：是指与国内先进水平同步或紧随开展的项目和技术。";
        expTitleList.add(titleExp_9);
        String titleExp_10 = strKongGe_1 + "四级：是指在省内首先开展，或领先于省内水平的项目和技术，或与省内先进水平同步，";
        expTitleList.add(titleExp_10);
        String titleExp_10_1 = "或紧随开展的项目和技术。";
        expTitleList.add(titleExp_10_1);
        String titleExp_11 = strKongGe_1 + "三、具有下列情形之一的，为限制类技术，申报获批后由医院组织向省卫建委备案：";
        expTitleList.add(titleExp_11);
        String titleExp_12 = strKongGe_1 + "（一）技术难度大、风险高，对医疗机构的服务能力、人员水平有较高专业要求，需要设";
        expTitleList.add(titleExp_12);
        String titleExp_12_1 = "置限定条件的； ";
        expTitleList.add(titleExp_12_1);
        String titleExp_13 = strKongGe_1 + "（二）需要消耗稀缺资源的；";
        expTitleList.add(titleExp_13);
        String titleExp_14 = strKongGe_1 + "（三）涉及重大伦理风险的；";
        expTitleList.add(titleExp_14);
        String titleExp_15 = strKongGe_1 + "（四）存在不合理临床应用，需要重点管理的。";
        expTitleList.add(titleExp_15);
        String titleExp_16 = strKongGe_1 + "四、国家限制临床应用的医疗技术（2015版）目录为： 一、安全性、有效性确切，但是技";
        expTitleList.add(titleExp_16);
        String titleExp_16_1 = "术难度大、风险高，对医疗机构的服务能力和人员技术水平有较高要求，需要限定条件的医疗";
        expTitleList.add(titleExp_16_1);
        String titleExp_16_2 = "技术。如：造血干细胞（包括脐带血造血干细胞）移植治疗血液系统疾病技术，质子、重离子";
        expTitleList.add(titleExp_16_2);
        String titleExp_16_3 = "加速器放射治疗技术，放射性粒子植入治疗技术（包括口腔颌面部恶性肿瘤放射性粒子植入治";
        expTitleList.add(titleExp_16_3);
        String titleExp_16_4 = "疗技术），肿瘤深部热疗和全身热疗技术，肿瘤消融治疗技术，心室辅助装置应用技术，颅颌面";
        expTitleList.add(titleExp_16_4);
        String titleExp_16_5 = "畸形颅面外科矫治术，口腔颌面部肿瘤颅颌联合根治术，人工智能辅助诊断、治疗技术等。二、";
        expTitleList.add(titleExp_16_5);
        String titleExp_16_6 = "存在重大伦理风险或使用稀缺资源，需要严格管理的医疗技术。如：同种胰岛移植治疗糖尿病";
        expTitleList.add(titleExp_16_6);
        String titleExp_16_7 = "技术，同种7异体组织移植治疗技术（仅限于角膜、骨、软骨、皮肤移植治疗技术），性别重置";
        expTitleList.add(titleExp_16_7);
        String titleExp_16_8 = "技术等";
        expTitleList.add(titleExp_16_8);
        String titleExp_17 = strKongGe_1 + "五、“项目类型”分为以下四种类：";
        expTitleList.add(titleExp_17);

        Font fontExpTitle = new Font(baseFontChinese, 15, bold, black);
        Font fontExpContent = new Font(baseFontChinese, 12, normal, black);
        float expPaddingLeft = 8f;
        float expPaddingRight = 8f;
        float contentHeight = 22f;

        document.newPage();
        numColumns = 1;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        cell = new PdfPCell(new Phrase(titleExp_1, fontExpTitle));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(contentHeight50);
        cell.setPaddingBottom(30);
        cell.setColspan(numColumns);
        table.addCell(cell);

        for (String item : expTitleList) {
            cell = new PdfPCell(new Phrase(item, fontExpContent));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPaddingLeft(expPaddingLeft);
            cell.setPaddingRight(expPaddingRight);
            cell.setFixedHeight(contentHeight);
            cell.setColspan(numColumns);
            table.addCell(cell);
        }

        document.add(table);

        document.newPage();
        numColumns = 1;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);
        expTitleList = new ArrayList<>();

        String titleExp2_1 = strKongGe_1 + "1.手术类；  2.治疗操作类；  3.检验检查类；  4.其他类";
        expTitleList.add(titleExp2_1);
        String titleExp2_2 = strKongGe_1 + "六、主要操作人员范围限定是指可执行本项技术的操作人员的最低技术级别与技术类别。";
        expTitleList.add(titleExp2_2);
        String titleExp2_2_1 = "填写时请在旁边“口”内以“√”表示。";
        expTitleList.add(titleExp2_2_1);
        String titleExp2_3 = strKongGe_1 + "七、医疗新技术项目申请的可行性报告包括以下内容（若表格不够，可另附页填写）：";
        expTitleList.add(titleExp2_3);
        String titleExp2_4 = strKongGe_1 + "1.项目主要内容；";
        expTitleList.add(titleExp2_4);
        String titleExp2_5 = strKongGe_1 + "2.项目目的、意义及创新之处；";
        expTitleList.add(titleExp2_5);
        String titleExp2_6 = strKongGe_1 + "3.项目依据：包括国内外研究及应用概况、采用的技术方案、可解决的主要问题、科室已";
        expTitleList.add(titleExp2_6);
        String titleExp2_6_1 = "具备的技术和设备条件及准备情况、实施计划及相关效益分析等；";
        expTitleList.add(titleExp2_6_1);
        String titleExp2_7 = strKongGe_1 + "4.科室实施此新技术制定的相应质控措施、操作规程及相关人员的职责范围；";
        expTitleList.add(titleExp2_7);
        String titleExp2_8 = strKongGe_1 + "5.新技术开展的适应症、禁忌症、可能造成的不良后果和并发症以及相应防范措施；";
        expTitleList.add(titleExp2_8);
        String titleExp2_9 = strKongGe_1 + "6.安全性、有效性佐证材料；";
        expTitleList.add(titleExp2_9);
        String titleExp2_10 = strKongGe_1 + "7.相应的病人知情同意书；";
        expTitleList.add(titleExp2_10);
        String titleExp2_11 = strKongGe_1 + "8.协作科室或单位的技术情况、承担责任及审批意见等。";
        expTitleList.add(titleExp2_11);
        String titleExp2_12 = strKongGe_1 + "八、申报一级、二级水平的新技术新项目还必须提交有资质部门出具的项目查新报告。查";
        expTitleList.add(titleExp2_12);
        String titleExp2_12_1 = "新报告题目应与申报的新技术新项目一致，报告中必须写明国际、国内和省内开展情况。";
        expTitleList.add(titleExp2_12_1);

        String titleExp2_13 = strKongGe_1 + "九、申报的新技术新项目中如有涉及使用新的药品、医疗器械或具有相似属性的相关产品、";
        expTitleList.add(titleExp2_13);
        String titleExp2_13_1 = "制剂等的，必须提交药品、医疗器械或具有相似属性的相关产品、制剂的“三证”，即：厂家生";
        expTitleList.add(titleExp2_13_1);
        String titleExp2_13_2 = "产许可证、厂家经营许可证、国家食品药品监督管理部门批准上市的批文。";
        expTitleList.add(titleExp2_13_2);


        for (String item : expTitleList) {
            cell = new PdfPCell(new Phrase(item, fontExpContent));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPaddingLeft(expPaddingLeft);
            cell.setPaddingRight(expPaddingRight);
            cell.setFixedHeight(contentHeight);
            cell.setColspan(numColumns);
            table.addCell(cell);
        }
        document.add(table);

        //endregion

        // 检验检查类
        if (check.getProjectType() == 0) {
            //region 内容 里面分层次 检验检查类
            document.newPage();
            numColumns = 25;
            table = new PdfPTable(numColumns);
            setWids = new int[numColumns];
            //table总Width宽度
            table.setTotalWidth(totalWidth);
            //设置总Width宽度 生效
            table.setLockedWidth(true);
            //列布局
            for (int i = 0; i < numColumns; i++) {
                setWids[i] = 1;
            }
            table.setWidths(setWids);

            //region 项  目  负  责  人
            String title1_1 = "项  目  负  责  人";
            String title1_2 = "姓名";
            String title1_2_1 = "性别";
            String title1_2_2 = "出生年月";
            String title1_3 = "学历、学位";
            String title1_3_1 = "职  称";
            String title1_3_2 = "职    务";
            String title1_4 = "专    业";
            String title1_4_1 = "专    长";
            String title1_4_2 = "手机号码";

            String value1_2 = check.getUserAccountName();
            String value1_2_1 = check.getSexName();
            String value1_2_2 = check.getBirthday();
            String value1_3 = check.getEdu();
            String value1_3_1 = check.getZhichenglc();
            String value1_3_2 = check.getZhiwu();
            String value1_4 = check.getZhuany();
            String value1_4_1 = check.getZhuanc();
            String value1_4_2 = check.getTelephone();

//        cell.setColspan(4);
//        cell.setRowspan(4);

            //列一
            // 项  目  负  责  人
            cell = new PdfPCell(new Phrase(title1_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(numColumns);
            table.addCell(cell);
            //列二
            //姓名
            cell = new PdfPCell(new Phrase(title1_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);
            //性别
            cell = new PdfPCell(new Phrase(title1_2_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_2_1, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            //出生年月
            cell = new PdfPCell(new Phrase(title1_2_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_2_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            //列三
            //学历、学位
            cell = new PdfPCell(new Phrase(title1_3, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_3, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            //职  称
            cell = new PdfPCell(new Phrase(title1_3_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_3_1, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            //职    务
            cell = new PdfPCell(new Phrase(title1_3_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_3_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);
            //列四
            //专    业
            cell = new PdfPCell(new Phrase(title1_4, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_4, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);
            //专  长
            cell = new PdfPCell(new Phrase(title1_4_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_4_1, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            //手机号码
            cell = new PdfPCell(new Phrase(title1_4_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_4_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            //endregion

            //region 项目主要人员
            String title2_1 = "项目主要人员";
            String title2_2 = "姓名";
            String title2_3 = "性别";
            String title2_4 = "年龄";
            String title2_5 = "学历";
            String title2_6 = "职务职称";
            String title2_7 = "专业方面";

            //列五
            //项目主要人员
            cell = new PdfPCell(new Phrase(title2_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            cell.setRowspan(renyList.size() + 1);
            table.addCell(cell);
            //姓名
            cell = new PdfPCell(new Phrase(title2_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //性别
            cell = new PdfPCell(new Phrase(title2_3, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //年龄
            cell = new PdfPCell(new Phrase(title2_4, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //学历
            cell = new PdfPCell(new Phrase(title2_5, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //职务职称
            cell = new PdfPCell(new Phrase(title2_6, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            //专业方面
            cell = new PdfPCell(new Phrase(title2_7, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(5);
            table.addCell(cell);

            for (XxbBCheckD item : renyList) {
                //姓名
                cell = new PdfPCell(new Phrase(item.getUserAccountName(), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //性别
                cell = new PdfPCell(new Phrase(item.getSexName(), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //年龄
                cell = new PdfPCell(new Phrase(item.getAge(), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //学历
                cell = new PdfPCell(new Phrase(item.getEdu(), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //职务职称
                cell = new PdfPCell(new Phrase(item.getZhichenglc() + "-" + item.getZhiwu(), fontValue));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);

                //专业方面
                cell = new PdfPCell(new Phrase(item.getZhuany() + "-" + item.getZhuanc(), fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(5);
                table.addCell(cell);
            }

            String title3_1 = "项目第一操作者的最低职称限定";
            String title3_2 = "级 别";
            String title3_3 = "类 别";
            String title3_4 = "项目级别";
            String title3_5 = "项目的主要内容及意义";
            String title3_6 = "临床应用安全性分析及佐证资料";
            String title3_7 = "技术临床应用安全性分析及佐证资料";
            String title3_8 = "科室已具备的技术和设备条件";
            String title3_9 = "适应症";
            String title3_10 = "禁忌症";
            String title3_11 = "操作规程";

//            1正高级 2副高级以上 3中级以上 4初级以上 5其他
//            1医　　师 2护　　士  3药剂人员 4技术人员 5其他
            String value3_2 = lev1 + "     " + lev2 +  "     " + lev3 +  "     " + lev4 +  "     " + lev5;

            String value3_3 = typ1 + "     " + typ2 +  "     " + typ3 +  "     " + typ4 +  "     " + typ5;
            String value3_4 = check.getProjectLevel() == 1 ? "一" :
                    check.getProjectLevel() == 2 ? "二" :
                            check.getProjectLevel() == 3 ? "三" : "四";
            value3_4 = "第 " + value3_4 + " 级";
            String value3_5 = check.getProjectcontent();
            String value3_6 = check.getLincaqfx();
            String value3_7 = check.getLincyyfx();
            String value3_8 = check.getLincjssb();
            String value3_9 = check.getSyz();
            String value3_10 = check.getJjz();
            String value3_11 = check.getCzgz();

            // 项目第一操作者的最低职称限定
            cell = new PdfPCell(new Phrase(title3_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(numColumns);
            table.addCell(cell);

            //级 别
            cell = new PdfPCell(new Phrase(title3_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(21);
            table.addCell(cell);

            //类 别
            cell = new PdfPCell(new Phrase(title3_3, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_3, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(21);
            table.addCell(cell);

            //项目级别
            cell = new PdfPCell(new Phrase(title3_4, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_4, fontValue));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(21);
            table.addCell(cell);

            //项目的主要内容及意义
            cell = new PdfPCell(new Phrase(title3_5, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_5, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(21);
            table.addCell(cell);


            //临床应用安全性分析及佐证资料
            cell = new PdfPCell(new Phrase(title3_6, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_6, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_6,cell);
            cell.setColspan(21);
            table.addCell(cell);

            //技术临床应用有效性分析及佐证资料
            cell = new PdfPCell(new Phrase(title3_7, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_7, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_7,cell);
            cell.setColspan(21);
            table.addCell(cell);

            //科室已具备的技术和设备条件
            cell = new PdfPCell(new Phrase(title3_8, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_8, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_8,cell);
            cell.setColspan(21);
            table.addCell(cell);

            //适应症
            cell = new PdfPCell(new Phrase(title3_9, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_9, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_9,cell);
            cell.setColspan(21);
            table.addCell(cell);

            //禁忌症
            cell = new PdfPCell(new Phrase(title3_10, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_10, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_10,cell);
            cell.setColspan(21);
            table.addCell(cell);

            //操作规程
            cell = new PdfPCell(new Phrase(title3_11, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_11, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_11,cell);
            cell.setColspan(21);
            table.addCell(cell);

            //check 待定
//        PdfPCell c0 = new PdfPCell();
//        c0.setCellEvent(new CheckboxCellEvent("1", true));
//        c0.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//        c0.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//        c0.setFixedHeight(10);
//        c0.setPadding(10);
//
//        cell = c0;
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setFixedHeight(contentHeightStaff);
//        cell.setColspan(21);
//        table.addCell(cell);


            document.add(table);
            //endregion

            //endregion  检验检查类
        }

        // 单科、多科
        if (check.getProjectType() != 0) {
            //region 内容 里面分层次 单科多科
            document.newPage();
            numColumns = 15;
            table = new PdfPTable(numColumns);
            setWids = new int[numColumns];
            //table总Width宽度
            table.setTotalWidth(totalWidth);
            //设置总Width宽度 生效
            table.setLockedWidth(true);
            //列布局
            for (int i = 0; i < numColumns; i++) {
                setWids[i] = 1;
            }
            table.setWidths(setWids);

            //region 项  目  牵  头  人
            String title1_1 = "项  目  牵  头  人";
            String title1_2 = "姓名";
            String title1_2_1 = "出生年月";
            String title1_2_2 = "性别";
            String title1_3 = "学历、学位";
            String title1_3_1 = "职  称";
            String title1_3_2 = "职    务";
            String title1_4 = "专    业";
            String title1_4_1 = "专    长";
            String title1_4_2 = "手机号码";

            String value1_2 = check.getUserAccountName();
            String value1_2_1 = check.getBirthday();
            String value1_2_2 = check.getSexName();
            String value1_3 = check.getEdu();
            String value1_3_1 = check.getZhichenglc();
            String value1_3_2 = check.getZhiwu();
            String value1_4 = check.getZhuany();
            String value1_4_1 = check.getZhuanc();
            String value1_4_2 = check.getTelephone();

            //列一
            // 项  目  负  责  人
            cell = new PdfPCell(new Phrase(title1_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(numColumns);
            table.addCell(cell);
            //列二
            //姓名
            cell = new PdfPCell(new Phrase(title1_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //性别
            cell = new PdfPCell(new Phrase(title1_2_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_2_1, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //出生年月
            cell = new PdfPCell(new Phrase(title1_2_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_2_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //列三
            //学历、学位
            cell = new PdfPCell(new Phrase(title1_3, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_3, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //职  称
            cell = new PdfPCell(new Phrase(title1_3_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_3_1, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //职    务
            cell = new PdfPCell(new Phrase(title1_3_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_3_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //列四
            //专    业
            cell = new PdfPCell(new Phrase(title1_4, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_4, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //专  长
            cell = new PdfPCell(new Phrase(title1_4_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_4_1, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //手机号码
            cell = new PdfPCell(new Phrase(title1_4_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value1_4_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //endregion

            //region 项目参与专科
            if (check.getProjectType() == 2) {
                String title2_1 = "项目参与专科";
                String title2_2 = "科室名称";
                String title2_3 = "项目贡献度（贡献度之和为1）";
                String title2_4 = "科室主任签名";

                //列五
                //项目主要人员
                cell = new PdfPCell(new Phrase(title2_1, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(numColumns);
                table.addCell(cell);
                //科室名称
                cell = new PdfPCell(new Phrase(title2_2, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);

                //项目贡献度（贡献度之和为1）
                cell = new PdfPCell(new Phrase(title2_3, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(7);
                table.addCell(cell);

                //科室主任签名
                cell = new PdfPCell(new Phrase(title2_4, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(4);
                table.addCell(cell);

                for (XxbBProjdept item : pdList) {
                    //科室名称
                    cell = new PdfPCell(new Phrase(item.getDeptName(), fontValue));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(contentHeightStaff);
                    cell.setColspan(4);
                    table.addCell(cell);

                    //项目贡献度（贡献度之和为1）
                    cell = new PdfPCell(new Phrase(item.getGxd().toString(), fontValue));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(contentHeightStaff);
                    cell.setColspan(7);
                    table.addCell(cell);

                    //科室主任签名
                    cell = new PdfPCell(new Phrase(item.getDeptAccountName(), fontValue));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(contentHeightStaff);
                    cell.setColspan(4);
                    table.addCell(cell);
                }
            }
            //endregion
            String title3_0 = "项目级别及类型";
            String title3_1 = "项目第一操作者的最低职称限定";
            String title3_2 = "级 别";
            String title3_3 = "类 别";
            String title3_4 = "项目级别";
            String title3_01 = "项目类型";

//            1正高级 2副高级以上 3中级以上 4初级以上 5其他
//            1医　　师 2护　　士  3药剂人员 4技术人员 5其他
            String value3_2 = lev1 + "     " + lev2 +  "     " + lev3 +  "     " + lev4 +  "     " + lev5;

            String value3_3 = typ1 + "     " + typ2 +  "     " + typ3 +  "     " + typ4 +  "     " + typ5;

            String value3_4 = check.getProjectLevel() == 1 ? "一" :
                    check.getProjectLevel() == 2 ? "二" :
                            check.getProjectLevel() == 3 ? "三" : "四";
            value3_4 = "第 " + value3_4 + " 级 ";
            String lb1 = check.getProjectLb() == 1 ? "√ 手术类" : "□手术类";
            String lb2 = check.getProjectLb() == 2 ? "√ 治疗操作类" : "□治疗操作类";
            String lb3 = check.getProjectLb() == 3 ? "√ 其他类" : "□其他类";

            String value3_01 = lb1 + "     " + lb2 +  "     " + lb3;

            cell = new PdfPCell(new Phrase(title3_0, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(numColumns);
            table.addCell(cell);


            //项目级别
            cell = new PdfPCell(new Phrase(title3_4, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_4, fontValue));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(12);
            table.addCell(cell);

            //项目类型
            cell = new PdfPCell(new Phrase(title3_01, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_01, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(12);
            table.addCell(cell);

            // 项目第一操作者的最低职称限定
            cell = new PdfPCell(new Phrase(title3_1, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(numColumns);
            table.addCell(cell);

            //级 别
            cell = new PdfPCell(new Phrase(title3_2, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_2, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(12);
            table.addCell(cell);

            //类 别
            cell = new PdfPCell(new Phrase(title3_3, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_3, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(12);
            table.addCell(cell);

            //region 项目组人员情况
            int zcysgj = 0;
            int zcyszj = 0;
            int zcyscj = 0;
            int zchsgj = 0;
            int zchszj = 0;
            int zchscj = 0;
            int zcjsgj = 0;
            int zcjszj = 0;
            int zcjscj = 0;
            int zcqtgj = 0;
            int zcqtzj = 0;
            int zcqtcj = 0;
            int xlbs = 0;
            int xlyjs = 0;
            int xlbk = 0;
            int xlqt = 0;
            for (XxbBCheckD item : renyList) {
                if (StringUtil.isBlank(item.getRenshizifw())) {
                    item.setRenshizifw("");
                }
                if (StringUtil.isBlank(item.getZhicheng())) {
                    item.setZhicheng("");
                }
                if (StringUtil.isBlank(item.getEdu())) {
                    item.setEdu("");
                }
                if (item.getRenshizifw().equals("医师")) {
                    if (item.getZhicheng().equals("正高") || item.getZhicheng().equals("副高")) {
                        zcysgj += 1;
                    } else if (item.getZhicheng().equals("中级'")) {
                        zcyszj += 1;
                    } else {
                        zcyscj += 1;
                    }
                } else if (item.getRenshizifw().equals("护理")) {
                    if (item.getZhicheng().equals("正高") || item.getZhicheng().equals("副高")) {
                        zchsgj += 1;
                    } else if (item.getZhicheng().equals("中级")) {
                        zchszj += 1;
                    } else {
                        zchscj += 1;
                    }
                } else if (item.getRenshizifw().equals("技术")) {
                    if (item.getZhicheng().equals("正高") || item.getZhicheng().equals("副高")) {
                        zcjsgj += 1;
                    } else if (item.getZhicheng().equals("中级")) {
                        zcjszj += 1;
                    } else {
                        zcjscj += 1;
                    }
                } else {
                    if (item.getZhicheng().equals("正高") || item.getZhicheng().equals("副高")) {
                        zcqtgj += 1;
                    } else if (item.getZhicheng().equals("中级")) {
                        zcqtzj += 1;
                    } else {
                        zcqtcj += 1;
                    }
                }

                if (item.getEdu().equals("博士")) {
                    xlbs += 1;
                } else if (item.getEdu().equals("硕士")) {
                    xlyjs += 1;
                } else if (item.getEdu().equals("本科")) {
                    xlbk += 1;
                } else {
                    xlqt += 1;
                }
            }

            String title_xmzryqk = "项目组人员情况";
            String title_zcjg = "职结\n称构";
            String title_ys = "医生";
            String title_hs = "护士";
            String title_jsry = "技术人员";
            String title_qt = "其他";
            String title_gj = "高级";
            String title_zj = "中级";
            String title_cj = "初级";
            String title_xljg = "学结\n历构";
            String title_bsyjs = "博士研究生";
            String title_ssyjs = "硕士研究生";
            String title_bkxs = "本科学士";
            String title_zkjqt = "专科及其他";

            //列五
            //项目主要人员
            cell = new PdfPCell(new Phrase(title_xmzryqk, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(numColumns);
            table.addCell(cell);

            //职结称构
            cell = new PdfPCell(new Phrase(title_zcjg, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            cell.setRowspan(3);
            table.addCell(cell);

            //医生
            cell = new PdfPCell(new Phrase(title_ys, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //护士
            cell = new PdfPCell(new Phrase(title_hs, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //技术人员
            cell = new PdfPCell(new Phrase(title_jsry, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //其他
            cell = new PdfPCell(new Phrase(title_qt, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //医生高级
            cell = new PdfPCell(new Phrase(title_gj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //医生中级
            cell = new PdfPCell(new Phrase(title_zj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //医生初级
            cell = new PdfPCell(new Phrase(title_cj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //护士高级
            cell = new PdfPCell(new Phrase(title_gj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //护士中级
            cell = new PdfPCell(new Phrase(title_zj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //护士初级
            cell = new PdfPCell(new Phrase(title_cj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //技术人员高级
            cell = new PdfPCell(new Phrase(title_gj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //技术人员中级
            cell = new PdfPCell(new Phrase(title_zj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //技术人员初级
            cell = new PdfPCell(new Phrase(title_cj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //其他高级
            cell = new PdfPCell(new Phrase(title_gj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //其他中级
            cell = new PdfPCell(new Phrase(title_zj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //其他初级
            cell = new PdfPCell(new Phrase(title_cj, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);

            // value
            //医生高级
            cell = new PdfPCell(new Phrase(String.valueOf(zcysgj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //医生中级
            cell = new PdfPCell(new Phrase(String.valueOf(zcyszj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //医生初级
            cell = new PdfPCell(new Phrase(String.valueOf(zcyscj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //护士高级
            cell = new PdfPCell(new Phrase(String.valueOf(zchsgj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //护士中级
            cell = new PdfPCell(new Phrase(String.valueOf(zchszj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //护士初级
            cell = new PdfPCell(new Phrase(String.valueOf(zchscj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //技术人员高级
            cell = new PdfPCell(new Phrase(String.valueOf(zcjsgj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //技术人员中级
            cell = new PdfPCell(new Phrase(String.valueOf(zcjszj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //技术人员初级
            cell = new PdfPCell(new Phrase(String.valueOf(zcjscj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //其他高级
            cell = new PdfPCell(new Phrase(String.valueOf(zcqtgj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //其他中级
            cell = new PdfPCell(new Phrase(String.valueOf(zcqtzj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);
            //其他初级
            cell = new PdfPCell(new Phrase(String.valueOf(zcqtcj), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(1);
            table.addCell(cell);

            //学结历构
            cell = new PdfPCell(new Phrase(title_xljg, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            cell.setRowspan(2);
            table.addCell(cell);

            //博士研究生
            cell = new PdfPCell(new Phrase(title_bsyjs, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //硕士研究生
            cell = new PdfPCell(new Phrase(title_ssyjs, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //本科学士
            cell = new PdfPCell(new Phrase(title_bkxs, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //专科及其他
            cell = new PdfPCell(new Phrase(title_zkjqt, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //value
            //博士研究生
            cell = new PdfPCell(new Phrase(String.valueOf(xlbs), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //硕士研究生
            cell = new PdfPCell(new Phrase(String.valueOf(xlyjs), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //本科学士
            cell = new PdfPCell(new Phrase(String.valueOf(xlbk), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);
            //专科及其他
            cell = new PdfPCell(new Phrase(String.valueOf(xlqt), fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(contentHeightStaff);
            cell.setColspan(3);
            table.addCell(cell);

            //endregion

            //region 项目主要人员
            String title4_1 = "项  目  牵  头  人";
            String title4_2 = "姓名";
            String title4_2_1 = "性别";
            String title4_2_2 = "出生年月";
            String title4_3 = "学历、学位";
            String title4_3_1 = "职  称";
            String title4_3_2 = "职    务";
            String title4_4 = "专    业";
            String title4_4_1 = "专    长";
            String title4_4_2 = "联系电话";

            String value4_2 = "";
            String value4_2_1 = "";
            String value4_2_2 = "";
            String value4_3 = "";
            String value4_3_1 = "";
            String value4_3_2 = "";
            String value4_4 = "";
            String value4_4_1 = "";
            String value4_4_2 = "";
            int count = 0;
            for (XxbBCheckD item : renyList) {
                count +=1;
                value4_2 = item.getUserAccountName();
                value4_2_1 = item.getSexName();
                value4_2_2 = item.getBirthday();
                value4_3 = item.getEdu();
                value4_3_1 = item.getZhichenglc();
                value4_3_2 = item.getZhiwu();
                value4_4 = item.getZhuany();
                value4_4_1 = item.getZhuanc();
                value4_4_2 = item.getTelephone();
                //列一
                // 项  目  负  责  人
                cell = new PdfPCell(new Phrase(title4_1+"("+ count+")", fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(numColumns);
                table.addCell(cell);
                //列二
                //姓名
                cell = new PdfPCell(new Phrase(title4_2, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_2, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //性别
                cell = new PdfPCell(new Phrase(title4_2_1, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_2_1, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //出生年月
                cell = new PdfPCell(new Phrase(title4_2_2, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_2_2, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //列三
                //学历、学位
                cell = new PdfPCell(new Phrase(title4_3, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_3, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //职  称
                cell = new PdfPCell(new Phrase(title4_3_1, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_3_1, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //职    务
                cell = new PdfPCell(new Phrase(title4_3_2, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_3_2, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //列四
                //专    业
                cell = new PdfPCell(new Phrase(title4_4, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_4, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
                //专  长
                cell = new PdfPCell(new Phrase(title4_4_1, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_4_1, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);

                //联系电话
                cell = new PdfPCell(new Phrase(title4_4_2, fontTitle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(value4_4_2, fontValue));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(contentHeightStaff);
                cell.setColspan(3);
                table.addCell(cell);
            }
            //endregion

            String title3_5 = "项目的主要内容";
            String title3_6 = "临床应用安全性分析及佐证资料";
            String title3_7 = "技术临床应用安全性分析及佐证资料";
            String title3_8 = "科室已具备的技术和设备条件";
            String title3_9 = "适应症";
            String title3_10 = "禁忌症";
            String title3_11 = "操作规程";

            String title3_12 = "项目的目的、意义及创新之处";
            String title3_13 = "国内外研究与应用概况";
            String title3_14 = "可行性分析及实施计划";
            String title3_15 = "项目可解决的临床问题及效益分析";
            String title3_16 = "操作人员技术培训及岗位职责情况";
            String title3_17 = "可能造成的不良后果、并发症及相应的防范措施";
            String title3_18 = "质控措施";
            String title3_19 = "是否在我院诊疗科目范畴";


            String value3_5 = check.getProjectcontent();
            String value3_6 = check.getLincaqfx();
            String value3_7 = check.getLincyyfx();
            String value3_8 = check.getLincjssb();
            String value3_9 = check.getSyz();
            String value3_10 = check.getJjz();
            String value3_11 = check.getCzgz();

            String value3_12 = check.getProjectmdyycx();

            String value3_13 = check.getGnwyjyygk();
            String value3_14 = check.getKxxfxssjh();
            String value3_15 = check.getProjectlcwtxyfx();
            String value3_16 = check.getRyjjpxgwzzqk();
            String value3_17 = check.getBlhgbfzffcs();
            String value3_18 = check.getZkcs();
            String value3_19 = check.getSfwyzlkmfw();

            //项目的主要内容及意义
            cell = new PdfPCell(new Phrase(title3_5, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_5, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //项目的目的、意义及创新之处
            cell = new PdfPCell(new Phrase(title3_12, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_12, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //国内外研究与应用概况
            cell = new PdfPCell(new Phrase(title3_13, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_13, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //技术临床应用安全性分析及佐证资料
            cell = new PdfPCell(new Phrase(title3_6, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_6, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_6,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //技术临床应用有效性分析及佐证资料
            cell = new PdfPCell(new Phrase(title3_7, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_7, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_7,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //可行性分析及实施计划
            cell = new PdfPCell(new Phrase(title3_14, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_14, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //项目可解决的临床问题及效益分析
            cell = new PdfPCell(new Phrase(title3_15, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_15, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //科室已具备的技术和设备条件
            cell = new PdfPCell(new Phrase(title3_8, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_8, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_8,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //操作人员技术培训及岗位职责情况
            cell = new PdfPCell(new Phrase(title3_16, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_16, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //适应症
            cell = new PdfPCell(new Phrase(title3_9, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_9, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_9,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //禁忌症
            cell = new PdfPCell(new Phrase(title3_10, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_10, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_10,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //可能造成的不良后果、并发症及相应的防范措施
            cell = new PdfPCell(new Phrase(title3_17, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_17, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //操作规程
            cell = new PdfPCell(new Phrase(title3_11, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_11, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_11,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //质控措施
            cell = new PdfPCell(new Phrase(title3_18, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_18, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            //是否在我院诊疗科目范畴
            cell = new PdfPCell(new Phrase(title3_19, fontTitle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(value3_19, fontValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            this.setHeight(value3_5,cell);
            cell.setColspan(12);
            table.addCell(cell);

            document.add(table);
            //endregion
        }

        //如何控制分页展示table，显得紧凑些？在add到document之前添加跨页设置
//        table.setSplitLate(false);//跨页处理
//        table.setSplitRows(true);
//        document.add(table);

        out.flush();
        document.close();
        out.close();

        if (mergeAddPdfList.size() > 1) {
            mergePdfFiles(mergeAddPdfList, mergeFileName);
        }
    }

    private void setHeight(String value,PdfPCell cell){
        if(value.length() < 500) {
            cell.setFixedHeight(500 * 0.22f);
        } else {
            cell.setFixedHeight(value.length() * 0.22f);
        }
    }

    public void mergePdfFiles(List<String> fileList, String savePath) {
        Document document = null;
        try {
            document = new Document(new PdfReader(fileList.get(0)).getPageSize(1));
            PdfCopy pdfCopy = new PdfCopy(document, new FileOutputStream(savePath));
            document.open();
            for (int i = 0; i < fileList.size(); ++i) {
                PdfReader pdfReader = new PdfReader(fileList.get(i));
                int numberOfPages = pdfReader.getNumberOfPages();
                for (int j = 1; j <= numberOfPages; ++j) {
                    document.newPage();
                    PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, j);
                    pdfCopy.addPage(importedPage);
                }
                pdfReader.close();
            }
            pdfCopy.flush();
            pdfCopy.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    class CheckboxCellEvent implements PdfPCellEvent {
        // The name of the check box field
        protected String name;
        protected boolean flag;

        // We create a cell event
        public CheckboxCellEvent(String name, boolean flag) {
            this.name = name;
            this.flag = flag;
        }

        // We create and add the check box field
        public void cellLayout(PdfPCell cell, Rectangle position,
                               PdfContentByte[] canvases) {
            PdfWriter writer = canvases[0].getPdfWriter();
            // define the coordinates of the middle
            float x = (position.getLeft() + position.getRight()) / 2;
            float y = (position.getTop() + position.getBottom()) / 2;
            // define the position of a check box that measures 20 by 20
            //画勾
            Rectangle rect = new Rectangle(x - 5, y - 5, x + 5, y + 5);
            RadioCheckField checkbox = new RadioCheckField(writer, rect, name, "On");
            checkbox.setCheckType(RadioCheckField.TYPE_CHECK);

            if (flag) {
                //设置为选中状态
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }
            //画框
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.setColorStroke(BaseColor.BLACK);
            canvas.setLineDash(1f);
            canvas.rectangle(x - 10, y - 10, 20, 20);
            canvas.stroke();

            try {
                writer.addAnnotation(checkbox.getCheckField());
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        }
    }

    // 查询图片组装image
    private Image loadingPicture(String picUrl) throws BadElementException, IOException {
        File file = new File(picUrl);
        byte[] by = File2byte(file);
        Image image = Image.getInstance(by);
        image.scaleAbsolute(60, 80);// 调整图片大小(宽度 高度)
        return image;
    }

    private byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
