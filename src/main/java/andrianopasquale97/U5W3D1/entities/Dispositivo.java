package andrianopasquale97.U5W3D1.entities;



import andrianopasquale97.U5W3D1.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Dispositivi")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
    @Enumerated(EnumType.STRING)
private tipologie tipologia;
    @Enumerated(EnumType.STRING)
private stato stato;
@ManyToOne
@JoinColumn(name = "dipendente_id")
private Dipendente dipendente;

    public Dispositivo(tipologie tipologia, stato stato) {
        this.tipologia = tipologia;
        this.stato = stato;}
}
