//package pl.coderslab.pokersessionmanager.service;
//
//import org.assertj.core.api.Assertions;
//import org.hibernate.Hibernate;
//import org.junit.jupiter.api.Test;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import pl.coderslab.pokersessionmanager.entity.PokerRoom;
//import pl.coderslab.pokersessionmanager.entity.Role;
//import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
//import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
//import pl.coderslab.pokersessionmanager.entity.user.Player;
//import pl.coderslab.pokersessionmanager.entity.user.PlayerStats;
//import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
//import pl.coderslab.pokersessionmanager.enums.RoleName;
//import pl.coderslab.pokersessionmanager.repository.PlayerRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@SpringBootTest
//class PlayerServiceTest {
//
//
//    @InjectMocks
//    private PlayerService playerService;
//
//    @Mock
//    private PlayerRepository playerRepository;
//
//    private static final Long playerId = 1L;
//
//    public List<Player> preparePlayerList() {
//        return List.of(new Player(), new Player(), new Player());
//    }
//
//    public Player prepareTestPlayer() {
//
//        Player testPlayer = new Player();
//        testPlayer.setId(playerId);
////        testPlayer.setPassword("testPassword");
////        testPlayer.setUsername("testUsername");
////        testPlayer.setFirstName("testFirstName");
////        testPlayer.setLastName("testLastName");
////        testPlayer.setFullName(String.join(" ", testPlayer.getFirstName(), testPlayer.getLastName()));
////        testPlayer.setEmail("testEmail@test.com");
////        testPlayer.setEnabled(1);
////        testPlayer.setBirthdayDate(LocalDate.of(2000, 1, 1));
////        testPlayer.setCreated(LocalDateTime.of(2023, 6, 3, 14, 55, 0));
////        testPlayer.setLocalTournaments(List.of(new TournamentLocal(), new TournamentLocal()));
////        testPlayer.setSuggestedTournaments(List.of(new TournamentSuggestion(), new TournamentSuggestion()));
////        PlayerStats testPlayerStats = new PlayerStats();
////        testPlayerStats.setPlayer(testPlayer);
////        testPlayerStats.setId(1L);
////        testPlayerStats.setProfit(10.000);
////        testPlayerStats.setBalance(50.000);
////        testPlayerStats.setAverageProfit(1000);
////        testPlayerStats.setTournamentCount(10);
////        testPlayerStats.setTournamentWins(1);
////        testPlayer.setPlayerStats(testPlayerStats);
////        testPlayer.setFavouriteTournaments(List.of(new TournamentSuggestion(), new TournamentLocal()));
////        PokerRoom testPokerRoom = new PokerRoom();
////        testPokerRoom.setPlayer(testPlayer);
////        testPokerRoom.setPokerRoomScope(PokerRoomScope.LOCAL);
////        testPokerRoom.setName("testPokerRoomName");
////        testPokerRoom.setUrl("https://www.test-poker-room-url.com");
////        testPokerRoom.setTournamentList(List.of(new TournamentLocal()));
////        testPlayer.setPokerRoomsLocal(List.of(testPokerRoom));
////        Role rolePlayer = new Role();
////        rolePlayer.setId(3L);
////        rolePlayer.setName(RoleName.ROLE_USER.name());
////        testPlayer.setRoles(new HashSet<>(Set.of(rolePlayer)));
//        return testPlayer;
//    }
//
//    @Test
//    void findAllPlayers() {
//        //given
//        BDDMockito.given(playerRepository.findAll())
//                .willReturn(preparePlayerList());
//
//        //when
//        List<Player> players = playerService.findAllPlayers();
//
//        //then
//        Assertions.assertThat(players).hasSize(3);
//    }
//
//    @Test
//    void findById() {
//
//
//        BDDMockito.given(playerRepository.findById(playerId))
//                .willReturn(Optional.of(prepareTestPlayer()));
//
//
//        Player player = playerService.findById(playerId);
//
//
//        Assertions.assertThat(playerRepository.findById(playerId).get())
//                .isEqualTo(player);
//
//        Assertions.assertThat(playerRepository.findById(2L))
//                .isEqualTo(Optional.empty());
//
//    }
//
//    @Test
//    void ifPlayerExistFalse() {
//        //given
//
//        Optional<Player> emptyPlayerOptional = Optional.empty();
//
//        // when
//
//        Throwable throwable = Assertions.catchThrowable(() ->
//                playerService.ifPlayerExist(emptyPlayerOptional));
//
//        //then
//        Assertions.assertThat(throwable)
////                .isInstanceOf(RuntimeException.class)
//
//                .hasMessage("Player not exist.")
//        ;
//
//    }
//
//    @Test
//    void ifPlayerExistTrue() {
//        //given
//
//        Optional<Player> presentPlayerOptional = Optional.ofNullable(prepareTestPlayer());
//
//        // when
//
//        Player player = playerService.ifPlayerExist(presentPlayerOptional);
//
//        //then
//        Assertions.assertThat(player.getId())
//                .isEqualTo(playerId);
//
//    }
//  @Test
//    void ifPlayerExistNull() {
//        //given
//
//        Optional<Player> nullPlayerOptional = null;
//
////        // when
////
////        Throwable throwable = playerService.ifPlayerExist(nullPlayerOptional);
////
////        //then
////        Assertions.assertThat(player.getId())
////                .isEqualTo(1L);
//
//    }
//
//    @Test
//    void loadFavouriteTournamentsToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized = Hibernate.isInitialized(player.getFavouriteTournaments());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//
//    }
//
//    @Test
//    void loadSuggestedTournamentsToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized = Hibernate.isInitialized(player.getSuggestedTournaments());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//    }
//
//    @Test
//    void loadLocalTournamentsToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized = Hibernate.isInitialized(player.getLocalTournaments());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//    }
//
//    @Test
//    void loadPokerRoomLocalToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized = Hibernate.isInitialized(player.getPokerRoomsLocal());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//    }
//
//    @Test
//    void loadPlayerStatsToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized = Hibernate.isInitialized(player.getPlayerStats());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//    }
//
//    @Test
//    void loadSessionsToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized = Hibernate.isInitialized(player.getSessions());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//    }
//
//    @Test
//    void loadLazyDataToPlayer() {
//        // given
//        Player player = prepareTestPlayer();
//
//        // when
//        boolean isInitialized =
//                Hibernate.isInitialized(player.getFavouriteTournaments()) &&
//                        Hibernate.isInitialized(player.getSuggestedTournaments()) &&
//                        Hibernate.isInitialized(player.getLocalTournaments()) &&
//                        Hibernate.isInitialized(player.getPokerRoomsLocal()) &&
//                        Hibernate.isInitialized(player.getPlayerStats()) &&
//                        Hibernate.isInitialized(player.getSessions());
//
//        //then
//        Assertions.assertThat(isInitialized).isTrue();
//    }
//}