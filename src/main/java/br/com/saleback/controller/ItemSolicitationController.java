package br.com.saleback.controller;

import br.com.saleback.exception.NotFoundException;
import br.com.saleback.exception.ResourceExceptionHandler;
import br.com.saleback.model.ItemSolicitation;
import br.com.saleback.service.ItemSolicitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.UUID;

@ResponseBody
@RestController
@RequestMapping("/item-solicitation")
public class ItemSolicitationController extends ResourceExceptionHandler {

    @Autowired
    private ItemSolicitationService itemSolicitationService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ItemSolicitation itemSolicitation, ServletWebRequest request) {
        return saveOrEdit(itemSolicitation, request, false);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ItemSolicitation itemSolicitation, ServletWebRequest request) {
        return saveOrEdit(itemSolicitation, request, true);
    }

    private ResponseEntity<?> saveOrEdit(ItemSolicitation itemSolicitation, ServletWebRequest request, Boolean edit) {
        try {
            Boolean saveOk = itemSolicitationService.save(itemSolicitation) != null;
            if(saveOk) {
                String msg = edit? "editado": "salvo";
                return ResponseEntity.ok("Itens pedido " + msg + " com sucesso.");
            }
            return ResponseEntity.badRequest().body("Ocorreu um erro");
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

    @GetMapping()
    public List<ItemSolicitation> getItemsOrder() {
        return itemSolicitationService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ItemSolicitation findById(@PathVariable UUID id) {
        return itemSolicitationService.findById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id, ServletWebRequest request) {
        try {
            String deleteOk = itemSolicitationService.deleteById(id);
            if("OK".equals(deleteOk)) {
                return ResponseEntity.ok("Item pedido removido com sucesso");
            }
            return ResponseEntity.badRequest().body(deleteOk);
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

}
