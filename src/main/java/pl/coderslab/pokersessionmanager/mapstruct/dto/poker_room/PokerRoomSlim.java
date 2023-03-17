package pl.coderslab.pokersessionmanager.mapstruct.dto.poker_room;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * A DTO for the {@link pl.coderslab.pokersessionmanager.entity.PokerRoom} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokerRoomSlim implements Serializable {
    private Long id;
    @NotEmpty
    private String name;
    @URL
    @NotEmpty
    private String url;
}