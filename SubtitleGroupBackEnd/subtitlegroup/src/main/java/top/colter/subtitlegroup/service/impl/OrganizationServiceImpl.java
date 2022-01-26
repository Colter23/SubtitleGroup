package top.colter.subtitlegroup.service.impl;

import org.springframework.stereotype.Service;
import top.colter.subtitlegroup.dao.OrganizationDao;
import top.colter.subtitlegroup.entity.Group;
import top.colter.subtitlegroup.entity.Organization;
import top.colter.subtitlegroup.service.OrganizationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDao organizationDao;

    public OrganizationServiceImpl(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override
    public void saveOrganization(Organization organization) {
        organizationDao.saveOrganization(organization);
    }

    @Override
    public void removeOrganization(Long id) {
        organizationDao.removeOrganization(id);
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    @Override
    public List<Organization> findAllOrganization() {
        return organizationDao.findAllOrganization();
    }

    @Override
    public List<Group> findAllGroup() {
        return findAllOrganization().stream().flatMap(v->v.getGroup().stream()).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllGroupNumber() {
        return findAllGroup().stream().map(Group::getQqGroup).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Organization> findOrganization(List<String> list) {
        List<Organization> lo = findAllOrganization();
        lo = lo.stream().filter(v -> list.stream().anyMatch(d -> d.startsWith(v.getHomeLink()))).collect(Collectors.toList());
        lo.forEach(v -> {
            v.setGroup(v.getGroup().stream().filter(d -> list.contains(d.getLink())).collect(Collectors.toList()));
        });
        return lo;
    }
}
