package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Admin;
import org.springframework.data.repository.CrudRepository;

public interface IAdminDAO extends CrudRepository<Admin, Long> {
}