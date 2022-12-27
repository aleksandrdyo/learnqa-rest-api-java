package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {//метод генерирует рандомные email
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    //метод с импортом Мар, без параметров для случайных email
    public static Map<String, String> getRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    //метод с импортом Мар, с параметрами HashMap
    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues= DataGenerator.getRegistrationData();

        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }

        return userData;

    }

    /*На уроке создали две функции в классе дата генератор
    Одна возвращает значения по умолчанию
    Вторая заменяет данные, если один из ключей был передан в качестве аргумента
    Можно создать рядом третью, удалять значение из мапы по переданному аргументу
    Создали мапу по умолчанию, создали массив ключей, а цикле перебрали аргументы, если аргумент совпадает, удаляй*/

    /*public static Map<String, String> getRegistrationDataWithoutOneField() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        //data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }*/

    public static Map<String, String> getRegistrationDataWithoutOneField(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = DataGenerator.getRegistrationData();

        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "lastName", "firstName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.remove(key, nonDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }




}
