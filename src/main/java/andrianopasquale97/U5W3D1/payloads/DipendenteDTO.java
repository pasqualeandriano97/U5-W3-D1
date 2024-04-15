package andrianopasquale97.U5W3D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotEmpty(message = "Username non può essere vuoto")
        @Size(message = "lo Username deve essere di almeno 3 caratteri e massimo 7", min = 3,max = 20)
        String username,
        @NotEmpty(message = "Il nome non può essere vuoto")
        @Size(message = "Il nome deve essere di almeno 3 caratteri e massimo 15", min = 3,max = 15)
        String nome,
        @NotEmpty(message = "Il cognome non può essere vuoto")
        @Size(message = "Il cognome deve essere di almeno 3 caratteri e massimo 15", min = 3)
        String cognome,
        @NotEmpty(message = "L'email non può essere vuota")
        @Email(message = "Email non valida")
        String email,
        @NotEmpty(message = "La password non può essere vuota")
                String password,
        String profileImage
) {
}
