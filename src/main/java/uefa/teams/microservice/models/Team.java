package uefa.teams.microservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "team")
@Getter
@Setter
public class Team {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @OneToOne
    private Country country;

    @Column(name = "rival", length = 255)
    private String rival;

    @Column(name = "photo")
    //, columnDefinition = "BLOB")
    //@Lob
    private Integer photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}