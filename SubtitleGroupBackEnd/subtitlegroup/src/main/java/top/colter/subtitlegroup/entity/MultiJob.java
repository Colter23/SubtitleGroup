package top.colter.subtitlegroup.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiJob implements Serializable {
    private Integer state;
    private String link;
    private String receiveTime;
    private String finishTime;
    private List<Long> producer;
    private Boolean lock;
}
