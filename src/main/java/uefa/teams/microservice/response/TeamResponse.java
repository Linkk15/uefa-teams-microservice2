package uefa.teams.microservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uefa.teams.microservice.models.Country;
import uefa.teams.microservice.models.Rival;

import java.util.Date;

@Getter
@Setter
public class TeamResponse {

    @JsonProperty("team")
    private Integer idTeam;

    @JsonProperty("name")
    private String nameTeam;

    @JsonProperty("rival")
    private Rival rivalTeam;

    @JsonProperty("photo")
    private Integer photoTeam;

    @JsonProperty("country")
    private Country countryTeam;

    @JsonProperty("uefaChampion")
    private Date dateWonUefa;
}