
package football_manager;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final List<Player> players = new ArrayList<>();
    private long nextId = 1;

    public List<Player> getAllPlayers() {
        return players;
    }

    public Optional<Player> getPlayerById(Long id) {
        return players.stream()
                .filter(player -> player.getId().equals(id))
                .findFirst();
    }

    public Player addPlayer(Player player) {
        player.setId(nextId++);
        players.add(player);
        return player;
    }

    public boolean updatePlayer(Long id, Player updatedPlayer) {
        Optional<Player> existing = getPlayerById(id);

        if (existing.isEmpty()) {
            return false;
        }

        Player player = existing.get();
        player.setName(updatedPlayer.getName());
        player.setPosition(updatedPlayer.getPosition());
        player.setJerseyNumber(updatedPlayer.getJerseyNumber());
        player.setTeam(updatedPlayer.getTeam());

        return true;
    }

    public boolean deletePlayer(Long id) {
        return players.removeIf(player -> player.getId().equals(id));
    }
}
