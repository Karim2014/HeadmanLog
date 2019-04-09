package com.karimsabitov.headmanlog.attendance.model;

import com.karimsabitov.headmanlog.Utils.FileUtils;
import com.karimsabitov.headmanlog.Utils.Utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by User on 09.04.2019.
 */

public class ReportCreator {

    HSSFWorkbook mWorkbook;
    HSSFSheet mSheet;
    List<Date> mDates;
    SortedSet<String> mStudents;

    public ReportCreator() {
        mWorkbook = new HSSFWorkbook();
        mSheet = mWorkbook.createSheet("Отчет");
    }

    public void init() {
        Row row = mSheet.createRow(1);
        Cell cell = row.createCell(8);
        cell.setCellValue("Ведомость");
        row = mSheet.createRow(2);
        cell = row.createCell(3);
        cell.setCellValue("посещений занятий студентами группы ПКС-15 за октябрь 2018г.");

        row = mSheet.createRow(5);
        row.createCell(0).setCellValue("No");
        row.createCell(1).setCellValue("Фамилия И.О. студента");
        for (int i = 0; i < mDates.size(); i++) {
            cell = row.createCell(i+2);
            cell.setCellValue(Utils.formatDate("d", mDates.get(i)));
        }
        row.createCell(cell.getColumnIndex()+1).setCellValue("Всего");
    }

    public void setDates(List<Date> dates) {
        mDates = dates;
    }

    public void startSelectStudents(){
        // Формируем список студентов за весь месяц
        mStudents = new TreeSet<>();
        for (Date date : mDates) {
            AttDay day = FileUtils.getAttDay(date);
            if (day != null) {
                List<String> strings = day.getStudents();
                mStudents.addAll(strings);
            }
        }

        //Записываем студентов в книгу
    }

    public void saveReport() throws IOException {
        String filePath = FileUtils.folderPath + "Reports/"; // записать в файл
        File file = new File(filePath + "March2019.xls");
        file.getParentFile().mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        mWorkbook.write(fileOutputStream);
    }

    private class XLSStudent {
        private String name;
        private int row;

        public XLSStudent(String name, int row) {
            this.name = name;
            this.row = row;
        }
    }

}
