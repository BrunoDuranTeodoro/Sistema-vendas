package com.br.aweb.sistema_vendas.config;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.model.Perfil;
import com.br.aweb.sistema_vendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe o email do admin
        String emailAdmin = "admin@aweb.com.br";
        Optional<Cliente> userExistente = clienteRepository.findByEmail(emailAdmin);

        if (userExistente.isEmpty()) {
            Cliente admin = new Cliente();
            
            // Dados de Login
            admin.setEmail(emailAdmin);
            admin.setSenha(passwordEncoder.encode("admin123")); // Senha criptografada
            admin.setPerfil(Perfil.ADMIN); // Define como ADMIN
            
            // Dados Obrigatórios da entidade Cliente
            admin.setNomeCompleto("Administrador do Sistema");
            
            // CPF Válido (Gerado para teste)
            // Substituí o anterior por este que passa na validação matemática
            admin.setCpf("44622134837"); 
            
            admin.setTelefone("11999999999");
            
            // Endereço Fictício
            admin.setLogradouro("Rua Administrativa");
            admin.setNumero("100");
            admin.setBairro("Centro");
            admin.setCidade("São Paulo");
            admin.setUf("SP");
            admin.setCep("01001000");

            clienteRepository.save(admin);
            System.out.println("------------------------------------------------");
            System.out.println(" ADMINISTRADOR PADRÃO CRIADO COM SUCESSO");
            System.out.println(" Login: " + emailAdmin);
            System.out.println(" Senha: admin123");
            System.out.println("------------------------------------------------");
        }
    }
}