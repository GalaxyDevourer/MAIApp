package models.services;

public class UtilGenerator {
    public String[] randomNamesGenerator (String name, int size) {
        String[] array = new String[size];

        for (int i = 0; i < size; i++) {
            array[i] = name + i;
        }

        return array;
    }
}
