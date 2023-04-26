package ooo.github.io.datacheck.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 抽取sql中的tableName
 *
 * @author kaiqin
 */
public class TableRetrieveUtil {

    private static final String SCHEME_KEY_COLUMN_USAGE = "information_schema.`key_column_usage`";

    private static final String FROM = "from";

    public static Collection<String> retrieveTableName(String sql) {
        if (sql == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        String[] split = sql.split("\\s+");
        for (int i = 0; i < split.length; i++) {
            String splitName = split[i].toLowerCase();
            if (splitName.equals(FROM) && i + 1 < split.length && !split[i + 1].toLowerCase().equals(SCHEME_KEY_COLUMN_USAGE)) {
                result.add(split[i + 1].toLowerCase());
            }
        }
        return result;
    }

}
