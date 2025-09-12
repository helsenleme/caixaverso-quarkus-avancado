package br.com.caixaverso.model;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Produto extends PanacheEntity {

    @NotBlank(message = "O nome é obrigatório")
    public String nome;

    public String descricao;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 1, message = "O preço deve ser maior que zero")
    public Double preco;
}