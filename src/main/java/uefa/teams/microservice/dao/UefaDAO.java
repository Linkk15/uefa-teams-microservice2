package uefa.teams.microservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uefa.teams.microservice.models.Uefa;

import java.util.List;


@Repository
public interface UefaDAO extends JpaRepository<Uefa, Integer> {
    @Query("select u from Uefa t join t.teamChampion u where t.id = :id")
    List<Uefa> listTeamsWonUefas(final Integer id);
}
