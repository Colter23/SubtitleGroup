package top.colter.subtitlegroup.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "organization")
public class Organization implements Serializable{
    /**
     * ID
     */
    @Id
    private String oid;
    /**
     * 组织名
     */
    private String organization;
    /**
     * 组织主页链接
     * eg: /vivid
     */
    private String homeLink;
    /**
     * 主页文字
     */
    private String homeText;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 组织下组列表
     */
    private List<Group> group;


}
