package task6;

import java.util.ArrayList;
import java.util.List;

public class CourseImporter {
    // Граница перевода: NumberFormatException -> CourseImportException с номером записи
    public List<Integer> importHours(List<String> records) throws CourseImportException {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            try {
                result.add(CourseParser.parseHours(records.get(i)));
            } catch (NumberFormatException e) {
                throw new CourseImportException(i + 1, e);
            }
        }
        return result;
    }
}
