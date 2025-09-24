package br.com.caixaverso.resource;

import br.com.caixaverso.dto.ProdutoDTO;
import br.com.caixaverso.model.Produto;
import br.com.caixaverso.service.ProdutoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.security.Authenticated;

import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated // Garante que todos os métodos exigem autenticação
public class ProdutoResource {

    @Inject
    ProdutoService service;

    @GET
    @RolesAllowed({"admin", "user"})
    public List<Produto> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    public Response buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id)
                .map(produto -> Response.ok(produto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @RolesAllowed("admin")
    public Response criar(@Valid ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.nome = dto.nome;
        produto.descricao = dto.descricao;
        produto.preco = dto.preco;

        Produto salvo = service.salvar(produto);
        return Response.status(Response.Status.CREATED).entity(salvo).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response atualizar(@PathParam("id") Long id, @Valid ProdutoDTO dto) {
        Produto dadosAtualizados = new Produto();
        dadosAtualizados.nome = dto.nome;
        dadosAtualizados.descricao = dto.descricao;
        dadosAtualizados.preco = dto.preco;

        return service.atualizar(id, dadosAtualizados)
                .map(produto -> Response.ok(produto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response excluir(@PathParam("id") Long id) {
        boolean removido = service.excluir(id);
        if (!removido)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}