package top.colter.subtitlegroup.dao;

import top.colter.subtitlegroup.entity.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    void removeUser(Long id);
    void updateUser(User user);

    User findUserById(Long id);
    List<User> findAllUser();
}
