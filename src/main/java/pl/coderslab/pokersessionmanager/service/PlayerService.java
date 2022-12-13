package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.repository.PlayerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    private final PlayerMapper playerMapper;

//    public String encodePassword(String password) {
//        return bCryptPasswordEncoder.encode(password);
//    }

    public boolean isPasswordCorrect(String passwordToCheck, String userPassword) {

        return bCryptPasswordEncoder.matches(passwordToCheck, userPassword);
    }

//    public void create(Player player) {
// if user exist then don't need to encode his password again -> details editing
//        if (findByEmail(player.getEmail()).isEmpty()) {
//            player.setPassword(bCryptPasswordEncoder.encode(player.getPassword()));
//            player.setEnabled(1); // maybe default ??
//            Role userRole = roleService.findByName("ROLE_USER");
//            player.setRoles(new HashSet<>(List.of(userRole)));
//            if password is not updated, it is not encoding second time
//        } else if (player.getPassword()
//                .equals(findByEmail(player.getEmail()).get().getPassword())) {
//            playerRepository.save(player);
    // if password is updated it have to be encoded

//        }

//        playerRepository.save(player);
//    }

    // update ??
    public boolean updatePassword(Player player, String oldPassword, String newPassword, String confirmNewPassword) {

        boolean compareActualAndOldPassword = bCryptPasswordEncoder.matches(oldPassword, player.getPassword());


        boolean compareActualAndNewPassword = bCryptPasswordEncoder.matches(newPassword, player.getPassword());
        boolean compareActualAndConfirmNewPassword = bCryptPasswordEncoder.matches(confirmNewPassword, player.getPassword());


        boolean compareNewPasswordAndConfirmNewPassword = newPassword.equals(confirmNewPassword);

        if ((oldPassword.equals("")) || (newPassword.equals("")) || (confirmNewPassword.equals(""))) {
            System.out.println("jakies polle puste");
            return false;
        }

        if (!compareActualAndOldPassword) {
            System.out.println("stare haslo bledne");
            return false;
        } else if (compareActualAndNewPassword || compareActualAndConfirmNewPassword) {
            System.out.println("Stare ok ale nowe lub confirm jak stare");
            return false;
        }

        if (!compareNewPasswordAndConfirmNewPassword) {
            System.out.println("Stare ok ale 2 rozne");
            return false;
        }

        player.setPassword(bCryptPasswordEncoder.encode(newPassword));
        playerRepository.save(player);
        return true;

    }

    public List<Player> findAll() {
        List<Player> allPlayers = playerRepository.findAll().stream().peek(this::loadLazyDataToPlayer).toList();
        return allPlayers;
    }


//    public UserBasicInfoWithPasswordDto findUserBasicInfoWithPasswordDto(Long id) {
//        Optional<User> playerOptional = playerRepository.findById(id);
//
//        if (playerOptional.isPresent()) {
//
//            return playerMapper.playerToUserBasicInfoWithPasswordDto(playerOptional.get());
//
//        }
//
//        throw new RuntimeException("I can't find/convert player by player Id.");
//    }
//
//    public UserBasicInfoWithOutPasswordDto findUserBasicInfoWithOutPasswordDtoById(Long id) {
//        Optional<User> playerOptional = playerRepository.findById(id);
//
//        if (playerOptional.isPresent()) {
//
//            return playerMapper.playerToUserBasicInfoWithOutPasswordDto(playerOptional.get());
//
//        }
//
//        throw new RuntimeException("I can't find/convert player by player Id.");
//    }
//
//    public UserBasicInfoWithPasswordDto convertUserToUserBasicInfoWithPasswordDto(Player player) {
//        return playerMapper.playerToUserBasicInfoWithPasswordDto(player);
//    }
//
//    public UserBasicInfoWithOutPasswordDto convertUserToUserBasicInfoWithOutPasswordDto(Player player) {
//        return playerMapper.playerToUserBasicInfoWithOutPasswordDto(player);
//    }

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
        loadFavouriteTournamentsToPlayer(player);
        loadSuggestedTournamentsToPlayer(player);
        loadLocalTournamentsToPlayer(player);
        loadPokerRoomLocalToPlayer(player);
        loadPlayerStatsToPlayer(player);
    }

}
