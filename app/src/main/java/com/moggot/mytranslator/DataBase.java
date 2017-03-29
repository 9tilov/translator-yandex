package com.moggot.commonalarmclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseIntArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moggot.commonalarmclock.alarm.Alarm;
import com.moggot.commonalarmclock.alarm.AlarmDao;
import com.moggot.commonalarmclock.alarm.DaoMaster;
import com.moggot.commonalarmclock.alarm.DaoSession;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by toor on 01.03.17.
 */

public class DataBase {

    private Context context;
    private AlarmDao alarmDao;

    private final String DB_NAME = "alarm_db";

    public DataBase(Context context) {
        this.context = context;
        alarmDao = setupDb();
    }

    public void addAlarm(Alarm alarm) {
        alarmDao.insert(alarm);
    }

    public void editAlarm(Alarm alarm) {
        alarmDao.update(alarm);
    }

    public void deleteAlarm(Alarm alarm) {
        alarmDao.delete(alarm);
    }

    public Alarm getAlarm(long id) {
        return alarmDao.load(id);
    }

    public List<Alarm> getAllAlarms() {
        return alarmDao.queryBuilder().build().list();
    }

    private AlarmDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session
        return masterSession.getAlarmDao();
    }

    public int getRandomRequestCode() {
        HashSet<Integer> unique = new HashSet<>();
        List<Alarm> alarms = getAllAlarms();
        for (Alarm alarm : alarms) {
            Type type = new TypeToken<SparseIntArray>() {
            }.getType();
            SparseIntArray ids = new Gson().fromJson(alarm.getRequestCodes(), type);
            for (int i = 0; i < ids.size(); ++i) {
                unique.add(ids.valueAt(i));
            }
        }
        Random rnd = new Random();
        int requestCode;
        do {
            requestCode = rnd.nextInt(Integer.MAX_VALUE);
        } while (unique.contains(requestCode));
        return requestCode;
    }

}
