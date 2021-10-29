package uefa.teams.microservice.service;

import org.springframework.stereotype.Service;
import uefa.teams.microservice.dao.TeamDAO;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.response.TeamResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeamService {

    //Intelli me recomienda instanciar el dao en vez de hacer una inyección de dependia con @Autowired
    //esto me lo comento Jose..
    private final TeamDAO teamDAO;

    public TeamService(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public List<Team> listTeams() throws NoSuchElementException {
        List<Team> teamList = teamDAO.findAll();

        if (teamList.isEmpty()) {
            throw new NoSuchElementException("No teams created. Create ones.");
        } else {
            return teamList;
        }
    }

    public TeamResponse getTeamResponseById(Integer id) throws NoSuchElementException {
        Optional<Team> team = teamDAO.findById(id);

        if (team.isPresent()) {
            return convertTeamIntoTeamResponse(team.get());
        } else {
            throw new NoSuchElementException("No team record exists for that id");
        }

    }

    public TeamResponse createOrUpdateTeam(final Team newTeam) throws IllegalArgumentException {
        if (nameTeamFiltering(newTeam) && newTeam.getCountry() != null) {
            Optional<Team> teamExists = teamDAO.findById(newTeam.getId());

            if (teamExists.isPresent()) {
                //update
                Team teamUpdate = new Team();
                teamUpdate.setId(newTeam.getId());
                teamUpdate.setName(newTeam.getName());
                teamUpdate.setCountry(newTeam.getCountry());
                teamUpdate.setRival(newTeam.getRival());
                teamUpdate.setPhoto(newTeam.getPhoto());

                //save updated team
                return convertTeamIntoTeamResponse(teamDAO.save(teamUpdate));
            } else {

                if (sameTeamName(listTeams(), newTeam.getName())) {
                    throw new IllegalArgumentException("There is already team with that name.");
                } else {
                    //new team
                    return convertTeamIntoTeamResponse(teamDAO.save(newTeam));
                }
            }
        } else if (newTeam.getCountry() == null) {
            throw new IllegalArgumentException("The country can not be null");
        } else {
            throw new IllegalArgumentException("The team name must be between 4 and 24 characters long without blank spaces.");
        }
    }

    public void deleteTeam(Integer id) {
        teamDAO.deleteById(getTeamById(id).getId());
    }

    public TeamResponse teamUefaChampion(Integer id, String date) throws NoSuchElementException, ParseException {
        Optional<Team> teamExists = teamDAO.findById(id);

        if (teamExists.isPresent()) {
            teamExists.get().setDateUefa(formatDate(date));
            return convertTeamIntoTeamResponse(teamDAO.save(teamExists.get()));
        } else {
            throw new NoSuchElementException("No team record exists for that id");
        }
    }

    private Date formatDate(final String date) throws ParseException {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(date);
        } catch (ParseException e) {
            throw new ParseException(String.format("The selected date %s cannot be parsed", date), 0);
        }
    }

    private Team getTeamById(Integer id) throws NoSuchElementException {
        Optional<Team> team = teamDAO.findById(id);

        if (team.isPresent()) {
            return team.get();
        } else {
            throw new NoSuchElementException("No team record exists for that id");
        }
    }

    private boolean sameTeamName(List<Team> teamList, String newName) {
        boolean sameName = false;
        if (teamList != null && newName != null) {
            for (int i = 0; i < teamList.size() && !sameName; i++) {
                if (Objects.equals(teamList.get(i).getName(), newName)) {
                    sameName = true;
                }
            }
        }

        return sameName;
    }

    private boolean nameTeamFiltering(final Team team) {
        return team.getName() != null && !team.getName().contains(" ") && (team.getName().length() >= 4 && team.getName().length() <= 24);
    }

    //method that make a TeamResponse
    private TeamResponse convertTeamIntoTeamResponse(final Team team) {
        TeamResponse teamResponse = new TeamResponse();

        if (team != null) {
            teamResponse.setIdTeam(team.getId());
            teamResponse.setNameTeam(team.getName());
            teamResponse.setCountryTeam(team.getCountry());
            teamResponse.setRivalTeam(team.getRival());
            teamResponse.setPhotoTeam(team.getPhoto());
            teamResponse.setDateWonUefa(team.getDateUefa());
        }

        return teamResponse;
    }
}
