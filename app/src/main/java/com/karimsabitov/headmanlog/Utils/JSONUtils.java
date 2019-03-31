package com.karimsabitov.headmanlog.Utils;

import com.karimsabitov.headmanlog.attendance.model.AttDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 13.09.2018.
 */

public class JSONUtils {

    private static final String KEY_DATE = "Date";
    private static final String KEY_COUPLE = "Couple";
    private static final String KEY_STUDENTS = "Students";


    public static JSONObject getJsonFromFile(String path) throws JSONException, IOException {

        String uri = FileUtils.openTextFile(path);
        return new JSONObject(uri);
    }

    public static JSONArray getJsonArrayFromObject(JSONObject jsonObject, String key) throws JSONException {
        return (JSONArray) jsonObject.get(key);
    }

    public static JSONObject createJSONObj(AttDay attDay) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_STUDENTS, getStudentsArray(attDay));
        jsonObject.put(KEY_COUPLE, getCouplesArray(attDay));
        jsonObject.put(KEY_DATE, attDay.getDate().getTime());
        return jsonObject;
    }

    private static JSONArray getStudentsArray(AttDay attDay) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < attDay.getStudents().size(); i++) {
            jsonArray.put(attDay.getStudents().get(i));
        }

        return jsonArray;
    }

    private static JSONArray getCouplesArray(AttDay attDay) {
        JSONArray array = new JSONArray();
        HashMap<Integer, List<String>> map = attDay.getCouples();
        for (int i = 0; i < map.size(); i++) {
            JSONArray couple = new JSONArray();
            for (String item: map.get(i)){
                couple.put(item);
            }
            array.put(couple);
        }
        return array;
    }

    private static HashMap<Integer,List<String>> getCoupleFromJsonObject(JSONObject jsonObject) {
        HashMap<Integer,List<String>> hash = new HashMap<>();
        try {
            JSONArray jsonArray = JSONUtils.getJsonArrayFromObject(jsonObject, KEY_COUPLE);
            for (int i = 0; i < jsonArray.length(); i++) {
                hash.put(i, getListFromJsonArray(jsonArray.getJSONArray(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return hash;
        }
        return hash;
    }

    private static List<String> getListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            strings.add(jsonArray.getString(i));
        }
        return strings;
    }

    public static AttDay getAttDay(JSONObject jsonObject, AttDay attDay) throws JSONException {
        attDay.setCouples(getCoupleFromJsonObject(jsonObject));
        attDay.setStudents(getStudentsFromJson(jsonObject));
        Date date = new Date(jsonObject.getLong(KEY_DATE));
        attDay.setDate(date);
        return attDay;
    }

    private static List<String> getStudentsFromJson(JSONObject jsonObject) throws JSONException {
        JSONArray array = getJsonArrayFromObject(jsonObject, KEY_STUDENTS);

        return getListFromJsonArray(array);
    }
}
