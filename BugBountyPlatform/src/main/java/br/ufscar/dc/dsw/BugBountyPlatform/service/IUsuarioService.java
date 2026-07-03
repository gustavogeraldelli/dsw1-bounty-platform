package br.ufscar.dc.dsw.BugBountyPlatform.service;

import br.ufscar.dc.dsw.BugBountyPlatform.domain.Usuario;

public interface IUsuarioService {
    Usuario buscarPorEmail(String email);
}