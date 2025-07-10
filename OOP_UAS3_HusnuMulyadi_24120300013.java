import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Class Club.
 * Merepresentasikan klub sepak bola FC Cakrawala binaan Universitas Cakrawala.
 */
class Club {
    private String clubId;
    private String name;
    private Date foundingDate;
    private Double budget;
    private String league;
    private String stadiumId; // FK

    private List<Team> teams = new ArrayList<>(); // 1 club mengelola banyak team
    private List<Sponsor> sponsors = new ArrayList<>(); // 1 club memiliki banyak sponsor
    private List<Staff> staff = new ArrayList<>(); // 1 club memiliki banyak staf
    private List<Contract> contracts = new ArrayList<>(); // 1 club memiliki banyak kontrak
    private List<Person> persons = new ArrayList<>(); // To hold all hired persons


    public Club(String clubId, String name, Date foundingDate, double budget, String league, String stadiumId) {
        this.clubId = clubId;
        this.name = name;
        this.foundingDate = foundingDate;
        this.budget = budget;
        this.league = league;
        this.stadiumId = stadiumId;
    }

    public Person hirePerson(PersonFactory factory) {
        Person person = factory.createPerson();
        persons.add(person);
        if (person instanceof Staff) {
            staff.add((Staff) person);
        }
        System.out.println(person.getFullName() + " has been hired.");
        return person;
    }

    public void manageBudget() {
        System.out.println("Managing budget of FC Cakrawala: " + budget);
    }

    public void signSponsor(Sponsor sponsor) {
        System.out.println("Sponsor telah ditambahkan ke klub FC Cakrawala.");
    }

    public List<Team> getTeams() {
        return teams;
    }

}

/**
 * Class Sponsor.
 * Merepresentasikan sebuah sponsor untuk klub FC Cakrawala.
 */
class Sponsor {
    private String sponsorId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private Double contractValue;
    private Date contractStartDate;
    private Date contractEndDate;

    public Sponsor(String sponsorId, String name, String contactPerson, String phone, String email, Double contractValue, Date contractStartDate, Date contractEndDate) {
        this.sponsorId = sponsorId;
        this.name = name;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.contractValue = contractValue;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
    }

    public void renewContract(Date newEndDate, Double newValue) {
        this.contractEndDate = newEndDate;
        this.contractValue = newValue;
        System.out.println("Contract untuk " + this.name + " telah diperbarui. New end date: " + newEndDate + ", new value: " + newValue);
    }
}

/**
 * Class Team.
 * Merepresentasikan sebuah tim kelompok umur U-23 (Under 23) bernama FC Cakrawala Muda
 * Tim ini memiliki seorang Head Coach, seorang Assistant Coach,dan diperkuat 15 mahasiswa Universitas Cakrawala sebagai Player utamanya.
 */
class Team {
    private String teamId;
    private String league;
    private String division;
    private String clubId; // FK
    private String name; // e.g., Senior Men, U18, Women's

    private Coach coach; // 1 team memiliki 1 coach
    private List<Player> players = new ArrayList<>(); // 1 team memiliki banyak player
    private List<TrainingSession> trainingSessions; // 1 team memiliki banyak training session


    public Team(String teamId, String league, String division, String clubId, String name, Coach coach) {
        this.teamId = teamId;
        this.league = league;
        this.division = division;
        this.clubId = clubId;
        this.name = name;
        this.coach = coach;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void scheduleTraining(TrainingSession session) {
        System.out.println("\nTraining scheduled for team " + this.name + ".");
    }

}

/**
 * Class abstract Person sebagai dasar untuk semua individu di dalam klub.
 * Sesuai dengan diagram UML.
 */
abstract class Person {
    protected String personId;
    protected String firstName;
    protected String lastName;
    protected Date dateOfBirth;
    protected String nationality;

    public Person(String personId, String firstName, String lastName, Date dateOfBirth, String nationality) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

/**
 * Class Staff.
 * Merepresentasikan seorang staf di dalam klub.
 */
class Staff extends Person {
    private String department;
    private String staffId; // FK from Person
    private String clubId; // FK
    private String role; // e.g., Coach, Assistant Coach, Goalkeeper Coach

    private List<Contract> contracts; // 1 staff memiliki banyak kontrak

    public Staff(String personId, String firstName, String lastName, Date dateOfBirth, String nationality, String department, String role, String clubId) {
        super(personId, firstName, lastName, dateOfBirth, nationality);
        this.staffId = personId;
        this.department = department;
        this.role = role;
        this.clubId = clubId;
        this.contracts = new ArrayList<>();
    }

    public void performDuties() {
        System.out.println(getFullName() + " as " + role + " is performing duties.");
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }
}

/**
 * Class Player, turunan dari Person.
 * Merepresentasikan seorang pemain sepak bola.
 */
class Player extends Person {
    private int jerseyNumber;
    private Double marketValue;
    private String playerId; // FK from Person
    private String teamId; // FK
    private String position; // e.g., Forward, Midfielder, Defender
    private String status; // e.g., Active, Injured, Suspended

    private List<Contract> contracts; // 1 player memiliki banyak kontrak

    public Player(String personId, String firstName, String lastName, Date dateOfBirth, String nationality, String teamId, int jerseyNumber, String position) {
        super(personId, firstName, lastName, dateOfBirth, nationality);
        this.playerId = personId;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
        this.marketValue = 0.0;
        this.status = "Active";
        this.teamId = teamId;
        this.contracts = new ArrayList<>();
    }

    public void train() {
        System.out.println(getFullName() + " is training.");
    }

    public void playMatch() {
        System.out.println(getFullName() + " is playing a match.");
    }

}

/**
 * Class Coach, turunan dari Person.
 * Merepresentasikan seorang pelatih.
 */
class Coach extends Person {
    private String licenseLevel;
    private String coachId; // FK from Person
    private String teamId;  // FK
    private String role; // e.g., Head Coach, Assistant Coach, Goalkeeper Coach

    private List<Contract> contracts; // 1 coach memiliki banyak kontrak

    public Coach(String personId, String firstName, String lastName, Date dateOfBirth, String nationality, String role, String licenseLevel, String teamId) {
        super(personId, firstName, lastName, dateOfBirth, nationality);
        this.coachId = personId;
        this.role = role;
        this.licenseLevel = licenseLevel;
        this.teamId = teamId;
        this.contracts = new ArrayList<>();
    }

    public void conductTraining() {
        System.out.println(getFullName() + " is conducting training.");
    }

    public void selectSquad() {
        System.out.println(getFullName() + " is selecting the squad.");
    }

}

/**
 * Class Contract.
 * Merepresentasikan kontrak antara individu (Pemain, Pelatih, Staf) dengan klub.
 */
class Contract {
    private String contractId;
    private Date startDate;
    private Date endDate;
    private Double salary;
    private String clauses;
    private String clubId;
    private String personId;

    public Contract(String contractId, Date startDate, Date endDate, Double salary, String clauses, String clubId, String personId) {
        this.contractId = contractId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.clauses = clauses;
        this.clubId = clubId;
        this.personId = personId;
    }

    public void renew() {
        System.out.println("Contract " + contractId + " for person " + personId + " has been renewed.");
    }

    public void terminate() {
        System.out.println("Contract " + contractId + " for person " + personId + " has been terminated.");
    }
}

/**
 * Class TrainingSession.
 * Merepresentasikan sesi latihan yang dijadwalkan untuk sebuah tim.
 */
class TrainingSession {
    private String sessionId;
    private Date sessionDate;
    private String sessionTime;
    private String location;
    private String focusArea;
    private String teamId; // FK ke Team

    private Map<Player, Boolean> attendance; // Untuk mencatat kehadiran

    public TrainingSession(String sessionId, Date sessionDate, String sessionTime, String location, String focusArea, String teamId, String coachId) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.location = location;
        this.focusArea = focusArea;
        this.teamId = teamId;
        this.attendance = new HashMap<>();
    }

    public void recordAttendance(Player player, Boolean present) {
        this.attendance.put(player, present); 
        System.out.println("Attendance recorded for " + player.getFullName() + ": " + (present ? "Present" : "Absent"));
    }
}

/**
 * Class Match.
 * Merepresentasikan sebuah pertandingan antara dua tim.
 */
class Match {
    private String matchId;
    private Date matchDate;
    private String matchTime;
    private int homeScore;
    private int awayScore;
    private String competition; 
    private String homeTeamId; // FK ke Team
    private String awayTeamId; // FK ke Team
    private String stadiumId;  // FK ke Stadium
    private String seasonId;   // FK ke Season


    public Match(String matchId, Date matchDate, String matchTime, String competition, String homeTeamId, String awayTeamId, String stadiumId, String seasonId) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.competition = competition;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.stadiumId = stadiumId;
        this.seasonId = seasonId;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public void recordScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        System.out.println("Score recorded: Home " + homeScore + " - " + awayScore + " Away");
    }

    public Map<String, Object> generateReport() {
        System.out.println("Generating report for match: " + this.matchId);
        Map<String, Object> report = new HashMap<>();
        report.put("matchId", this.matchId);
        report.put("date", this.matchDate);
        report.put("score", this.homeScore + " - " + this.awayScore);
        return report;
    }
}

/**
 * Class Stadium.
 * Merepresentasikan stadion tempat pertandingan diadakan.
 */
class Stadium {
    private String stadiumId;
    private String name;
    private int capacity;
    private String address;

    private List<Match> matches; // 1 stadium memiliki banyak match

    public Stadium(String stadiumId, String name, int capacity, String address) {
        this.stadiumId = stadiumId;
        this.name = name;
        this.capacity = capacity;
        this.address = address;
    }

    public void hostMatch(Match match) {
        System.out.println("Stadium " + this.name + " is hosting a match.");
    }
}

/**
 * Class Season.
 * Merepresentasikan satu musim kompetisi.
 */
class Season {
    private String seasonId;
    private int year;
    private String league;
    private Date startDate;
    private Date endDate;

    private List<Match> matches; // 1 season memiliki banyak match

    public Season(String seasonId, int year, String league, Date startDate, Date endDate) {
        this.seasonId = seasonId;
        this.year = year;
        this.league = league;
        this.startDate = startDate;
        this.endDate = endDate;
        this.matches = new ArrayList<>();
    }

    public List<Match> getMatches() {
        return this.matches;
    }

    public Map<String, Integer> getStandings() {
        System.out.println("Generating standings for league: " + this.league);
        // Logika untuk menghasilkan klasemen akan ditambahkan di sini
        return new HashMap<>();
    }
}

// Factory Method Pattern Implementation

/**
 * Abstract Factory for creating Person objects.
 */
abstract class PersonFactory {
    public abstract Person createPerson();
}

/**
 * Concrete factory for creating Player objects.
 */
class PlayerFactory extends PersonFactory {
    @Override
    public Person createPerson() {
        // In a real application, parameters would be passed to the factory or set on the factory instance.
        return new Player("P001", "Cristiano", "Ronaldo", new Date(), "Portuguese", "T001", 7, "Forward");
    }
}

/**
 * Concrete factory for creating Coach objects.
 */
class CoachFactory extends PersonFactory {
    @Override
    public Person createPerson() {
        return new Coach("C001", "Zinedine", "Zidane", new Date(), "French", "Head Coach", "UEFA Pro License", "T001");
    }
}

/**
 * Concrete factory for creating Staff objects.
 */
class StaffFactory extends PersonFactory {
    @Override
    public Person createPerson() {
        return new Staff("S001", "John", "Doe", new Date(), "American", "Management", "General Manager", "CLUB01");
    }
}

/**
 * Main class to demonstrate the Factory Method Pattern.
 */
public class OOP_UAS3_HusnuMulyadi_24120300013 {
    public static void main(String[] args) {
        Club myClub = new Club("CLUB01", "FC Cakrawala", new Date(), 50000000.0, "Premier League", "STAD01");

        // Use factories to create and hire people
        PersonFactory playerFactory = new PlayerFactory();
        myClub.hirePerson(playerFactory);

        PersonFactory coachFactory = new CoachFactory();
        myClub.hirePerson(coachFactory);

        PersonFactory staffFactory = new StaffFactory();
        myClub.hirePerson(staffFactory);

        System.out.println("\nFinished hiring process.");
    }
}