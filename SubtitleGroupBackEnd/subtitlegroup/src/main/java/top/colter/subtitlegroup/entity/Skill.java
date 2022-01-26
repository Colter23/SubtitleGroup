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
public class Skill implements Serializable {

    private Integer auth = 0;

    private Integer origin = 0;
    private Integer rebroadcast = 0;
    private Integer timeline = 0;
    private Integer effectTimeline = 0;
    private Integer translator = 0;
    private Integer proofreader = 0;
    private Integer cut = 0;
    private Integer afterEffect = 0;
    private Integer artist = 0;
    private Integer compaction = 0;


}
