package uefa.teams.microservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.models.Uefa;

import java.util.List;


@Repository
public interface UefaDAO extends JpaRepository<Uefa, Integer> {
    @Query("select t from Team t join t.uefas u where t.id = :id")
    List<Team> listTeamsWonUefas(final Integer id);
}
