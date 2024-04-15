package andrianopasquale97.U5W3D1.controllers;



import andrianopasquale97.U5W3D1.entities.Dispositivo;
import andrianopasquale97.U5W3D1.payloads.DispositivoDTO;
import andrianopasquale97.U5W3D1.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public DispositivoDTO save(@RequestBody @Validated DispositivoDTO newDispositivo) {
        this.dispositivoService.save(newDispositivo);
        return newDispositivo;
    }

    @GetMapping("")
    public Page<Dispositivo> getAllAuthor(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return this.dispositivoService.getDispositivi(page, size, sortBy);
    }
    // 3. - GET http://localhost:3001/dipendenti/{id}
    @GetMapping("/{dispositivoId}")
    public Dispositivo findById(@PathVariable int dipendenteId) throws Exception {
        return dispositivoService.getDispositivoById(dipendenteId);
    }
    // 4. - PUT http://localhost:3001/dipendenti/{id} (+ req.bod)
    @PutMapping("/{dispositivoId}")
    public Dispositivo findAndUpdate(@PathVariable int dispositivoId, @RequestBody DispositivoDTO body) throws Exception {
        return dispositivoService.update(dispositivoId, body);
    }
    // 5. - DELETE http://localhost:3001/dipendenti/{id}
    @DeleteMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int dispositivoId) {
        dispositivoService.findByIdAndDelete(dispositivoId);
    }

    // 6. - POST http://localhost:3001/dipendenti/image/{id} (+ req.body)
    @PatchMapping("/upload/{dispositivoId}/{dipendenteId}")
    public Dispositivo uploadAssign( @PathVariable int dispositivoId,@PathVariable int dipendenteId) throws IOException {
        return this.dispositivoService.findAndAssociate(dispositivoId, dipendenteId);
    }
    @PatchMapping("/disassociate/{dispositivoId}")
    public Dispositivo disassociate(@PathVariable int dispositivoId) throws IOException {
        return this.dispositivoService.findAndDisassociate(dispositivoId);
    }
    @PatchMapping("/dismiss/{dispositivoId}")
    public Dispositivo dismiss(@PathVariable int dispositivoId) throws IOException {
        return this.dispositivoService.findAndDismiss(dispositivoId);
    }
    @PatchMapping("/manitence/{dispositivoId}")
    public Dispositivo manitence(@PathVariable int dispositivoId) throws IOException {
        return this.dispositivoService.findAndManitence(dispositivoId);
    }

}
