package top.colter.subtitlegroup.dao;

import top.colter.subtitlegroup.entity.Group;
import top.colter.subtitlegroup.entity.Organization;

import java.util.List;

public interface OrganizationDao {
    void saveOrganization(Organization organization);
    void removeOrganization(Long id);
    void updateOrganization(Organization organization);

    List<Organization> findAllOrganization();
}
