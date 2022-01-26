package top.colter.subtitlegroup.dao.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import top.colter.subtitlegroup.dao.SheetDao;
import top.colter.subtitlegroup.entity.MultiJob;
import top.colter.subtitlegroup.entity.Sheet;
import top.colter.subtitlegroup.entity.SingleJob;
import top.colter.subtitlegroup.utils.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SheetDaoImpl implements SheetDao {

    private final MongoTemplate mongoTemplate;

    public SheetDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveSheet(Sheet sheet) {
        mongoTemplate.save(sheet);
    }

    @Override
    public void removeSheet(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query);
    }

    @Override
    public void updateSheet(Sheet sheet) {
        String sid = sheet.getSid();
        sheet.setSid(null);
        if (ObjectUtils.objCheckIsNull(sheet)){
            return;
        }
        Query query = new Query(Criteria.where("_id").is(sid));

        Update update = new Update();

        if (sheet.getCutName()!=null){
            if (sheet.getCutName().getValue()!=null) {
                update.set("cutName.value", sheet.getCutName().getValue());
            }
            if (sheet.getCutName().getLock()!=null) {
                update.set("cutName.lock", sheet.getCutName().getLock());
            }
        }

        if (sheet.getTitle()!=null){
            if (sheet.getTitle().getValue()!=null) {
                update.set("title.value", sheet.getTitle().getValue());
            }
            if (sheet.getTitle().getLock()!=null) {
                update.set("title.lock", sheet.getTitle().getLock());
            }
        }
        if (sheet.getRemark()!=null){
            if (sheet.getRemark().getValue()!=null) {
                update.set("remark.value", sheet.getRemark().getValue());
            }
            if (sheet.getRemark().getLock()!=null) {
                update.set("remark.lock", sheet.getRemark().getLock());
            }
        }
        if (sheet.getDuration()!=null){
            if (sheet.getDuration().getValue()!=null) {
                update.set("duration.value", sheet.getDuration().getValue());
            }
            if (sheet.getDuration().getLock()!=null) {
                update.set("duration.lock", sheet.getDuration().getLock());
            }
        }
        if (sheet.getOrigin()!=null){
            if (sheet.getOrigin().getOriginLink()!=null) {
                update.set("origin.originLink", sheet.getOrigin().getOriginLink());
            }
            if (sheet.getOrigin().getDownloadLink()!=null) {
                update.set("origin.downloadLink", sheet.getOrigin().getDownloadLink());
            }
            if (sheet.getOrigin().getTitle()!=null) {
                update.set("origin.title", sheet.getOrigin().getTitle());
            }
            if (sheet.getOrigin().getType()!=null) {
                update.set("origin.type", sheet.getOrigin().getType());
            }
            if (sheet.getOrigin().getProducer()!=null) {
                update.set("origin.producer", sheet.getOrigin().getProducer());
            }
            if (sheet.getOrigin().getLock()!=null) {
                update.set("origin.lock", sheet.getOrigin().getLock());
            }
        }

        if (sheet.getState()!=null) {
            update.set("state", sheet.getState());
        }
        if (sheet.getLink()!=null) {
            update.set("link", sheet.getLink());
        }
        if (sheet.getRow()!=null) {
            update.set("row", sheet.getRow());
        }
        if (sheet.getFinishTime()!=null) {
            update.set("finishTime", sheet.getFinishTime());
        }
        if (sheet.getPublishTime()!=null) {
            update.set("publishTime", sheet.getPublishTime());
        }
        if (sheet.getSlivers()!=null) {
            update.set("slivers", sheet.getSlivers());
        }

        Map<String,List<SingleJob>> sjobs = new HashMap<>();
        if (sheet.getTimeline()!=null) {
            if (sheet.getTimeline().get(0).getIndex() == null){
                update.set("timeline", sheet.getTimeline());
            }else{
                sjobs.put("timeline", sheet.getTimeline());
            }
        }
        if (sheet.getTranslator()!=null) {
            if (sheet.getTranslator().get(0).getIndex() == null){
                update.set("translator", sheet.getTranslator());
            }else{
                sjobs.put("translator", sheet.getTranslator());
            }
        }
        if (sheet.getProofreader()!=null) {
            if (sheet.getProofreader().get(0).getIndex() == null){
                update.set("proofreader", sheet.getProofreader());
            }else{
                sjobs.put("proofreader", sheet.getProofreader());
            }
        }
        if (sheet.getSecondaryProofreader()!=null) {
            if (sheet.getSecondaryProofreader().get(0).getIndex() == null){
                update.set("secondaryProofreader", sheet.getSecondaryProofreader());
            }else{
                sjobs.put("secondaryProofreader", sheet.getSecondaryProofreader());
            }
        }

        if (sjobs.size() > 0) {
            sjobs.forEach((label, sjob) -> sjob.forEach(job -> {
                String prefix = label + "." + job.getIndex() + ".";
                if (job.getRow() != null) {
                    update.set(prefix + "row", job.getRow());
                }
                if (job.getState() != null) {
                    update.set(prefix + "state", job.getState());
                }
                if (job.getLink() != null) {
                    update.set(prefix + "link", job.getLink());
                }
                if (job.getReceiveTime() != null) {
                    update.set(prefix + "receiveTime", job.getReceiveTime());
                }
                if (job.getFinishTime() != null) {
                    update.set(prefix + "finishTime", job.getFinishTime());
                }
                if (job.getProducer() != null) {
                    update.set(prefix + "producer", job.getProducer());
                }
                if (job.getLock() != null) {
                    update.set(prefix + "lock", job.getLock());
                }
            }));
        }

        Map<String, MultiJob> mjobs = new HashMap<>();
        if (sheet.getCut()!=null) {
            mjobs.put("cut", sheet.getCut());
        }
        if (sheet.getAfterEffect()!=null) {
            mjobs.put("afterEffect", sheet.getAfterEffect());
        }
        if (sheet.getCover()!=null) {
            mjobs.put("cover", sheet.getCover());
        }
        if (sheet.getCompaction()!=null) {
            mjobs.put("compaction", sheet.getCompaction());
        }
        if (sheet.getArtist()!=null) {
            mjobs.put("artist", sheet.getArtist());
        }

        if (mjobs.size() > 0) {
            mjobs.forEach((label, job) ->  {
                String prefix = label+".";
                if (job.getState()!=null){
                    update.set(prefix+"state", job.getState());
                }
                if (job.getLink()!=null){
                    update.set(prefix+"link", job.getLink());
                }
                if (job.getReceiveTime()!=null){
                    update.set(prefix+"receiveTime", job.getReceiveTime());
                }
                if (job.getFinishTime()!=null){
                    update.set(prefix+"finishTime", job.getFinishTime());
                }
                if (job.getProducer()!=null){
                    update.set(prefix+"producer", job.getProducer());
                }
                if (job.getLock()!=null){
                    update.set(prefix+"lock", job.getLock());
                }
            });
        }

        mongoTemplate.updateFirst(query, update, Sheet.class);
    }

    @Override
    public List<Sheet> findSheetByPertain(String pertain) {
        Query query = new Query(Criteria.where("pertain").is(pertain));
        return mongoTemplate.find(query, Sheet.class);
    }
}
