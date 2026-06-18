package br.ufscar.dc.dsw.BugBountyPlatform;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.*;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.*;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.Severidade;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.enums.StatusRelatorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BugBountyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugBountyPlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner popularBanco(IEmpresaDAO empresaDAO, IPesquisadorDAO pesquisadorDAO,
	                                      IProgramaDAO programaDAO, IRelatorioDAO relatorioDAO,
	                                      IAdminDAO adminDAO, BCryptPasswordEncoder passwordEncoder) {
		return (args) -> {
			Admin admin = new Admin("admin@admin.com", passwordEncoder.encode("admin"), "ROLE_ADMIN", "Administrador Principal");
			adminDAO.save(admin);

			Empresa e1 = new Empresa("security@techcorp.com", passwordEncoder.encode("123"), "ROLE_EMPRESA", "TechCorp S.A.", "11.111.111/0001-11", "Empresa de Tecnologia e Software", "Tecnologia");
			empresaDAO.save(e1);
			Empresa e2 = new Empresa("app@finbank.com", passwordEncoder.encode("123"), "ROLE_EMPRESA", "FinBank", "22.222.222/0001-22", "Banco Digital e FinTech", "Finanças");
			empresaDAO.save(e2);
			Empresa e3 = new Empresa("contato@startupsec.com", passwordEncoder.encode("123"), "ROLE_EMPRESA", "Startup Sec", "33.333.333/0001-33", "Startup emergente de cibersegurança.", "Tecnologia");
			empresaDAO.save(e3);

			Pesquisador p1 = new Pesquisador("hack@hack.com", passwordEncoder.encode("123"), "ROLE_PESQUISADOR", "João Silva", "111.222.333-44", "11999999999", "M", LocalDate.of(1995, 5, 20));
			pesquisadorDAO.save(p1);
			Pesquisador p2 = new Pesquisador("alice@sec.com", passwordEncoder.encode("123"), "ROLE_PESQUISADOR", "Alice Martins", "555.666.777-88", "11888888888", "F", LocalDate.of(1998, 10, 15));
			pesquisadorDAO.save(p2);
			Pesquisador p3 = new Pesquisador("carlos@hacker.com", passwordEncoder.encode("123"), "ROLE_PESQUISADOR", "Carlos Souza", "999.888.777-66", "11777777777", "M", LocalDate.of(2000, 1, 1));
			pesquisadorDAO.save(p3);

			Programa prog1 = new Programa("Vulnerabilidades Web", "*.techcorp.com", new BigDecimal("5000.00"), LocalDate.of(2026, 12, 31), e1);
			programaDAO.save(prog1);
			Programa prog2 = new Programa("Teste em API PIX", "api.finbank.com", new BigDecimal("15000.00"), LocalDate.of(2026, 10, 15), e2);
			programaDAO.save(prog2);
			Programa prog3 = new Programa("Sistemas Legados", "legacy.techcorp.com", new BigDecimal("2000.00"), LocalDate.of(2026, 5, 10), e1);
			programaDAO.save(prog3);
			Programa prog4 = new Programa("App Mobile iOS", "ios.finbank.com", new BigDecimal("8000.00"), LocalDate.of(2027, 1, 1), e2);
			programaDAO.save(prog4);

			Relatorio r1 = new Relatorio("uploads/joao_poc1.pdf", StatusRelatorio.EM_TRIAGEM, LocalDateTime.now().minusDays(2), p1, prog1);
			relatorioDAO.save(r1);
			Relatorio r2 = new Relatorio("uploads/alice_poc_pix.pdf", StatusRelatorio.VULNERAVEL, LocalDateTime.now().minusDays(10), p2, prog2);
			r2.setSeveridade(Severidade.ALTA);
			r2.setRecompensa(new BigDecimal("12000.00"));
			relatorioDAO.save(r2);
			Relatorio r3 = new Relatorio("uploads/joao_legacy.pdf", StatusRelatorio.REJEITADO, LocalDateTime.now().minusMonths(1), p1, prog3);
			relatorioDAO.save(r3);
		};
	}
}