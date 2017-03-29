package com.moggot.mytranslator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.moggot.mytranslator.translator.DaoMaster;
import com.moggot.mytranslator.translator.DaoSession;
import com.moggot.mytranslator.translator.Translator;
import com.moggot.mytranslator.translator.TranslatorDao;

import java.util.List;

/**
 * Created by toor on 01.03.17.
 */

public class DataBase {

    private Context context;
    private TranslatorDao translatorDao;

    private final String DB_NAME = "alarm_db";

    public DataBase(Context context) {
        this.context = context;
        translatorDao = setupDb();
    }

    public void addRecord(Translator record) {
        translatorDao.insert(record);
    }

    public void editRecord(Translator record) {
        translatorDao.update(record);
    }

    public void deleteRecord(Translator record) {
        translatorDao.delete(record);
    }

    public Translator getTranslator(long id) {
        return translatorDao.load(id);
    }

    public List<Translator> getAllRecords() {
        return translatorDao.queryBuilder().build().list();
    }

    private TranslatorDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session
        return masterSession.getTranslatorDao();
    }

}
