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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "country", length = 255, nullable = false)
    private String country;

    @Column(name = "rival", length = 255)
    private String rival;

    @Column(name = "photo")
    private Integer photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}