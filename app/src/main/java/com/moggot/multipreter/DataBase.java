package com.moggot.multipreter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.moggot.multipreter.translator.DaoMaster;
import com.moggot.multipreter.translator.DaoSession;
import com.moggot.multipreter.translator.Translator;
import com.moggot.multipreter.translator.TranslatorDao;

import java.util.List;

/**
 * Класс базы данных для хранения записей.
 * Используется ORM GreenDAO
 */
public class DataBase {

    private static final String LOG_TAG = DataBase.class.getSimpleName();

    /**
     * Контекст Activity
     */
    private Context context;

    /**
     * Объект greenDAO
     */
    private TranslatorDao translatorDao;

    /**
     * Конструктор
     * Инициализация БД
     *
     * @param context - контекст Activity
     */
    public DataBase(Context context) {
        this.context = context;
        translatorDao = setupDb();
    }

    /**
     * Инициализация БД
     *
     * @return объект greenDAO
     */
    private TranslatorDao setupDb() {
        String DB_NAME = "alarm_db";
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null); //create database db file if not exist
        SQLiteDatabase db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession = master.newSession(); //Creates Session session
        return masterSession.getTranslatorDao();
    }

    /**
     * Добавление уникальных записей в базу
     *
     * @param translator - запись
     */
    public void addRecord(Translator translator) {

        Translator tmpRecord;
        try {
            tmpRecord = translatorDao.queryBuilder().where(TranslatorDao.Properties.Text.eq(translator.getText())
                    , TranslatorDao.Properties.InputLanguage.eq(translator.getInputLanguage())
                    , TranslatorDao.Properties.OutputLanguage.eq(translator.getOutputLanguage())).unique();
        } catch (Exception ex) {
            tmpRecord = null;
        }

        if (tmpRecord == null)
            translatorDao.insert(translator);
    }

    /**
     * Поиск записи по тексту, входному и выходному языку
     *
     * @param translator - запись, которую требуется найти
     * @return найденная запись
     */
    public Translator findRecord(Translator translator) {
        return translatorDao.queryBuilder().where(TranslatorDao.Properties.Text.eq(translator.getText())
                , TranslatorDao.Properties.InputLanguage.eq(translator.getInputLanguage())
                , TranslatorDao.Properties.OutputLanguage.eq(translator.getOutputLanguage())).unique();
    }

    /**
     * Удаление всех записей в БД
     */
    public void deleteAll() {
        translatorDao.deleteAll();
    }

    /**
     * Удаление всех избранных записей
     * С записей снимается флаг IsFavorites
     */
    public void deleteAllFavorites() {
        List<Translator> itemsForDelete = translatorDao.queryBuilder().where(TranslatorDao.Properties.IsFavorites.eq(true)).list();
        for (Translator item : itemsForDelete) {
            item.setIsFavorites(false);
            editRecord(item);
        }
    }

    /**
     * Обновление записи
     *
     * @param translator - запись, которую требуется обновить
     */
    public void editRecord(Translator translator) {
        translatorDao.update(translator);
    }

    /**
     * Удаление записи
     *
     * @param translator - запись, которую требуется удалить
     */
    public void deleteRecord(Translator translator) {
        translatorDao.delete(translator);
    }

    /**
     * Получение записи по ее ID
     *
     * @param id - id записи
     * @return запись
     */
    public Translator getTranslator(Long id) {
        return translatorDao.load(id);
    }

    /**
     * Получение всех записей
     *
     * @return список всех записей
     */
    public List<Translator> getAllRecords() {
        return translatorDao.queryBuilder().orderDesc(TranslatorDao.Properties.Date).list();
    }

    /**
     * Получение избранных записей
     *
     * @return список избранных записей
     */
    public List<Translator> getFavoritesRecords() {
        return translatorDao.queryBuilder().where(TranslatorDao.Properties.IsFavorites.eq(true)).orderDesc(TranslatorDao.Properties.Date).list();
    }
}
