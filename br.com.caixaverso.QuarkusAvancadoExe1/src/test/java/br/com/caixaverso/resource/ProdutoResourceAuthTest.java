package br.com.caixaverso.resource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class ProdutoResourceAuthTest {

    // Tokens JWT simulados — substitua por tokens reais gerados pelo Keycloak
    private static final String TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."; // com role "admin"
    private static final String TOKEN_USER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";  // com role "user"

    @Test
    public void devePermitirListarProdutosComUsuarioAutenticado() {
        RestAssured.given()
                .auth().oauth2(TOKEN_USER)
                .when().get("/produtos")
                .then().statusCode(200);
    }

    @Test
    public void deveNegarCriacaoDeProdutoParaUsuarioComum() {
        RestAssured.given()
                .auth().oauth2(TOKEN_USER)
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Teste\",\"descricao\":\"Teste\",\"preco\":10.0}")
                .when().post("/produtos")
                .then().statusCode(403); // acesso negado
    }

    @Test
    public void devePermitirCriacaoDeProdutoParaAdmin() {
        RestAssured.given()
                .auth().oauth2(TOKEN_ADMIN)
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Produto Admin\",\"descricao\":\"Teste\",\"preco\":99.9}")
                .when().post("/produtos")
                .then().statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void deveNegarAcessoSemToken() {
        RestAssured.given()
                .when().get("/produtos")
                .then().statusCode(401); // não autenticado
    }
}