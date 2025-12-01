package com.br.aweb.sistema_vendas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "clientes")
public class Cliente implements UserDetails { // Implementa UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedido = new ArrayList<>();

    @NotBlank(message = "O nome completo é obrigatório.")
    @Column(nullable = false, length = 150)
    private String nomeCompleto;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Insira um e-mail válido.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // NOVO CAMPO: Senha
    @NotBlank(message = "A senha é obrigatória.")
    @Column(nullable = false)
    private String senha;

    // NOVO CAMPO: Perfil (padrão será CLIENTE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil = Perfil.CLIENTE;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "Insira um CPF válido.")
    @Size(min = 11, max = 11)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Column(nullable = false)
    private String logradouro;

    private String numero;
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Column(nullable = false)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Column(nullable = false)
    private String cidade;

    @NotBlank(message = "A UF é obrigatória.")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 caracteres.")
    @Column(nullable = false, length = 2)
    private String uf;

    @NotBlank(message = "O CEP é obrigatório.")
    @Column(nullable = false)
    private String cep;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; 
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}