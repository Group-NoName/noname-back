package com.noname.uol.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noname.uol.entidades.Produtos;
import com.noname.uol.modelo.ProdutoSelecionado;
import com.noname.uol.repositorios.produtosRepositorio;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoControles {
	@Autowired
	private produtosRepositorio repositorio;
	@Autowired
	private ProdutoSelecionado selecionador;
	
	@GetMapping("/produtos")
	public List<Produtos> obterProdutos() {
		List<Produtos> produto = repositorio.findAll();
		return produto;
	}
	@PostMapping("/cadastro")
	public void cadastrarProduto(@RequestBody Produtos produto) {
		repositorio.insert(produto);
	}
	@DeleteMapping("/excluir/{id}")
	public void excluirProduto(@PathVariable String id) {
		repositorio.deleteById(id);
	}
}
