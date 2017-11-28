/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.planner;

import com.topcoder.direct.services.view.dto.project.planner.ProjectPlannerTransferDTO;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The utilities for the import and export for the project plan in project planner page.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Project Planner and game plan preview Update)
 * <ul>
 *  <li>Updated {@link #exportProjectPlanToExcel(com.topcoder.direct.services.view.dto.project.planner.ProjectPlannerTransferDTO)} to add VM cost data</li>
 *  <li>Updated {@link #importProjectPlanFromExcel(org.apache.poi.hssf.usermodel.HSSFWorkbook)} to import VM cost data</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.2 - Topcoder - Remove JIRA Issues Related Functionality In Direct App v1.0
 * - remove JIRA related functionality
 * </p>
 *
 * @author GreatKevin, TCCoder 
 * @version 1.2 
 * @since 1.0 (Module Assembly - TopCoder Cockpit Project Planner)
 */
public class ProjectPlanUtil {

    /**
     * The sheet name to store the contests data.
     */
    private static final String CONTEST_SHEET = "contests";

    /**
     * The sheet name to store the VM cost data.
     *
     * @since 1.1
     */
    private static final String VM_COST_SHEET = "vm cost";

    /**
     * Exports the <code>ProjectPlannerTransferDTO</code> to excel workbook
     *
     * @param data the <code>ProjectPlannerTransferDTO</code> instance
     * @return the exported excel workbook.
     */
    public static HSSFWorkbook exportProjectPlanToExcel(ProjectPlannerTransferDTO data) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 1) sheets - contests
        HSSFSheet contestsSheet = workbook.createSheet(CONTEST_SHEET);

        Map<String, Object[]> sheetData = new LinkedHashMap<String, Object[]>();

        // put the header
        sheetData.put("0",
                new Object[]{"index", "name", "type", "follow", "dependsOn", "timeStart", "timeEnd", "startStr",
                        "endStr", "timeLength"});
        int count = 1;
        for (ProjectPlannerTransferDTO.ProjectPlannerContestRow c : data.getContests()) {
            sheetData.put(String.valueOf(count++),
                    new Object[]{c.getIndex(), c.getName(), c.getType(), c.getFollow(), integerListToString(
                            c.getDependsOn()), c.getTimeStart(), c.getTimeEnd(), c.getStartStr(), c.getEndStr(),
                            c.getTimeLength()});
        }

        insertDataToSheet(sheetData, contestsSheet);

        // 3) sheets - vm cost
        HSSFSheet vmCostSheet = workbook.createSheet(VM_COST_SHEET);

        sheetData = new LinkedHashMap<String, Object[]>();

        // put the header
        sheetData.put("0",
                new Object[]{"use vm ?"});
        sheetData.put("1",
                new Object[]{data.isUseVM() ? "yes" : "no"});

        insertDataToSheet(sheetData, vmCostSheet);

        return workbook;
    }

    /**
     * Imports the excel workbook and returns a <code>ProjectPlannerTransferDTO</code> instance.
     *
     * @param excel the excel workbook to import
     * @return the <code>ProjectPlannerTransferDTO</code> instance
     * @throws Exception if any error.
     */
    public static ProjectPlannerTransferDTO importProjectPlanFromExcel(HSSFWorkbook excel) throws Exception {
        ProjectPlannerTransferDTO result = new ProjectPlannerTransferDTO();

        // 1) parse contests sheet
        HSSFSheet contestsSheet = excel.getSheet(CONTEST_SHEET);

        Iterator<Row> iterator = contestsSheet.iterator();
        int rowNumber = 0;
        while (iterator.hasNext()) {
            rowNumber++;
            Row row = iterator.next();
            if (rowNumber > 1) {
                ProjectPlannerTransferDTO.ProjectPlannerContestRow contest = new ProjectPlannerTransferDTO
                        .ProjectPlannerContestRow();
                // index
                contest.setIndex((int) row.getCell(0).getNumericCellValue());
                // name
                contest.setName(row.getCell(1).getStringCellValue());
                // type
                contest.setType(row.getCell(2).getStringCellValue());
                // follow
                contest.setFollow(row.getCell(3).getStringCellValue());
                // depends on
                String dependsStr = row.getCell(4).getStringCellValue();
                List<Integer> dependsIds = new ArrayList<Integer>();
                if (dependsStr != null && dependsStr.length() > 0) {
                    String[] dependsStrIds = dependsStr.split(",");
                    for (String s : dependsStrIds) {
                        dependsIds.add(Integer.parseInt(s));
                    }

                }
                contest.setDependsOn(dependsIds);
                // time start
                contest.setTimeStart((int) row.getCell(5).getNumericCellValue());
                // time end
                contest.setTimeEnd((int) row.getCell(6).getNumericCellValue());
                // start str
                contest.setStartStr(row.getCell(7).getStringCellValue());
                // end str
                contest.setEndStr(row.getCell(8).getStringCellValue());
                // time length
                contest.setTimeLength((int) row.getCell(9).getNumericCellValue());

                result.getContests().add(contest);
            }
        }

        if (rowNumber <= 1) {
            throw new IllegalArgumentException("The import project plan excel file does not have contests data");
        }

        // 3) parse VM cost sheets
        HSSFSheet vmCostSheet = excel.getSheet(VM_COST_SHEET);

        iterator = vmCostSheet.iterator();
        rowNumber = 0;
        while (iterator.hasNext()) {
            rowNumber++;
            Row row = iterator.next();
            if (rowNumber > 1) {
                result.setUseVM(row.getCell(0).getStringCellValue().toLowerCase().equals("yes"));
            }
        }

        if (rowNumber <= 1) {
            throw new IllegalArgumentException("The import project plan excel file does not have vm cost data");
        }

        return result;
    }

    /**
     * Helper method to insert data into the give excel sheet.
     *
     * @param sheetData the sheet data
     * @param sheet     the excel sheet.
     */
    private static void insertDataToSheet(Map<String, Object[]> sheetData, HSSFSheet sheet) {
        Set<String> keySet = sheetData.keySet();
        int count = 0;
        for (String key : keySet) {
            Row row = sheet.createRow(count++);
            Object[] objArr = sheetData.get(key);
            int cellNum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }
    }

    /**
     * Helper method to covert a list of integer to ',' delimited string
     *
     * @param list a list of integers
     * @return the delimited string.
     */
    private static String integerListToString(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return "";
        }

        StringBuilder b = new StringBuilder();
        for (Integer id : list) {
            if (b.length() > 0) {
                b.append(',');
            }
            b.append(id);
        }
        return b.toString();
    }
}
