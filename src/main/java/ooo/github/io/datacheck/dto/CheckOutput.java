package ooo.github.io.datacheck.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author kaiqin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutput {

    public static final CheckOutput EMPTY = new CheckOutput("", Collections.emptyList());

    private String msg;

    private List<ArchiveCheckResult> data;

}
