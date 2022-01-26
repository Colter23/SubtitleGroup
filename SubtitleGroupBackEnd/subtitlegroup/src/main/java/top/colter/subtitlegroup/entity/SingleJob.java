package top.colter.subtitlegroup.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleJob implements Serializable {
    private Integer index;
    private Integer row;
    private Integer state;
    private String link;
    private String receiveTime;
    private String finishTime;
    private Long producer;
    private Boolean lock;
}
