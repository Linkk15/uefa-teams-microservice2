package uefa.teams.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.service.TeamService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

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

    @GetMapping("/team/{id}")
    public ResponseEntity getTeam(@PathVariable Long id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable Long id) {
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
