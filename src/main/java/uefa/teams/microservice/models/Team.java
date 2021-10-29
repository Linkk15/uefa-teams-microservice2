package uefa.teams.microservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "photo")
    //, columnDefinition = "BLOB")
    //@Lob
    private Integer photo;

    @Column(name = "date_uefa")
    private Date dateUefa;

    //Relations
    @OneToOne
    private Country country;
    /* Todo el tema del país también se puede hacer con un ENUM pero serían meter muchas constantes */

    @OneToOne
    private Rival rival;
    /* Esto no podría hacerse con un atributo de Team ya que el rival también es un equipo ?? */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}