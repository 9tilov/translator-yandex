package com.moggot.multipreter.conversation;

/**
 * Класс экранирования символов ";" "+" в запросе к серверу
 */
public class StringEscaper {

    /**
     * Экранирование символов в запросе
     */
    public static String escapeResponse(String text) {
        text = text.replace(";", "%3B");
        text = text.replace("+", "%2B");
        return text;
    }

    /**
     * Экранирование символов в ответе
     */
    public static String escapeResult(String text) {
        text = text.replace("%3B", ";");
        text = text.replace("%2B", "+");
        return text;
    }
}
