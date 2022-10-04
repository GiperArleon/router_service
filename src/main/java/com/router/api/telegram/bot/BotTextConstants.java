package com.router.api.telegram.bot;

public class BotTextConstants {
    public static final String WRONG_COMMAND = "Сообщение, не соответствующее формату\nВсе команды /help";
    public static final String START_COMMAND = "Сначала нужно зарегистрироваться\n/reg Имя Фамилия\n/reg Иван Иванов\nВсе команды /help";
    public static final String WORK_COMMAND = "Вы уже зарегестрированы!\nВводите рабочее время\n/work часы минуты описание\n/work 3 20 мыл посуду\n/help все команды";
    public static final String BOT_STARTED_MESSAGE = "TimeTracker бот запущен.\nСтарт по команде /start";
    public static final String BOT_OK_MESSAGE = "Успешно выполнено";
    public static final String BOT_ERROR_MESSAGE = "Произошла ошибка";
    public static final String BOT_ERROR_REG_WRONG_PARAMS = "Проверьте параметры\n/reg Имя Фамилия\n/reg Иван Иванов";
    public static final String BOT_ERROR_WORK_WRONG_PARAMS = "Проверьте параметры\n/work часы минуты описание\n/work 3 20 мыл посуду";

    public static final String HELP =
            "Бот учета времени\n\n" +
            "*Список команд*\n" +
            "\uD83D\uDC49/reg - регистрация\n     /reg Иван Иванов\n" +
            "\uD83D\uDC49/work - ввод времени\n     /work 2 30 мыл посуду\n" +
            "\uD83D\uDC49/help - помощь\n\n" +
            "❗*Успей до 21:59* \uD83D\uDE42";
}
