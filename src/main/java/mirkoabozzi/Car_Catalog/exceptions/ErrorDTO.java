package mirkoabozzi.Car_Catalog.exceptions;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime timeStamp) {
}
