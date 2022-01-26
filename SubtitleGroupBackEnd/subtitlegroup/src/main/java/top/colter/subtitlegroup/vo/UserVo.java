package top.colter.subtitlegroup.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.colter.subtitlegroup.entity.Organization;
import top.colter.subtitlegroup.entity.Skill;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo implements Serializable {

    private Long qq;
    private String name;
    private Integer state;
    private String remark;
    private Skill skill;
    private Map<String, Skill> pertain;
    private List<Organization> organization;

}
