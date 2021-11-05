package uefa.teams.microservice.service;

import org.springframework.stereotype.Service;
import uefa.teams.microservice.dao.TeamDAO;
import uefa.teams.microservice.models.Team;
import uefa.teams.microservice.models.Uefa;
import uefa.teams.microservice.response.TeamResponse;
import uefa.teams.microservice.response.UefaResponse;

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

    public List<TeamResponse> listTeams() throws NoSuchElementException {
        List<Team> teamList = teamDAO.findAll();

        if (teamList.isEmpty()) {
            throw new NoSuchElementException("No teams created. Create ones.");
        } else {
            List<TeamResponse> teamResponseList = new ArrayList<>();
            for (Team team : teamList) {
                teamResponseList.add(convertTeamIntoTeamResponse(team));
            }

            return teamResponseList;
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

                if (sameTeamName(teamDAO.findAll(), newTeam.getName())) {
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

    public TeamResponse teamUefaChampion(final Integer id, final String date) throws NoSuchElementException, ParseException, IllegalArgumentException, NullPointerException {
        Optional<Team> teamExists = teamDAO.findById(id);
        Team teamWinner = new Team();
        boolean dateFounded = false;
        Date dateInput = formatDate(date, null);

        if (teamExists.isPresent() && date != null) {
            for (int i = 0; i < teamExists.get().getUefas().size() && !dateFounded; i++) {
                if (formatDate(null, teamExists.get().getUefas().get(i).getDate().toString()).equals(dateInput)) {
                    teamWinner = teamExists.get().getUefas().get(i).getTeamChampion();
                    dateFounded = true;
                }
            }

            if (!dateFounded) {
                throw new NullPointerException("This team has not won UEFA on this date.");
            } else {
                return convertTeamIntoTeamResponse(teamWinner);
            }
        } else if (date == null) {
            throw new IllegalArgumentException("The date can not be null");
        } else {
            throw new NoSuchElementException("No team record exists for that id");
        }
    }

    public List<UefaResponse> listUefas(final Integer id) {
        List<Uefa> uefaList = teamDAO.listUefasWonByTeam(id);
        if (uefaList.isEmpty()) {
            throw new NoSuchElementException("This team has not won a UEFA yet.");
        } else {
            List<UefaResponse> uefaResponseList = new ArrayList<>();
            for (Uefa uefa : uefaList) {
                uefaResponseList.add(convertUefaIntoUefaResponse(uefa));
            }

            return uefaResponseList;
        }
    }

    private Date formatDate(final String date, final String date2) throws ParseException {
        try {
            if (date != null) {
                return new SimpleDateFormat("yyyyMMdd").parse(date);
            } else if (date2 != null) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(date2);
            } else {
                //review!!
                throw new ParseException(String.format("The selected date %s cannot be parsed", date), 0);
            }
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


    private UefaResponse convertUefaIntoUefaResponse(final Uefa uefa) {
        UefaResponse uefaResponse = new UefaResponse();

        if (uefa != null) {
            uefaResponse.setIdUefa(uefa.getId());
            uefaResponse.setDateWin(uefa.getDate());
            uefaResponse.setIdTeamWin(uefa.getTeamChampion().getId());
        }

        return uefaResponse;
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
                List<UefaResponse> uefaResponseList = new ArrayList<>();
                for (int i = 0; i < team.getUefas().size(); i++) {
                    uefaResponseList.add(convertUefaIntoUefaResponse(team.getUefas().get(i)));
                }

                teamResponse.setUefas(uefaResponseList);
            }
        }

        return teamResponse;
    }
}
