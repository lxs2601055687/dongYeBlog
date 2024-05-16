package cn.bitoffer.api.dto.lottery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoParam implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("userName")
    private String userName;

}
