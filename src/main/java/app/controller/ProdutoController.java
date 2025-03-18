package app.controller;


import app.entity.Produto;
import app.exceptions.RecursoNaoEncontradoException;
import app.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Implementar métodos para cada operação de CRUD (Create, Read, Update, Delete) do produto

    @PostMapping("/save")
    public ResponseEntity<Produto> save(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.save(produto);
            // Cria a URI do novo recurso
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}") // O segmento de caminho com o ID
                    .buildAndExpand(novoProduto.getId()) // Substitui {id} pelo ID do produto
                    .toUri();
            // Retorna o recurso criado com status 201 e o cabeçalho Location
            return ResponseEntity.created(location).body(novoProduto);
        } catch (RecursoNaoEncontradoException e) {
            // Status 404 é mais apropriado para recurso não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Ou e.getMessage() se quiser retornar a mensagem
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
            Produto produto = produtoService.findById(id);
            return ResponseEntity.ok(produto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Produto>> findAll() {
        try {
            List<Produto> produtoList = produtoService.findAll();
            return new ResponseEntity<>(produtoList,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Produto produto,@PathVariable Long id){
        try {

            String mensagem = produtoService.update(produto, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
