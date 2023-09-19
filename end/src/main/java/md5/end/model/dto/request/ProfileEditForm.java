package md5.end.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileEditForm {
    private String name;
    private String email;

    @Pattern(regexp = "\\S+", message = "The input field contains whitespaces.")
    @Pattern(regexp = "^0\\d{9}$",message = "Invalid phone number format.")
    private String tel;
    private String address;
    private String avatar;

}
