package kg.attractor.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer id;

    private String name;
    private Integer parentId;

    public boolean hasParent() {
        return parentId != null;
    }
}