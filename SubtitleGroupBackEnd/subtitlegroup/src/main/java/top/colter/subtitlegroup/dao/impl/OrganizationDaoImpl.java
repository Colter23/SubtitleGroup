package top.colter.subtitlegroup.dao.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import top.colter.subtitlegroup.dao.OrganizationDao;
import top.colter.subtitlegroup.entity.Organization;

import java.util.List;

@Component
public class OrganizationDaoImpl implements OrganizationDao {

    private final MongoTemplate mongoTemplate;

    public OrganizationDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveOrganization(Organization organization) {
        mongoTemplate.save(organization);
    }

    @Override
    public void removeOrganization(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query);
    }

    @Override
    public void updateOrganization(Organization organization) {

    }

    @Override
    public List<Organization> findAllOrganization() {
        //Query query = new Query(Criteria.where("group").elemMatch(Criteria.where("link").in("/vivid/bell")));
        //return mongoTemplate.find(query, Organization.class);
        return mongoTemplate.findAll(Organization.class);
    }

}
