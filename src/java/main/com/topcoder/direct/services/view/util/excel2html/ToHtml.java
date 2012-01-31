/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util.excel2html;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellStyle.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * This example shows how to display a spreadsheet in HTML using the classes for
 * spreadsheet display.
 *
 * @author FireIce
 * @version  1.0
 */
public class ToHtml {
    private final Workbook wb;
    private final Appendable output;
    private boolean completeHTML;
    private Formatter out;
    private boolean gotBounds;
    private int firstColumn;
    private int endColumn;
    private HtmlHelper helper;
    private boolean generateAllSheets = false;
    private int sheetIndex;

    private static final String DEFAULTS_CLASS = "excelDefaults";

    private static final Map<Short, String> ALIGN = mapFor(ALIGN_LEFT, "left",
            ALIGN_CENTER, "center", ALIGN_RIGHT, "right", ALIGN_FILL, "left",
            ALIGN_JUSTIFY, "left", ALIGN_CENTER_SELECTION, "center");

    private static final Map<Short, String> VERTICAL_ALIGN = mapFor(
            VERTICAL_BOTTOM, "bottom", VERTICAL_CENTER, "middle", VERTICAL_TOP,
            "top");

    private static final Map<Short, String> BORDER = mapFor(BORDER_DASH_DOT,
            "dashed 1pt", BORDER_DASH_DOT_DOT, "dashed 1pt", BORDER_DASHED,
            "dashed 1pt", BORDER_DOTTED, "dotted 1pt", BORDER_DOUBLE,
            "double 3pt", BORDER_HAIR, "solid 1px", BORDER_MEDIUM, "solid 2pt",
            BORDER_MEDIUM_DASH_DOT, "dashed 2pt", BORDER_MEDIUM_DASH_DOT_DOT,
            "dashed 2pt", BORDER_MEDIUM_DASHED, "dashed 2pt", BORDER_NONE,
            "none", BORDER_SLANTED_DASH_DOT, "dashed 2pt", BORDER_THICK,
            "solid 3pt", BORDER_THIN, "dashed 1pt");

    @SuppressWarnings({"unchecked"})
    private static <K, V> Map<K, V> mapFor(Object... mapping) {
        Map<K, V> map = new HashMap<K, V>();
        for (int i = 0; i < mapping.length; i += 2) {
            map.put((K) mapping[i], (V) mapping[i + 1]);
        }
        return map;
    }

    /**
     * Creates a new converter to HTML for the given workbook.
     *
     * @param wb     The workbook.
     * @param output Where the HTML output will be written.
     *
     * @return An object for converting the workbook to HTML.
     */
    public static ToHtml create(Workbook wb, Appendable output) {
        return new ToHtml(wb, output);
    }

    /**
     * Creates a new converter to HTML for the given workbook.  If the path ends
     * with "<tt>.xlsx</tt>" an {@link XSSFWorkbook} will be used; otherwise
     * this will use an {@link HSSFWorkbook}.
     *
     * @param path   The file that has the workbook.
     * @param output Where the HTML output will be written.
     *
     * @return An object for converting the workbook to HTML.
     */
    public static ToHtml create(String path, Appendable output)
            throws IOException {
        return create(new FileInputStream(path), output);
    }

    /**
     * Creates a new converter to HTML for the given workbook.  This attempts to
     * detect whether the input is XML (so it should create an {@link
     * XSSFWorkbook} or not (so it should create an {@link HSSFWorkbook}).
     *
     * @param in     The input stream that has the workbook.
     * @param output Where the HTML output will be written.
     *
     * @return An object for converting the workbook to HTML.
     */
    public static ToHtml create(InputStream in, Appendable output)
            throws IOException {
        try {
            Workbook wb = WorkbookFactory.create(in);
            return create(wb, output);
        } catch (InvalidFormatException e){
            throw new IllegalArgumentException("Cannot create workbook from stream", e);
        }
    }

    private ToHtml(Workbook wb, Appendable output) {
        if (wb == null)
            throw new NullPointerException("wb");
        if (output == null)
            throw new NullPointerException("output");
        this.wb = wb;
        this.output = output;
        setupColorMap();
    }

    private void setupColorMap() {
        if (wb instanceof HSSFWorkbook)
            helper = new HSSFHtmlHelper((HSSFWorkbook) wb);
        else if (wb instanceof XSSFWorkbook)
            helper = new XSSFHtmlHelper((XSSFWorkbook) wb);
        else
            throw new IllegalArgumentException(
                    "unknown workbook type: " + wb.getClass().getSimpleName());
    }

    public static String generateStatsPage(int sheetIndex, String excelFile, List<String> tabs)  throws Exception {

        StringBuilder strBuf = new StringBuilder();
        ToHtml toHtml = create(excelFile, strBuf);
        toHtml.sheetIndex = sheetIndex;
        toHtml.setCompleteHTML(false);
        toHtml.printPage();
        
        for (int i = 0; i < toHtml.wb.getNumberOfSheets(); i++) {
            Sheet sheet =toHtml.wb.getSheetAt(i); 
            if (sheet.rowIterator().hasNext()) {
                tabs.add(sheet.getSheetName());
            }
        }

        return strBuf.toString();
    }
    
    public static String generateStatsPageForAllSheets(String excelFile)  throws Exception {

        StringBuilder strBuf = new StringBuilder();
        ToHtml toHtml = create(excelFile, strBuf);
        toHtml.setCompleteHTML(false);
        toHtml.setGenerateAllSheets(true);
        toHtml.printPage();

        return strBuf.toString();
    }

    public void setCompleteHTML(boolean completeHTML) {
        this.completeHTML = completeHTML;
    }

    public void printPage() throws IOException {
        try {
            ensureOut();
            if (completeHTML) {
                out.format(
                        "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>%n");
                out.format("<html>%n");
                out.format("<head>%n");
                out.format("</head>%n");
                out.format("<body>%n");
            }

            print();

            if (completeHTML) {
                out.format("</body>%n");
                out.format("</html>%n");
            }
        } finally {
            if (out != null)
                out.close();
            if (output instanceof Closeable) {
                Closeable closeable = (Closeable) output;
                closeable.close();
            }
        }
    }

    public void print() {
        printInlineStyle();
        printSheets();
    }

    private void printInlineStyle() {
        //out.format("<link href=\"excelStyle.css\" rel=\"stylesheet\" type=\"text/css\">%n");
        out.format("<style type=\"text/css\">%n");
        printStyles();
        out.format("</style>%n");
    }

    private void ensureOut() {
        if (out == null)
            out = new Formatter(output);
    }

    public void printStyles() {
        ensureOut();

        // First, copy the base css
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("excelStyle.css")));
            String line;
            while ((line = in.readLine()) != null) {
                out.format("%s%n", line);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Reading standard css", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //noinspection ThrowFromFinallyBlock
                    throw new IllegalStateException("Reading standard css", e);
                }
            }
        }

        // now add css for each used style
        Set<String> seen = new HashSet<String>();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            Iterator<Row> rows = sheet.rowIterator();
            gotBounds = false;
            ensureColumnBounds(sheet);
            
            while (rows.hasNext()) {
                Row row = rows.next();
                
                for (int col = firstColumn; col < endColumn; col++) {
                    if (col >= row.getFirstCellNum() && col < row.getLastCellNum()) {
                        Cell cell = row.getCell(col);
                        if (cell != null) {
                            CellStyle style = cell.getCellStyle();
                            String styleName = styleName(i, style);
                            if (!seen.contains(styleName)) {
                                printStyle(i, style);
                                seen.add(styleName);
                            }
                        }
                    }
                }
            }
        }
        
        CellStyle style = wb.getCellStyleAt((short) 0);
        String styleName = styleName(0, style);
        if (!seen.contains(styleName)) {
            printStyle(0, style);
            seen.add(styleName);
        }
    }

    private void printStyle(int sheetIndex, CellStyle style) {
        out.format(".%s .%s {%n", DEFAULTS_CLASS, styleName(sheetIndex, style));
        styleContents(style);
        out.format("}%n");
    }

    private void styleContents(CellStyle style) {
        styleOut("text-align", style.getAlignment(), ALIGN);
        styleOut("vertical-align", style.getAlignment(), VERTICAL_ALIGN);
        fontStyle(style);
        borderStyles(style);
        helper.colorStyles(style, out);
    }

    private void borderStyles(CellStyle style) {
        styleOut("border-left", style.getBorderLeft(), BORDER);
        styleOut("border-right", style.getBorderRight(), BORDER);
        styleOut("border-top", style.getBorderTop(), BORDER);
        styleOut("border-bottom", style.getBorderBottom(), BORDER);
    }

    private void fontStyle(CellStyle style) {
        Font font = wb.getFontAt(style.getFontIndex());

        if (font.getBoldweight() >= HSSFFont.BOLDWEIGHT_NORMAL)
            out.format("  font-weight: bold;%n");
        if (font.getItalic())
            out.format("  font-style: italic;%n");

        int fontheight = font.getFontHeightInPoints();
        if (fontheight == 9) {
            //fix for stupid ol Windows
            fontheight = 10;
        }
        out.format("  font-size: %dpt;%n", fontheight);

        // Font color is handled with the other colors
    }

    private String styleName(int sheetIndex, CellStyle style) {
        if (style == null)
            style = wb.getCellStyleAt((short) 0);
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        if (style.getIndex() > 0) {
            fmt.format("style_%02x_%02x", sheetIndex, style.getIndex());
        } else {
            fmt.format("style_%02x", style.getIndex());
        }
        return fmt.toString();
    }

    private <K> void styleOut(String attr, K key, Map<K, String> mapping) {
        String value = mapping.get(key);
        if (value != null) {
            out.format("  %s: %s;%n", attr, value);
        }
    }

    private static int ultimateCellType(Cell c) {
        int type = c.getCellType();
        if (type == Cell.CELL_TYPE_FORMULA)
            type = c.getCachedFormulaResultType();
        return type;
    }

    private void printSheets() {
        ensureOut();
        
        if (generateAllSheets) {
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                gotBounds = false;
                Sheet sheet = wb.getSheetAt(i);
                printSheet(i, sheet);
            }
        } else {
            gotBounds = false;
            Sheet sheet = wb.getSheetAt(sheetIndex);
            printSheet(sheetIndex, sheet);
        }
    }

    public void printSheet(int sheetIndex, Sheet sheet) {
        ensureOut();
        out.format("<div id='sheet_%s'><table class=%s>%n", sheetIndex, DEFAULTS_CLASS);
        printCols(sheet);
        printSheetContent(sheetIndex, sheet);
        out.format("</table></div>%n");
    }

    private void printCols(Sheet sheet) {
        out.format("<col/>%n");
        ensureColumnBounds(sheet);
        for (int i = firstColumn; i < endColumn; i++) {
            out.format("<col/>%n");
        }
    }

    private void ensureColumnBounds(Sheet sheet) {
        if (gotBounds)
            return;

        Iterator<Row> iter = sheet.rowIterator();
        firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
        endColumn = 0;
        while (iter.hasNext()) {
            Row row = iter.next();
            short firstCell = row.getFirstCellNum();
            if (firstCell >= 0) {
                firstColumn = Math.min(firstColumn, firstCell);
                endColumn = Math.max(endColumn, row.getLastCellNum());
            }
        }
        gotBounds = true;
    }

    private void printSheetContent(int sheetIndex, Sheet sheet) {
        out.format("<tbody>%n");
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = rows.next();

            StringBuffer sb = new StringBuffer();
            boolean empty = true;
            for (int i = firstColumn; i < endColumn; i++) {
                String content = "&nbsp;";
                String attrs = "";
                CellStyle style = null;
                if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {
                    Cell cell = row.getCell(i);

                    if (cell != null) {
                        style = cell.getCellStyle();
                        attrs = tagStyle(cell, style);

                        //Set the value that is rendered for the cell
                        //also applies the format
                        CellFormat cf = CellFormat.getInstance(
                                style.getDataFormatString());
                        CellFormatResult result = cf.apply(cell);
                        content = result.text;
                        if(row.getRowNum() == 1) {
                            try {
                                Date date = cell.getDateCellValue();
                                if(date != null) {
                                    content = new SimpleDateFormat("MM/dd/yyyy").format(date);
                                }
                            } catch (Exception e) {
                                // not date cell, so ignore it.
                            }
                        }

                        // hack it.
                        if (content.contains("(0)")) {
                            content = content.replace("(0)", "-");
                        }

                        if (content.contains("(0.00)")) {
                            content = content.replace("(0.00)", "-");
                        }

                        if (content.equals(""))
                            content = "&nbsp;";
                    }
                }
                if (!content.equals("&nbsp;")) {
                    empty = false;
                }
                sb.append("    <td class=").append(styleName(sheetIndex, style)).append(" ").append(attrs).append(">").append(content).append("</td>\n");
            }
            
            if (!empty) {
                if (row.getRowNum() <= 1) {
                    out.format("  <tr class='header'>%n");
                }  else {
                    out.format("  <tr>%n");
                }
                out.format("%s%n", sb.toString());
                out.format("  </tr>%n");
            }
        }
        out.format("</tbody>%n");
    }

    private String tagStyle(Cell cell, CellStyle style) {
        if (style.getAlignment() == ALIGN_GENERAL) {
            switch (ultimateCellType(cell)) {
            case HSSFCell.CELL_TYPE_STRING:
                return "style=\"text-align: left;\"";
            case HSSFCell.CELL_TYPE_BOOLEAN:
            case HSSFCell.CELL_TYPE_ERROR:
                return "style=\"text-align: center;\"";
            case HSSFCell.CELL_TYPE_NUMERIC:
            default:
                // "right" is the default
                break;
            }
        }
        return "";
    }
    
    void setGenerateAllSheets(boolean generateAllSheets) {
        this.generateAllSheets = generateAllSheets;
    }
}