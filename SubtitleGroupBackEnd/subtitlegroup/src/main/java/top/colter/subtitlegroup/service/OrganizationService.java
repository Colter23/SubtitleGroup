package top.colter.subtitlegroup.service;

import top.colter.subtitlegroup.entity.Group;
import top.colter.subtitlegroup.entity.Organization;

import java.util.List;

public interface OrganizationService {
    void saveOrganization(Organization organization);
    void removeOrganization(Long id);
    void updateOrganization(Organization organization);

    List<Organization> findAllOrganization();
    List<Group> findAllGroup();
    List<Long> findAllGroupNumber();
    List<Organization> findOrganization(List<String> list);
}
