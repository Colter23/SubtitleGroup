package top.colter.subtitlegroup.service.impl;

import org.springframework.stereotype.Service;
import top.colter.subtitlegroup.dao.UserDao;
import top.colter.subtitlegroup.entity.User;
import top.colter.subtitlegroup.service.OrganizationService;
import top.colter.subtitlegroup.service.UserService;
import top.colter.subtitlegroup.vo.UserVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final OrganizationService organizationService;

    public UserServiceImpl(UserDao userDao, OrganizationService organizationService) {
        this.userDao = userDao;
        this.organizationService = organizationService;
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public UserVo findUserById(Long id) {
        User user = userDao.findUserById(id);
        UserVo userVo = new UserVo();
        userVo.setQq(user.getQq());
        userVo.setName(user.getName());
        userVo.setPertain(user.getPertain());
        userVo.setOrganization(organizationService.findOrganization(new ArrayList<>(userVo.getPertain().keySet())));
        return userVo;
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public List<UserVo> findUserByPertain(String pertain) {
        List<UserVo> users = new ArrayList<>();
        List<User> allUser = userDao.findAllUser();
        for (User user : allUser){
            if (user.getPertain().containsKey(pertain)){
                UserVo userVo = new UserVo();
                userVo.setName(user.getName());
                userVo.setQq(user.getQq());
                userVo.setRemark(user.getRemark());
                userVo.setState(user.getState());
                userVo.setSkill(user.getPertain().get(pertain));
                users.add(userVo);
            }
        }
        return users;
    }
}
