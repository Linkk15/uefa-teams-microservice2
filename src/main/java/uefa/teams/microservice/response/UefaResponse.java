package uefa.teams.microservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uefa.teams.microservice.models.Team;

import java.util.Date;

@Getter
@Setter
public class UefaResponse {

    @JsonProperty("uefa")
    private Integer idUefa;

    @JsonProperty("dateWin")
    private Date dateWin;

    @JsonProperty("teamWin")
    private Team teamWin;
}