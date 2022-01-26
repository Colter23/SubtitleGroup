package top.colter.subtitlegroup.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sheet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sheet implements Serializable {
    /**
     * ID
     */
    @Id
    private String sid;
    /**
     * 所属组
     */
    @Indexed
    private String pertain;
    /**
     * 创建者ID
     */
    private Long creator;
    /**
     * 切片名
     */
    private TextLock cutName;
    /**
     * 视频标题
     */
    private TextLock title;
    /**
     * 备注
     */
    private TextLock remark;
    /**
     * 时长
     */
    private NumberLock duration;

    /**
     * 源
     */
    private Origin origin;

    /**
     * 状态
     */
    private Integer state;
    /**
     * 上传后链接
     */
    private String link;
    /**
     * 行数（用于计算高度）
     */
    private Integer row;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 完成时间
     */
    private String finishTime;
    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 分片列表
     */
    private List<List<Integer>> slivers;

    /**
     * 时轴列表
     */
    private List<SingleJob> timeline;
    /**
     * 翻译列表
     */
    private List<SingleJob> translator;
    /**
     * 校对列表
     */
    private List<SingleJob> proofreader;
    /**
     * 二校列表
     */
    private List<SingleJob> secondaryProofreader;
    /**
     * 剪辑
     */
    private MultiJob cut;
    /**
     * 后期
     */
    private MultiJob afterEffect;
    /**
     * 封面
     */
    private MultiJob cover;
    /**
     * 压制
     */
    private MultiJob compaction;
    /**
     * 美工
     */
    private MultiJob artist;

}
