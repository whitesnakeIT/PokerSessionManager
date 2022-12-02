package pl.coderslab.pokersessionmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.coderslab.pokersessionmanager.service.TournamentService;

@Controller
@RequiredArgsConstructor
public class UnLoggedController {
private final TournamentService tournamentService;


}


