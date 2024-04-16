package andrianopasquale97.U5W3D1.controllers;



import andrianopasquale97.U5W3D1.entities.Dipendente;
import andrianopasquale97.U5W3D1.exceptions.BadRequestException;
import andrianopasquale97.U5W3D1.payloads.DipendenteDTO;
import andrianopasquale97.U5W3D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {

    @Autowired
    private DipendenteService dipendenteService;
    // 2. - GET http://localhost:3001/dipendenti
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.getDipendenti(page, size, sortBy);
    }
    // 3. - GET http://localhost:3001/dipendenti/{id}
    @GetMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findById(@PathVariable int dipendenteId) throws Exception {
        return dipendenteService.getDipendenteById(dipendenteId);
    }
    // 4. - PUT http://localhost:3001/dipendenti/{id} (+ req.bod)
    @PutMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DipendenteDTO findAndUpdate(@PathVariable int dipendenteId, @RequestBody DipendenteDTO body) throws Exception {
        return dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }
    // 5. - DELETE http://localhost:3001/dipendenti/{id}
    @DeleteMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int dipendenteId) {
        dipendenteService.findByIdAndDelete(dipendenteId);
    }

    // 6. - POST http://localhost:3001/dipendenti/image/{id} (+ req.body)
    @PostMapping("/upload/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente uploadAvatar(@RequestParam("profileImage") MultipartFile image, @PathVariable int dipendenteId) throws IOException {
        return this.dipendenteService.uploadAuthorImage(image, dipendenteId);
    }

    @GetMapping("/me")
    public Dipendente getMe(@AuthenticationPrincipal Dipendente dipendente) {
        return dipendente;
    }

    @PutMapping("/me")
    public Dipendente updateMe(@AuthenticationPrincipal Dipendente dipendente, @Validated @RequestBody DipendenteDTO body) {
        this.dipendenteService.findByIdAndUpdate(dipendente.getId(), body);
        return dipendente;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedUser){
        this.dipendenteService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

}
