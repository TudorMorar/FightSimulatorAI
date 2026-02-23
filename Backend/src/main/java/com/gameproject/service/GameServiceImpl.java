import com.gameproject.entity.Player;
import com.gameproject.repository.PlayerRepository;
import com.gameproject.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final PlayerRepository playerRepo;

    public GameServiceImpl(PlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    @Override
    @Transactional
    public String fight(Player a, Player b) {

        if (a == null || b == null) {
            return "Player invalid!";
        }

        if (a.getId().equals(b.getId())) {
            return "Un jucător nu poate lupta singur!";
        }

        if (a.getStamina() <= 0 || b.getStamina() <= 0) {
            return "Unul din jucători nu are stamina!";
        }

        // scadem stamina
        a.setStamina(a.getStamina() - 1);
        b.setStamina(b.getStamina() - 1);

        // creștem numărul de lupte
        a.setFights(a.getFights() + 1);
        b.setFights(b.getFights() + 1);

        // winner random pt început
        Player winner = Math.random() < 0.5 ? a : b;

        if (winner == a) {
            a.setWins(a.getWins() + 1);
            b.setLoses(b.getLoses() + 1);
        } else {
            b.setWins(b.getWins() + 1);
            a.setLoses(a.getLoses() + 1);
        }

        // salvăm ambii jucători
        playerRepo.save(a);
        playerRepo.save(b);

        return "🏆 Winner: " + winner.getName();
    }
}
