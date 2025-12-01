// src/main/java/com/br/aweb/sistema_vendas/model/Pedido.java
package com.br.aweb.sistema_vendas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    private StatusPedido status = StatusPedido.ATIVO;

    @Column(nullable = false)
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public void recalcularValorTotal() {
        this.valorTotal = itens.stream()
                               .map(ItemPedido::getValorTotal)
                               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
        recalcularValorTotal();
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        item.setPedido(null);
        recalcularValorTotal();
    }
}