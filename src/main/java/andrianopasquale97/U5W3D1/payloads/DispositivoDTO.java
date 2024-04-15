package andrianopasquale97.U5W3D1.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DispositivoDTO(
        @NotEmpty(message = "Il campo tipologia non può essere vuoto")
        String tipologia,
        @NotEmpty(message = "Il campo stato non può essere vuoto")
        String stato
) {
}
