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
        return playerRepository
                .findAll()
                .stream()
                .peek(this::loadLazyDataToPlayer)
                .toList();
    }

    public Player findById(Long playerId) {
        if (playerId == null) {
            throw new RuntimeException("Searching for player failed. Player id is null.");
        }
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Searching for player failed. Unrecognized player id: " + playerId));
        loadLazyDataToPlayer(player);

        return player;
    }

    public void loadLazyDataToPlayer(Player player) {
        loadSessionsToPlayer(player);
        loadFavouriteTournamentsToPlayer(player);
        loadSuggestedTournamentsToPlayer(player);
        loadLocalTournamentsToPlayer(player);
        loadPokerRoomLocalToPlayer(player);
        loadPlayerStatsToPlayer(player);
    }

    private void loadFavouriteTournamentsToPlayer(Player player) {
        Hibernate.initialize(player.getFavouriteTournaments());
    }

    private void loadSuggestedTournamentsToPlayer(Player player) {
        Hibernate.initialize(player.getSuggestedTournaments());
    }

    private void loadLocalTournamentsToPlayer(Player player) {
        Hibernate.initialize(player.getLocalTournaments());
    }

    private void loadPokerRoomLocalToPlayer(Player player) {
        Hibernate.initialize(player.getPokerRoomsLocal());
    }

    private void loadPlayerStatsToPlayer(Player player) {
        Hibernate.initialize(player.getPlayerStats());
    }

    private void loadSessionsToPlayer(Player player) {
        Hibernate.initialize(player.getSessions());
    }
}
