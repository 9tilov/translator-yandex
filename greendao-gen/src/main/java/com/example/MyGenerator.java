package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.moggot.mytranslator.translateData");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            /* Use forward slashes if you're on macOS or Unix, i.e. "/app/src/main/java"  */
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        Entity translationData = addTranslationData(schema);
    }

    private static Entity addTranslationData(final Schema schema) {
        Entity translateData = schema.addEntity("Data");

        translateData.addIdProperty().primaryKey().autoincrement();
        translateData.addStringProperty("text").notNull();
        translateData.addStringProperty("translation").notNull();
        translateData.addStringProperty("inputLanguage").notNull();
        translateData.addStringProperty("outputLanguage").notNull();
        translateData.addBooleanProperty("isFavorites");

        return translateData;
    }

}