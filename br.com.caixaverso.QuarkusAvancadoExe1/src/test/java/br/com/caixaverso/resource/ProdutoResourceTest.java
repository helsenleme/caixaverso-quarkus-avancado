package br.com.caixaverso.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProdutoResourceTest {

    @Test
    public void deveCriarProdutoComSucesso() {
        String json = """
        {
            "nome": "Café Especial",
            "descricao": "Torrado e moído",
            "preco": 19.90
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("Café Especial"));
    }

    @Test
    public void deveListarProdutos() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos")
                .then()
                .statusCode(200)
                .body("$", is(notNullValue()));
    }

    @Test
    public void deveBuscarProdutoPorId() {
        String json = """
        {
            "nome": "Notebook",
            "descricao": "Dell i7",
            "preco": 4999.90
        }
        """;

        Integer id = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .pathParam("id", id)
                .when()
                .get("/produtos/{id}")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Notebook"));
    }

    @Test
    public void deveAtualizarProduto() {
        String json = """
        {
            "nome": "Mouse",
            "descricao": "Sem fio",
            "preco": 99.90
        }
        """;

        Integer id = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        String atualizacao = """
        {
            "nome": "Mouse Gamer",
            "descricao": "RGB",
            "preco": 149.90
        }
        """;

        given()
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(atualizacao)
                .when()
                .put("/produtos/{id}")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Mouse Gamer"));
    }

    @Test
    public void deveExcluirProduto() {
        String json = """
        {
            "nome": "Teclado",
            "descricao": "Mecânico",
            "preco": 199.90
        }
        """;

        Integer id = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given()
                .pathParam("id", id)
                .when()
                .delete("/produtos/{id}")
                .then()
                .statusCode(204);
    }
}