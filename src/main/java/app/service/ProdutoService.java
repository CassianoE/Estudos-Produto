package app.service;

import app.entity.Produto;
import app.exceptions.RecursoNaoEncontradoException;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto findById(Long id){

        return produtoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID " + id + " nao encontrado"));
    }

    public List<Produto> findAll(){

        List<Produto> produtoList = produtoRepository.findAll();
        return produtoList;
    }

    public String update(Produto produto,Long id){

        produto.setId(id);
        produtoRepository.save(produto);
        return "Produto atualizado com sucesso!";
    }

    public void delete(Long id){

        if (!produtoRepository.existsById(id)){
            throw new RecursoNaoEncontradoException("Produto com ID " + id + " nao encontrado");
        }
        produtoRepository.deleteById(id);
    }

}
