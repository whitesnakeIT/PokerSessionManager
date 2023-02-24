package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.repository.PlayerRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;


    public List<Player> findAllPlayers() {
        List<Player> allPlayers = playerRepository.findAll().stream().peek(this::loadLazyDataToPlayer).toList();
        return allPlayers;
    }

    public void loadFavouriteTournamentsToPlayer(Player player) {
        Hibernate.initialize(player.getFavouriteTournaments());
    }

    public void loadSuggestedTournamentsToPlayer(Player player) {
        Hibernate.initialize(player.getSuggestedTournaments());
    }

    public void loadLocalTournamentsToPlayer(Player player) {
        Hibernate.initialize(player.getLocalTournaments());
    }

    public void loadPokerRoomLocalToPlayer(Player player) {
        Hibernate.initialize(player.getPokerRoomsLocal());
    }

    public void loadPlayerStatsToPlayer(Player player) {
        Hibernate.initialize(player.getPlayerStats());
    }

    public void loadSessionsToPlayer(Player player) {
        Hibernate.initialize(player.getSessions());
    }

    public void loadLazyDataToPlayer(Player player) {
        loadSessionsToPlayer(player);
//        Hibernate.initialize(player.getSessions());
        loadFavouriteTournamentsToPlayer(player);
        loadSuggestedTournamentsToPlayer(player);
        loadLocalTournamentsToPlayer(player);
        loadPokerRoomLocalToPlayer(player);
        loadPlayerStatsToPlayer(player);
    }

}
