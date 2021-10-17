package br.com.saleback.controller;

import br.com.saleback.dto.ItemSolicitationDTO;
import br.com.saleback.exception.NotFoundException;
import br.com.saleback.exception.ResourceExceptionHandler;
import br.com.saleback.model.Solicitation;
import br.com.saleback.service.SolicitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.UUID;

@ResponseBody
@RestController
@RequestMapping("/solicitation")
public class SolicitationController extends ResourceExceptionHandler  {

    @Autowired
    private SolicitationService solicitationService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Solicitation solicitation, ServletWebRequest request) {
        return saveOrEdit(solicitation, request, false);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody Solicitation solicitation, ServletWebRequest request) {
        return saveOrEdit(solicitation, request, true);
    }

    private ResponseEntity<?> saveOrEdit(Solicitation solicitation, ServletWebRequest request, Boolean edit) {
        try {
            Boolean saveOk = solicitationService.save(solicitation) != null;
            if(saveOk) {
                String msg = edit? "editado": "salvo";
                return ResponseEntity.ok("Pedido " + msg + " com sucesso.");
            }
            return ResponseEntity.badRequest().body("Ocorreu um erro");
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

    @GetMapping("/")
    public List<Solicitation> getOrders() {
        return solicitationService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Solicitation findById(@PathVariable UUID id) {
        return solicitationService.findById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id, ServletWebRequest request) {
        try {
            String deleteOk = solicitationService.deleteById(id);
            if("OK".equals(deleteOk)) {
                return ResponseEntity.ok("Pedido removido com sucesso");
            }
            return ResponseEntity.badRequest().body(deleteOk);
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

    @PutMapping("/apply-discount")
    public ResponseEntity<?> saveItemsApplyDiscount(@RequestBody ItemSolicitationDTO itemSolicitationDTO, ServletWebRequest request) {
        try {
            solicitationService.saveItemsApplyDiscount(itemSolicitationDTO);
            return ResponseEntity.ok("Itens adicionados com sucesso");
        } catch (Exception e) {
            return handleBadRequest(new NotFoundException(e.getMessage()), request);
        }
    }

}
