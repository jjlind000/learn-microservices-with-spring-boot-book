package microservices.book.java_22.gamification.game;

/**
 * This class implements a REST API for the Gamification LeaderBoard
 * service.
 */

import lombok.RequiredArgsConstructor;
import microservices.book.java_22.gamification.game.domain.LeaderBoardRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        return leaderBoardService.getCurrentLeaderBoard();
    }
}