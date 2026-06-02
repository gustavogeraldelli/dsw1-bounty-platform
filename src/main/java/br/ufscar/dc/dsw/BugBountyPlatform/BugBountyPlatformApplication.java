package br.ufscar.dc.dsw.BugBountyPlatform;

import br.ufscar.dc.dsw.BugBountyPlatform.dao.*;
import br.ufscar.dc.dsw.BugBountyPlatform.domain.*;
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
	public CommandLineRunner demo(IEmpresaDAO empresaDAO, IPesquisadorDAO pesquisadorDAO,
								  IProgramaDAO programaDAO, IRelatorioDAO relatorioDAO) {

		return (args) -> {
			System.out.println("\nIniciando testes");

			Empresa empTech = new Empresa();
			empTech.setEmail("security@techcorp.com");
			empTech.setSenha("123");
			empTech.setRole("ROLE_EMPRESA");
			empTech.setNome("TechCorp S.A.");
			empTech.setCnpj("11.111.111/0001-11");
			empTech.setDescricao("Tecnologia");
			empTech.setSetor("Tecnologia");
			empresaDAO.save(empTech);

			Empresa empFin = new Empresa();
			empFin.setEmail("app@finbank.com");
			empFin.setSenha("123");
			empFin.setRole("ROLE_EMPRESA");
			empFin.setNome("FinBank");
			empFin.setCnpj("22.222.222/0001-22");
			empFin.setDescricao("Banco Digital");
			empFin.setSetor("Finanças");
			empresaDAO.save(empFin);

			Pesquisador hacker = new Pesquisador();
			hacker.setEmail("hacker@hunter.com");
			hacker.setSenha("123");
			hacker.setRole("ROLE_PESQUISADOR");
			hacker.setNome("João Silva");
			hacker.setCpf("111.222.333-44");
			hacker.setSexo("M");
			hacker.setDataNascimento(LocalDate.of(1995, 5, 20));
			hacker.setTelefone("11999999999");
			pesquisadorDAO.save(hacker);

			Programa progTech = new Programa();
			progTech.setTitulo("Vulnerabilidades Web");
			progTech.setEscopo("*.techcorp.com");
			progTech.setRecompensaMaxima(new BigDecimal("5000.00"));
			progTech.setDataLimite(LocalDate.of(2026, 12, 31));
			progTech.setEmpresa(empTech);
			programaDAO.save(progTech);

			Programa progFin = new Programa();
			progFin.setTitulo("Teste em API PIX");
			progFin.setEscopo("api.finbank.com");
			progFin.setRecompensaMaxima(new BigDecimal("15000.00"));
			progFin.setDataLimite(LocalDate.of(2026, 10, 15));
			progFin.setEmpresa(empFin);
			programaDAO.save(progFin);

			Relatorio relatorio1 = new Relatorio();
			relatorio1.setCaminhoArquivoPoc("/uploads/xss.pdf");
			relatorio1.setStatus(StatusRelatorio.EM_TRIAGEM);
			relatorio1.setDataSubmissao(LocalDateTime.now());
			relatorio1.setPesquisador(hacker);
			relatorio1.setPrograma(progTech);
			relatorioDAO.save(relatorio1);

			System.out.println("Dados iniciais salvos.");

			System.out.println("\nTestando consultas");

			System.out.println("Setor Finanças:");
			for (Empresa e : empresaDAO.findBySetor("Finanças")) {
				System.out.println("- " + e.getNome() + " (ID: " + e.getId() + ")");
			}

			System.out.println("\nProgramas TechCorp (ID " + empTech.getId() + "):");
			for (Programa p : programaDAO.findByEmpresa(empTech)) {
				System.out.println("- " + p.getTitulo() + " (Recompensa: R$" + p.getRecompensaMaxima() + ")");
			}

			System.out.println("\nRelatório do João Silva na TechCorp:");
			Relatorio submissao = relatorioDAO.findByPesquisadorAndPrograma(hacker, progTech);
			if (submissao != null) {
				System.out.println("- Encontrado: ID " + submissao.getId() + " | Status: " + submissao.getStatus());
			}

			System.out.println("\nTestando atualização");
			Programa progParaAtualizar = programaDAO.findById(progTech.getId()).get();
			progParaAtualizar.setRecompensaMaxima(new BigDecimal("8000.00"));
			programaDAO.save(progParaAtualizar);

			Programa progAtualizado = programaDAO.findById(progTech.getId()).get();
			System.out.println("Nova recompensa TechCorp salva: R$" + progAtualizado.getRecompensaMaxima());

			System.out.println("\nTestando exclusão");
			relatorioDAO.deleteById(relatorio1.getId());

			boolean existe = relatorioDAO.findById(relatorio1.getId()).isPresent();
			System.out.println("Relatório apagado com sucesso? " + (!existe ? "Sim" : "Não"));

			System.out.println("\nFim do teste de fluxo.");
		};
	}
}