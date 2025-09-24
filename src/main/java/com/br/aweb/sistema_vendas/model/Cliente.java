package com.br.aweb.sistema_vendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe seu nome completo")
    @Column(nullable = false, length = 150)
    private String nomeCompleto;

    @NotBlank(message = "Informe seu email")
    @Email(message = "Insira um e-mail no formato correto exemple@gmail.com")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "Insira um CPF válido.")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "Informe seu telefone no padrão (XX)XXXX-XXXX")
    @Column(nullable = false, length = 20)
    private String telefone;

    @NotBlank(message = "Informe seu logradouro")
    @Column(nullable = false, length = 150)
    private String logradouro;

    @NotBlank(message = "Informe seu numero")
    @Column(length = 1000)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @NotBlank(message = "Informe seu bairro")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "Informe sua cidade")
    @Column(nullable = false, length = 100)
    private String cidade;

    @NotBlank(message = "Informe sua Unidade de Federação")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 caracteres.")
    @Column(nullable = false, length = 2)
    private String uf;

    @NotBlank(message = "Informe seu CEP")
    @Column(nullable = false, length = 9)
    private String cep;
}