package uefa.teams.microservice.service;

import org.springframework.stereotype.Service;
import uefa.teams.microservice.dao.TeamDAO;
import uefa.teams.microservice.dao.UefaDAO;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.models.Uefa;
import uefa.teams.microservice.response.TeamResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeamService {

    //Intelli me recomienda instanciar el dao en vez de hacer una inyección de dependia con @Autowired
    //esto me lo comento Jose..
    private final TeamDAO teamDAO;
    private final UefaDAO uefaDAO;

    public TeamService(TeamDAO teamDAO, UefaDAO uefaDAO) {
        this.teamDAO = teamDAO;
        this.uefaDAO = uefaDAO;
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

    public List<Uefa> listUefasWonByTeam(final Integer id) throws NoSuchElementException {
        Optional<Team> team = teamDAO.findById(id);

        if (team.isPresent()) {
            List<Uefa> uefaList = uefaDAO.listTeamsWonUefas(id);

            if (uefaList.isEmpty()) {
                throw new NoSuchElementException("This team has not won a Uefa Champions league yet.");
            } else {
                return uefaList;
            }
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

    public void teamUefaChampion(final Integer id, String date) throws NoSuchElementException, ParseException, IllegalArgumentException {
        Optional<Team> teamExists = teamDAO.findById(id);

        if (teamExists.isPresent() && date != null) {
            Uefa uefa = new Uefa();

            if (teamExists.get().getUefas().isEmpty()) {
                uefa.setId(1);
            } else {
                //esto da vergüenza...
                uefa.setId(teamExists.get().getUefas().get(teamExists.get().getUefas().size() - 1).getId() + 1);
            }

            uefa.setDate(formatDate(date));
            uefa.setTeamChampion(teamExists.get());

            uefaDAO.save(uefa);

            //Increment list of uefas
            List<Uefa> uefaList = teamExists.get().getUefas();
            uefaList.add(uefa);
            teamExists.get().setUefas(uefaList);

            teamDAO.save(teamExists.get());
        } else if (date == null) {
            throw new IllegalArgumentException("The date can not be null");
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

    private Team getTeamById(final Integer id) throws NoSuchElementException {
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

            if (!team.getUefas().isEmpty()) {
                teamResponse.setUefas(team.getUefas().get(0).getTeamChampion().getUefas());
            }
        }

        return teamResponse;
    }
}
