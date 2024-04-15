package andrianopasquale97.U5W3D1.controllers;

import andrianopasquale97.U5W3D1.exceptions.BadRequestException;
import andrianopasquale97.U5W3D1.payloads.DipendenteDTO;
import andrianopasquale97.U5W3D1.payloads.DipendenteRespDTO;
import andrianopasquale97.U5W3D1.payloads.DipendentiLoginDTO;
import andrianopasquale97.U5W3D1.payloads.DipendentiLoginResponseDTO;
import andrianopasquale97.U5W3D1.services.AuthService;
import andrianopasquale97.U5W3D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendentiLoginResponseDTO login(@RequestBody DipendentiLoginDTO payload){
        return new DipendentiLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteRespDTO saveUser(@RequestBody @Validated DipendenteDTO body, BindingResult validation){
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new DipendenteRespDTO(this.dipendenteService.save(body).email());
    }

}
