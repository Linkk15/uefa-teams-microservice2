package uefa.teams.microservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.service.TeamService;

import java.text.ParseException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/list")
    public ResponseEntity listAllTeams() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(teamService.listTeams());
            //DUDA:: COMO PASO LA LISTA DE LA ENTIDAD TEAM A UNA LISTA DE RESPONSETEAM
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    @GetMapping("/uefalist/{id}")
    public ResponseEntity listUefaWonByTeam(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(teamService.listUefas(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/team/{id}")
    public ResponseEntity getTeam(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(teamService.getTeamResponseById(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity postUpdateTeam(final Team team) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(teamService.createOrUpdateTeam(team));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/champion/{id}")
    public ResponseEntity getTeamChampionByDate(@PathVariable final Integer id,
                                                @RequestParam(value = "date", required = true) String date) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(teamService.teamUefaChampion(id, date));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ParseException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable Integer id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
