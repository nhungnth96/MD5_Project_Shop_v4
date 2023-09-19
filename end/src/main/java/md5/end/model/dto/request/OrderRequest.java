package md5.end.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequest {
    private String receiver;
    private String address;
    private String tel;
    private String note;

    private Long paymentId;
    private Long shippingId;

}
