package uefa.teams.microservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import uefa.teams.microservice.dao.TeamDAO;

import static org.assertj.core.api.Assertions.assertThat;

/**********************
 *
 * Utilizamos la anotación @DataJpaTest en vez de la anotación @SpringBootTest, con la finalidad que se puedea usar
 * si la prueba se enfoca solo en componentes JPA. Al usar esta anotación, la base de datos H2, Hibernate y Spring Data
 * se configuran automáticamente para la prueba. El registro de SQL también está activado.
 * Las pruebas son transaccionales por defecto y se retrotraen al final del caso de prueba.
 *
 */

@DataJpaTest
public class TeamRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamDAO teamDAO;

    /**
     * Añadimos las funciones para las pruebas CRUD
     */
    @Test
    public void existsTeams() {
        assertThat(teamDAO.findAll()).isNotEmpty();
    }

    @Test
    public void findTeam() {
        assertThat(teamDAO.findById(1)).isNotNull();
    }
}
