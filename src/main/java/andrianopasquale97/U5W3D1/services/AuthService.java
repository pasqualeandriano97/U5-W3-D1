package andrianopasquale97.U5W3D1.services;

import andrianopasquale97.U5W3D1.entities.Dipendente;
import andrianopasquale97.U5W3D1.exceptions.UnauthorizedException;
import andrianopasquale97.U5W3D1.payloads.DipendentiLoginDTO;
import andrianopasquale97.U5W3D1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndGenerateToken(DipendentiLoginDTO payload){
        Dipendente dipendente = this.dipendenteService.findByEmail(payload.email());
        if(passwordEncoder.matches(payload.password(),dipendente.getPassword() )) {
            return jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }


    }
}