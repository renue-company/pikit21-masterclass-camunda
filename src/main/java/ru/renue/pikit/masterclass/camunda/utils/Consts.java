package ru.renue.pikit.masterclass.camunda.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nevolin Alex.
 */
public class Consts {

    public static final Map<String, String> ERRORS = new HashMap<>();

    static {
        ERRORS.put("ERR-001", "Ошибка взамодействия с сервисом оплаты. Повторите заказ позже.");
        ERRORS.put("ERR-002", "Оплата не прошла. Повторите заказ еще раз.");
    }
}
