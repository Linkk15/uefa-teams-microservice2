package uefa.teams.microservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uefa.teams.microservice.controller.TeamController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UefaTeamsMicroservice2ApplicationTests {

    @Autowired
    private TeamController teamController;

    @Test
    void contextLoads() {
        assertThat(teamController).isNotNull();
    }

}
