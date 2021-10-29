package uefa.teams.microservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uefa.teams.microservice.models.Team;

@Repository
public interface TeamDAO extends JpaRepository<Team, Integer> {

}
