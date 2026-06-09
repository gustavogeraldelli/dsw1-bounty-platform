package br.ufscar.dc.dsw.BugBountyPlatform;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.IEmpresaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.dao.IPesquisadorDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.dao.IProgramaDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.dao.IRelatorioDAO;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Empresa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Pesquisador;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Programa;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.Relatorio;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BugBountyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugBountyPlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner popularBanco(IEmpresaDAO empresaDAO, IPesquisadorDAO pesquisadorDAO, IProgramaDAO programaDAO, IRelatorioDAO relatorioDAO) {
		return (args) -> {
			Empresa e1 = new Empresa();
			e1.setEmail("security@techcorp.com");
			e1.setSenha("123");
			e1.setRole("ROLE_EMPRESA");
			e1.setNome("TechCorp S.A.");
			e1.setCnpj("11.111.111/0001-11");
			e1.setDescricao("Empresa de Tecnologia e Software");
			e1.setSetor("Tecnologia");
			empresaDAO.save(e1);

			Empresa e2 = new Empresa();
			e2.setEmail("app@finbank.com");
			e2.setSenha("123");
			e2.setRole("ROLE_EMPRESA");
			e2.setNome("FinBank");
			e2.setCnpj("22.222.222/0001-22");
			e2.setDescricao("Banco Digital e FinTech");
			e2.setSetor("Finanças");
			empresaDAO.save(e2);

			Pesquisador p1 = new Pesquisador("hacker@hunter.com", "123", "ROLE_PESQUISADOR", "João Silva", "111.222.333-44", "11999999999", "M", LocalDate.of(1995, 5, 20));
			pesquisadorDAO.save(p1);

			Pesquisador p2 = new Pesquisador("alice@sec.com", "123", "ROLE_PESQUISADOR", "Alice Martins", "555.666.777-88", "11888888888", "F", LocalDate.of(1998, 10, 15));
			pesquisadorDAO.save(p2);

			Programa prog1 = new Programa("Vulnerabilidades Web", "*.techcorp.com", new BigDecimal("5000.00"), LocalDate.of(2026, 12, 31), e1);
			programaDAO.save(prog1);

			Programa prog2 = new Programa("Teste em API PIX", "api.finbank.com", new BigDecimal("15000.00"), LocalDate.of(2026, 10, 15), e2);
			programaDAO.save(prog2);

			Relatorio r1 = new Relatorio("uploads/joao_poc1.pdf", StatusRelatorio.EM_TRIAGEM, LocalDateTime.now(), p1, prog1);
			relatorioDAO.save(r1);
		};
	}

}