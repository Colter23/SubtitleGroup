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
public class Group implements Serializable {
    /**
     * 组名
     */
    private String name;
    /**
     * 链接
     * eg: /bell
     */
    private String link;
    /**
     * 头像
     */
    private String avatar;
    /**
     * qq群号
     */
    private Long qqGroup;
}
