package weatherBot;

public class Utils {

    public static final String botName = "moshnikovd0709_bot";

    public static final String botToken = "1238834312:AAFML27xcHW4aO2Fyc9fc6ED-IxcmtwK4Jg";

    public static String getURL(String location) {
        return "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e";
    }

}
