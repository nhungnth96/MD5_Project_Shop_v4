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
public class ProductShopResponse {
    private Long id;
    private String brandName;
    private String productName;
    private String image;
    private String description;
    private String price;
    private Map<String,String> specifications;
    private List<String> childImage;
}
