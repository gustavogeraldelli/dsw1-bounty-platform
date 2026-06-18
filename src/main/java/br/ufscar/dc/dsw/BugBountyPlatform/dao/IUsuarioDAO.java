package br.ufscar.dc.dsw.BugBountyPlatform.dao;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {

    Usuario findByEmail(String email);

}