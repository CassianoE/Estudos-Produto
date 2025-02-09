package app.service;

import app.entity.Produto;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public String save(Produto produto){

        this.produtoRepository.save(produto);
        return "Produto salvo com sucesso!";
    }

    public Produto findById(Long id){

        Produto produto = this.produtoRepository.findById(id).get();
        return produto;
    }

    public List<Produto> findAll(){

        List<Produto> produtoList = this.produtoRepository.findAll();
        return produtoList;
    }

    public String update(Produto produto,Long id){

        produto.setId(id);
        this.produtoRepository.save(produto);
        return "Produto atualizado com sucesso!";
    }

    public String delete(Long id){

        this.produtoRepository.deleteById(id);
        return "Produto excluído com sucesso!";
    }

}
