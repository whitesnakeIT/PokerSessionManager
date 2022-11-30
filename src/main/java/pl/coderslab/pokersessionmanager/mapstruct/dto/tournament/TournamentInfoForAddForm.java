package pl.coderslab.pokersessionmanager.mapstruct.dto.tournament;

import lombok.Data;

@Data
public class TournamentInfoForAddForm {

    private String name;

    private String type;

    private String speed;

    private String concatFields;

    public String getConcatFields() {
        return name + " " + type + " " + speed;
    }
}
