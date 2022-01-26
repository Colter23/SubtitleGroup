package top.colter.subtitlegroup.service;

import top.colter.subtitlegroup.entity.User;
import top.colter.subtitlegroup.vo.UserVo;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    void removeUser(Long id);
    void updateUser(User user);

    UserVo findUserById(Long id);
    List<User> findAllUser();
    List<UserVo> findUserByPertain(String pertain);
}
