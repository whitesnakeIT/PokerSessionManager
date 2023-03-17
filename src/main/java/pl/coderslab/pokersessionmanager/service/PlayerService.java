package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

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
        Optional<Player> playerOptional = playerRepository.findById(playerId);

        return ifPlayerExist(playerOptional);
    }

    public Player ifPlayerExist(Optional<Player> playerOptional) {
        if (playerOptional==null) {
            throw new RuntimeException("Player is null.");
        }
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            loadLazyDataToPlayer(player);

            return player;
        }

        throw new RuntimeException("Player not exist.");
    }

    private void loadFavouriteTournamentsToPlayer(Player player) { Hibernate.initialize(player.getFavouriteTournaments());}

    private void loadSuggestedTournamentsToPlayer(Player player) { Hibernate.initialize(player.getSuggestedTournaments()); }

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

    public void loadLazyDataToPlayer(Player player) {
        loadSessionsToPlayer(player);
        loadFavouriteTournamentsToPlayer(player);
        loadSuggestedTournamentsToPlayer(player);
        loadLocalTournamentsToPlayer(player);
        loadPokerRoomLocalToPlayer(player);
        loadPlayerStatsToPlayer(player);
    }

}
