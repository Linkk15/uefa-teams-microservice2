package uefa.teams.microservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponse {

    @JsonProperty("team")
    private Long idTeam;

    @JsonProperty("name")
    private String nameTeam;

    @JsonProperty("rival")
    private String rivalTeam;

    @JsonProperty("photo")
    private Integer photoTeam;

    @JsonProperty("country")
    private String countryTeam;
}