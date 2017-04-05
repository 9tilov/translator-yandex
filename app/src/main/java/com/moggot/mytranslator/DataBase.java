package com.moggot.mytranslator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.moggot.mytranslator.translate.Translate;
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

    private static final String LOG_TAG = "DataBase";

    public DataBase(Context context) {
        this.context = context;
        translatorDao = setupDb();
    }

    public void addRecord(Translator record) {

        Translator tmpRecord;
        try {
            tmpRecord = translatorDao.queryBuilder().where(TranslatorDao.Properties.Text.eq(record.getText())
                    , TranslatorDao.Properties.InputLanguage.eq(record.getInputLanguage())
                    , TranslatorDao.Properties.OutputLanguage.eq(record.getOutputLanguage())).unique();
        } catch (Exception ex) {
            tmpRecord = null;
        }

        if (tmpRecord == null)
            translatorDao.insert(record);
    }

    public Translator findRecord(Translator record) {
        Translator tmpRecord = translatorDao.queryBuilder().where(TranslatorDao.Properties.Text.eq(record.getText())
                , TranslatorDao.Properties.InputLanguage.eq(record.getInputLanguage())
                , TranslatorDao.Properties.OutputLanguage.eq(record.getOutputLanguage())).unique();
        if (tmpRecord != null)
            return tmpRecord;
        else
            return null;
    }

    public void editRecord(Translator record) {
        translatorDao.update(record);
    }

    public void deleteRecord(Translator record) {
        translatorDao.delete(record);
    }

    public void deleteAll() {
        translatorDao.deleteAll();
    }

    public void deleteAllFavorites() {
        List<Translator> itemsForDelete = translatorDao.queryBuilder().where(TranslatorDao.Properties.IsFavorites.eq(true)).list();
        for (Translator item : itemsForDelete) {
            item.setIsFavorites(false);
            editRecord(item);
        }
    }

    public Translator getTranslator(long id) {
        return translatorDao.load(id);
    }

    public List<Translator> getAllRecords() {
        return translatorDao.loadAll();
    }

    public List<Translator> getFavoritesRecords() {
        return translatorDao.queryBuilder().where(TranslatorDao.Properties.IsFavorites.eq(true)).list();
    }

    private TranslatorDao setupDb() {
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session
        return masterSession.getTranslatorDao();
    }

}
