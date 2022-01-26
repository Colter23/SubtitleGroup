package top.colter.subtitlegroup.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "user")
public class User implements Serializable {
    /**
     * qq号
     */
    @Id
    private Long qq;
    /**
     * 昵称
     */
    private String name;
    /**
     * 状态
     * 0: 可工作
     * 1: 无法工作
     */
    private Integer state = 0;
    /**
     * 备注
     */
    private String remark = "";
    /**
     * 所属组及职能
     * eg: /vivid/bell
     */
    @Indexed
    private Map<String, Skill> pertain;

    //private Map<String, Map<String, Integer>> pertain;

    //private Map<String, Integer> skill;
}
