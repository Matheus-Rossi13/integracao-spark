package com.exercicio3;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        
        port(1345);
        
      
        staticFileLocation("/public");
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        // Rota para exibir o formulário de cadastro de produto
        get("/produto/novo", (req, res) -> {
            return new ModelAndView(null, "novoProduto.html");
        }, new VelocityTemplateEngine());
        
        // Rota para processar o envio do formulário 
        post("/produto", (req, res) -> {
            String nome = req.queryParams("nome");
            String descricao = req.queryParams("descricao");
            double preco = Double.parseDouble(req.queryParams("preco"));
            
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            
            produtoDAO.create(produto);
            
            res.redirect("/produtos");
            return null;
        });
        
        
        get("/produtos", (req, res) -> {
            List<Produto> produtos = produtoDAO.readAll();
            Map<String, Object> model = new HashMap<>();
            model.put("produtos", produtos);
            return new ModelAndView(model, "listaProdutos.vm");
        }, new VelocityTemplateEngine());
        
       
    }
}
