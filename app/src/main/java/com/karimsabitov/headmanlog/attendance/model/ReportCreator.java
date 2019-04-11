package com.karimsabitov.headmanlog.attendance.model;

import com.karimsabitov.headmanlog.Utils.FileUtils;
import com.karimsabitov.headmanlog.Utils.Utils;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
    CellStyle mDefaultStyle;
    CellStyle mRightAlignStyle;
    CellStyle mLeftAlignStyle;

    String mMoth;
    String mYear;

    public ReportCreator() {
        mWorkbook = new HSSFWorkbook();
        mSheet = mWorkbook.createSheet("Отчет");
        mSheet.setDisplayRowColHeadings(true); // типа отображать колонтинутл но хз
        //разметка страницы
        mSheet.setMargin(Sheet.BottomMargin, 0.98); // in inches
        mSheet.setMargin(Sheet.TopMargin, 1.06);
        mSheet.setMargin(Sheet.HeaderMargin, 0.39);
        mSheet.setMargin(Sheet.FooterMargin, 0.39);
        mSheet.setMargin(Sheet.LeftMargin, 0);
        mSheet.setMargin(Sheet.RightMargin, 0);
        mSheet.getPrintSetup().setLandscape(true);
        mSheet.setPrintGridlines(true);
        mSheet.setDisplayZeros(false);

        mDefaultStyle  = createCellStyle();
        mRightAlignStyle = createCellStyle();
        mLeftAlignStyle = createCellStyle();

        mDefaultStyle.setAlignment(CellStyle.ALIGN_CENTER);
        mLeftAlignStyle.setAlignment(CellStyle.ALIGN_LEFT);
        mRightAlignStyle.setAlignment(CellStyle.ALIGN_RIGHT);

    }

    private CellStyle createCellStyle() {
        CellStyle cellStyle = mWorkbook.createCellStyle();

        cellStyle.setFont(createFont((short) 12));
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);

        return cellStyle;
    }

    public void init(String groupName, String month, String year) {
        if (mDates == null)
            throw new IllegalStateException("The list of dates is empty. You should call setDates(@NonNull <list>) before");

        mMoth = month;
        mYear = year;

        mSheet.getHeader().setCenter(String.format("Ведомость\nпосещений занятий студентами группы %s за %s %sг.", groupName, month, year));

        //  Заполнения шапки таблицы
        Row row = mSheet.createRow(0);

        Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
        cell.setCellValue("№");
        cell.setCellStyle(mDefaultStyle);
        mSheet.setColumnWidth(0, 4*256);// trunc(((1*8+5)/8*256)/256)

        cell = row.createCell(1, Cell.CELL_TYPE_STRING);
        cell.setCellValue("Ф.И.О.");
        cell.setCellStyle(mDefaultStyle);
        mSheet.setColumnWidth(1, 22*256);
        //mSheet.autoSizeColumn(cell.getColumnIndex());

        for (int i = 0; i < mDates.size(); i++) {
            cell = row.createCell(i+2, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Utils.formatDate("d", mDates.get(i)));
            cell.setCellStyle(mDefaultStyle);
            mSheet.setColumnWidth(i+2, 841); //~3.2*256
        }
        cell = row.createCell(cell.getColumnIndex()+1);
        cell.setCellValue("Всего");
        cell.setCellStyle(mRightAlignStyle);
        mSheet.setColumnBreak(cell.getColumnIndex()+2); // разрыв страницы при печати
    }

    private Font createFont(short size) {
        HSSFFont font = mWorkbook.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName("Times New Roman");
        return font;
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

        Row row;
        Cell cell;

        Object[] strings = mStudents.toArray();
        //Записываем студентов в книгу
        for (int i = 0; i < strings.length; i++) {
            row = mSheet.createRow(i+1);
            cell = row.createCell(0, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(i+1);
            cell.setCellStyle(mRightAlignStyle);

            cell = row.createCell(1, Cell.CELL_TYPE_STRING);
            cell.setCellValue(strings[i].toString());
            cell.setCellStyle(mLeftAlignStyle);
        }

        //нарисуем таблицу
        for (int i = 0; i < mDates.size(); i++) {
            for (int j = 0; j < mStudents.size(); j++) {
                mSheet.getRow(j+1).createCell(i+2, Cell.CELL_TYPE_NUMERIC).setCellStyle(mDefaultStyle);
            }
        }

        // Начинаем считать посещаемость
        for (int i = 0; i < mDates.size(); i++) {
            AttDay day = FileUtils.getAttDay(mDates.get(i)); // читаем день отметок
            if (day != null) { // если есть такой, то работаем
                List<String> studentList = day.getStudents();  //найдем список доступных студентов в этот день
                HashMap<Integer, List<String>> couples = day.getCouples(); // найдем список отметок посещаемости этого дня
                // будем считать количетсво вхождений каждого студента
                for (String s : studentList) {
                    int epsHours = 0; // счетчик количества пар отсутвия
                    for (int j = 0; j < couples.size(); j++) { // просматриваем все элементы массива и ищем есть ли такой студент
                        if (couples.get(j).contains(s)) { // если есть такой студент
                            epsHours++; // то увеличиваем счетчик
                        }
                    }
                    //можно записать в файл
                    int index = getStudentIndex(s); // Определим в какую строку это нуждно записать
                    row = mSheet.getRow(index);
                    cell = row.createCell(i+2, Cell.CELL_TYPE_NUMERIC);
                    cell.setCellStyle(mDefaultStyle);
                    if (epsHours != 0)
                        cell.setCellValue(epsHours*2);
                }
            }
        }

        //  посчитаем всего
        for (int i = 0; i < mStudents.size(); i++) {
            CellRangeAddress region = new CellRangeAddress(i+1, i+1, 2, mDates.size() + 1);
            cell = mSheet.getRow(i+1).createCell(mDates.size()+2, Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula(String.format("Sum(%s)", region.formatAsString()));
            cell.setCellStyle(mRightAlignStyle);
        }

    }

    private int getStudentIndex(String student) {
        Iterator<String> stringIterator = mStudents.iterator();
        int index = 1;
        while (stringIterator.hasNext()) {
            if (stringIterator.next().toString().equals(student)) {
                return index;
            }
            index++;
        }
        return 0;
    }

    public void saveReport() throws IOException {
        String filePath = FileUtils.folderPath + "Reports/"; // записать в файл
        File file = new File(filePath + mMoth + "-" + mYear +".xls");
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
