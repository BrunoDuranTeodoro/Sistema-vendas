package com.br.aweb.sistema_vendas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Precisar Ter O Nome!")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "precisa haver uma descrição!")
    @Column(nullable = false, length = 255)
    private String descricao;

    @Positive(message = "O Preço precisa ser positivo!")
    @NotNull(message = "Precisa digitar um preço!")
    @Column(nullable = false)
    private BigDecimal preco;

    @PositiveOrZero(message = "Quantidade invalida!")
    @NotNull(message = "precisa haver quantidade!")
    @Column(nullable = false)
    private Integer quantidadeEmEstoque;

}