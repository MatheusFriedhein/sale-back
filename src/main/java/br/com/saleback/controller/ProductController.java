package br.com.saleback.controller;

import br.com.saleback.exception.NotFoundException;
import br.com.saleback.exception.ResourceExceptionHandler;
import br.com.saleback.model.Product;
import br.com.saleback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.UUID;

@ResponseBody
@RestController
@RequestMapping("/product")
public class ProductController extends ResourceExceptionHandler {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Product product, ServletWebRequest request) {
        return saveOrEdit(product, request, false);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody Product product, ServletWebRequest request) {
        return saveOrEdit(product, request, true);
    }

    private ResponseEntity<?> saveOrEdit(Product product, ServletWebRequest request, Boolean edit) {
        try {
            Boolean saveOk = productService.save(product) != null;
            if (saveOk) {
                String msg = edit ? "editado" : "salvo";
                return ResponseEntity.ok("Produto " + msg + " com sucesso.");
            }
            return ResponseEntity.badRequest().body("Ocorreu um erro");
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Product findById(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id, ServletWebRequest request) {
        try {
            String deleteOk = productService.deleteById(id);
            if ("OK".equals(deleteOk)) {
                return ResponseEntity.ok("Produto removido com sucesso");
            }
            return ResponseEntity.badRequest().body(deleteOk);
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

}
