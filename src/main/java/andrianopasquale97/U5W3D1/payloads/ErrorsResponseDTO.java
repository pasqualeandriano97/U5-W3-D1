package andrianopasquale97.U5W3D1.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(
        String message,
        LocalDateTime timeStamp
) {
}
