package md5.end.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    @NotEmpty(message = "Product name is required")
    private String name;

    private String description;

    @Min(value = 0, message = "Import price must be greater than 0")
    private double importPrice;
    @Min(value = 0, message = "Export price must be greater than 0")
    private double exportPrice;
    @Min(value = 0, message = "Stock must be greater than 0")
    private int stock;
    private byte status;
    @NotNull(message = "Must be have a value")
    private Long brandId;
    @NotNull(message = "Must be have a value")
    private Long parentCategoryId;
    @NotNull(message = "Must be have a value")
    private List<Long> childCategoryId;

    private Map<Long,String> specs = new HashMap<>();
}
