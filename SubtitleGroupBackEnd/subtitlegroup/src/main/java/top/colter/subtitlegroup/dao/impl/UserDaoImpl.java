package top.colter.subtitlegroup.dao.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import top.colter.subtitlegroup.dao.UserDao;
import top.colter.subtitlegroup.entity.User;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final MongoTemplate mongoTemplate;

    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public void removeUser(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User findUserById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public List<User> findAllUser() {
        return mongoTemplate.findAll(User.class);
    }
}
