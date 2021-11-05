package uefa.teams.microservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.models.Uefa;

import java.util.List;

@Repository
public interface TeamDAO extends JpaRepository<Team, Integer> {
    @Query("select u from Uefa u where u.teamChampion.id=:id")
    List<Uefa> listUefasWonByTeam(final Integer id);
}
