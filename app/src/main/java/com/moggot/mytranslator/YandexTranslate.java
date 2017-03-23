package com.moggot.mytranslator;

import javax.net.ssl.HttpsURLConnection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class YandexTranslate {
    private static int i = 0;

    public static String translate(String lang, String input) throws IOException {
        String urlStr = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170323T095132Z.f84ddafb785fdce6.54b42bd77424bb33f1d638187dccd53d94c803ce";
        URL urlObj = new URL(urlStr);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes("text=" + URLEncoder.encode(input, "UTF-8") + "&lang=" + lang);

        InputStream response = connection.getInputStream();
        String json = new java.util.Scanner(response).nextLine();
        int start = json.indexOf("[");
        int end = json.indexOf("]");
        String translated = json.substring(start + 2, end - 1);
        i++;
        if (translated.equals(input) && i < 2) {
            // if return equal of entered text - we need change direction of translation
            return translate("en", input);
        } else return translated;
    }
}