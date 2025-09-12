package br.com.caixaverso.dto;

import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(name = "ProdutoDTO", description = "Dados para criação e atualização de produtos")
public class ProdutoDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Schema(description = "Nome do produto", example = "Café Especial")
    public String nome;

    @Schema(description = "Descrição do produto", example = "Torrado e moído")
    public String descricao;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 1, message = "O preço deve ser maior que zero")
    @Schema(description = "Preço do produto", example = "19.90")
    public Double preco;
}