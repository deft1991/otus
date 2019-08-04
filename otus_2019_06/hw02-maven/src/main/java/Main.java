import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DIYarrayList<String> arr = new DIYarrayList<>(5);

        Collections.addAll(arr, "1", "2", "3", "4", "5");

        System.out.println(Arrays.toString(arr.getArray()));
        Collections.addAll(arr, "16", "17", "18", "19", "20");
        System.out.println(Arrays.toString(arr.getArray()));
        Collections.addAll(arr, "6", "7", "8", "9", "0");
        System.out.println(Arrays.toString(arr.getArray()));
        Collections.addAll(arr, "21", "22", "23", "24", "25");
        System.out.println(Arrays.toString(arr.getArray()));
        Collections.addAll(arr, "11", "12", "13", "14", "15");

        DIYarrayList<String> copyList = new DIYarrayList<>(arr.size());
        Collections.copy(copyList, arr);
        System.out.println(Arrays.toString(arr.getArray()));
        System.out.println(Arrays.toString(copyList.getArray()));

        // я не знал но сорт не работает с массивами в которых есть нуловые элементы
        DIYarrayList<Integer> arrForSort = new DIYarrayList<>(5);
        Collections.addAll(arrForSort, 1,3,2,4,5);
        Collections.sort(arrForSort);
        System.out.println(Arrays.toString(arrForSort.getArray()));
    }

}
