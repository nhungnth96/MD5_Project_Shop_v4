package md5.end.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponse {
    private Long id;
    private String parentCategoryName;
    private String childCategoryName;
    private String brandName;
    private String productName;
    private String description;
    private String importPrice;
    private String exportPrice;
    private int stock;
    private String status;
    private Map<String,String> specifications;
    private List<String> images;
}
