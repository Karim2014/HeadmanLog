package com.karimsabitov.headmanlog.Utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.karimsabitov.headmanlog.App;
import com.karimsabitov.headmanlog.attendance.model.AttDay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by User on 13.09.2018.
 */

public class FileUtils {

    public static String folderPath;

    static {
        upgradeFolderPath();
    }

    public static void upgradeFolderPath() {
        folderPath = getAppFolder();
    }

    private static String getAppFolder() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return App.self().getCacheDir().getPath();
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constants.APP_FOLDER;
    }

    public static boolean makeAppDirectory() {
        return new File(folderPath).mkdir();
    }

    public static boolean makeDirectory(String path){
        return new File(folderPath + path).mkdirs();
    }

    public static boolean createFile(String path, String name) throws IOException {
        return new File(folderPath + path, name).createNewFile();
    }

    public static File openDirectory(String dirName) {
        return new File(folderPath + dirName);
    }

    public static boolean isFileExist(String fileName) {
        return new File(folderPath + fileName).exists();
    }

    @NonNull
    public static String openTextFile(String fileName) throws IOException {
        File file = new File(folderPath + fileName);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    return text.toString();
                }
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void deleteFile(String fileName) {
        File file = new File(folderPath + fileName);
        if (file != null) {
            file.delete();
        }
    }

    public static void updateJson(AttDay attDay) {
        Path path = getFolderPath(attDay.getDate());

        try {
            JSONObject jsonObject = JSONUtils.createJSONObj(attDay);
            writeJson(path.getFullPath(), jsonObject);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.e("Test", "updateAttDay: ", e);
        }
    }

    public static void writeJson(String fileName, JSONObject jsonObject) throws IOException {
        FileWriter fileWriter = new FileWriter(FileUtils.folderPath + fileName);
        fileWriter.write(jsonObject.toString());
        Log.d("Test", jsonObject.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static void addAttDay(AttDay attDay) {
        Path path = getFolderPath(attDay.getDate());

        FileUtils.makeDirectory(path.getFolderPath());

        try {
            FileUtils.createFile(path.getFolderPath(), path.getFileName());
            JSONObject jsonObject = JSONUtils.createJSONObj(attDay);
            FileUtils.writeJson(path.getFullPath(), jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static Path getFolderPath(Date date){
        int[] ymd = CalendarParser.getDates(date);
        String[] pathl = new String[] {
                String.valueOf(ymd[CalendarParser.YEAR]),
                String.valueOf(ymd[CalendarParser.MONTH]+1),
                CalendarParser.formatDate(Constants.ATT_FILE_PTRN, date)
        };

        String[] strings = new String[] {
                Constants.ATTENDANCE_FOLDER + pathl[CalendarParser.YEAR] + "/" + pathl[CalendarParser.MONTH] + "/" + pathl[CalendarParser.DAY_OF_MONTH] + Constants.JSON_EXT,
                Constants.ATTENDANCE_FOLDER + pathl[CalendarParser.YEAR] + "/" + pathl[CalendarParser.MONTH] + "/",
                pathl[CalendarParser.DAY_OF_MONTH] + Constants.JSON_EXT
        };

        return new Path.Builder()
                .setFullPath(strings[0])
                .setFolderPath(strings[1])
                .setFileName(strings[2])
                .build();


        /*return new String[] {
                Constants.ATTENDANCE_FOLDER + pathl[CalendarParser.YEAR] + "/" + pathl[CalendarParser.MONTH] + "/" + pathl[CalendarParser.DAY_OF_MONTH] + Constants.JSON_EXT,
                Constants.ATTENDANCE_FOLDER + pathl[CalendarParser.YEAR] + "/" + pathl[CalendarParser.MONTH] + "/",
                pathl[CalendarParser.DAY_OF_MONTH] + Constants.JSON_EXT
        };*/
    }

    @Nullable
    public static AttDay getAttDay(Date date) {
        Path path = getFolderPath(date);

        AttDay attDay;

        try {
            JSONObject jsonObject = JSONUtils.getJsonFromFile(path.getFullPath());
            attDay = new AttDay(date, null);
            JSONUtils.getAttDay(jsonObject, attDay);
        } catch (IOException | IndexOutOfBoundsException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return attDay;
    }


    public static void deleteAttDay(Date attDay) {
        deleteFile(getFolderPath(attDay).getFullPath());
    }
}
