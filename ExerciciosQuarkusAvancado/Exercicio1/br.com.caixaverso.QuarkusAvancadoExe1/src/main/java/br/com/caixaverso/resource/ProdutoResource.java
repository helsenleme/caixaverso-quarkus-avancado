package br.com.caixaverso.resource;

import br.com.caixaverso.dto.ProdutoDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import br.com.caixaverso.model.Produto;

import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> listar() {
        return Produto.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(produto).build();
    }

    @POST
    @Transactional
    public Response criar(@Valid ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.nome = dto.nome;
        produto.descricao = dto.descricao;
        produto.preco = dto.preco;
        produto.persist();
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, @Valid ProdutoDTO dto) {
        Produto produto = Produto.findById(id);
        if (produto == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        produto.nome = dto.nome;
        produto.descricao = dto.descricao;
        produto.preco = dto.preco;
        return Response.ok(produto).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        boolean removido = Produto.deleteById(id);
        if (!removido)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}