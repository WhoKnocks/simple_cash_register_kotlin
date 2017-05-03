package core.parsing;

import core.FoodType;
import core.MenuItem;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by GJ on 2/05/2017.
 */
public class InputReader {
    public FileReader readFile(FoodType foodType) throws FileNotFoundException {
        return new FileReader(String.format("C:\\joc-kassa-menu\\%s.json", foodType.getDescription().toLowerCase()));
    }
}
