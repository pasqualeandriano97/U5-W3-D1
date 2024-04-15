package andrianopasquale97.U5W3D1.services;



import andrianopasquale97.U5W3D1.entities.Dipendente;
import andrianopasquale97.U5W3D1.entities.Dispositivo;
import andrianopasquale97.U5W3D1.enums.stato;
import andrianopasquale97.U5W3D1.enums.tipologie;
import andrianopasquale97.U5W3D1.exceptions.BadRequestException;
import andrianopasquale97.U5W3D1.exceptions.CorrectDelete;
import andrianopasquale97.U5W3D1.exceptions.NotFoundException;
import andrianopasquale97.U5W3D1.payloads.DispositivoDTO;
import andrianopasquale97.U5W3D1.repositories.DispositivoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoDAO dispositivoDAO;
    @Autowired
    private DipendenteService dipendenteService;

    public DispositivoDTO save(DispositivoDTO newDispositivo) {

        Dispositivo dispositivo = new Dispositivo(tipologie(newDispositivo.tipologia()), stato.DISPONIBILE);

        dispositivoDAO.save(dispositivo);
        return newDispositivo;
    }

    public Page<Dispositivo> getDispositivi(int page, int size, String sortBy) {
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return dispositivoDAO.findAll(pageable);
    }


    public Dispositivo getDispositivoById(int id) {
        return dispositivoDAO.findById(id).orElseThrow(()-> new NotFoundException("Dispositivo non trovato"));
    }

    public void findByIdAndDelete(int id) {
        Dispositivo found = this.getDispositivoById(id);
        this.dispositivoDAO.delete(found);
        throw new CorrectDelete("Dispositivo correttamente eliminato");
    }


    public Dispositivo update(int id, DispositivoDTO newDispositivo) {
        Dispositivo found = this.getDispositivoById(id);
        found.setTipologia(tipologie(newDispositivo.tipologia()));
        found.setStato(statodispositivo(newDispositivo.stato()));
        return found;
    }

    public Dispositivo findAndAssociate(int idDispositivo, int idDipendente) {
        Dispositivo found = this.getDispositivoById(idDispositivo);
        Dipendente dipendente = this.dipendenteService.getDipendenteById(idDipendente);
        if (found.getStato().equals(stato.ASSEGNATO)||(found.getStato().equals(stato.DISMESSO))||(found.getStato().equals(stato.IN_MANUTENZIONE))) {
            throw new BadRequestException("Dispositivo già in manutenzione o assegnato");
        }else{
            found.setStato(stato.ASSEGNATO);
            found.setDipendente(dipendente);
            dispositivoDAO.save(found);
        return found;}
    }
    public Dispositivo findAndDisassociate(int idDispositivo) {
        Dispositivo found = this.getDispositivoById(idDispositivo);

        if (found.getStato().equals(stato.ASSEGNATO)) {
            found.setStato(stato.DISPONIBILE);
            found.setDipendente(null);
            dispositivoDAO.save(found);
            return found;
        } else {
            throw new BadRequestException("Dispositivo non assegnato");
        }
    }

    public Dispositivo findAndDismiss(int idDispositivo) {
        Dispositivo found = this.getDispositivoById(idDispositivo);

        if (found.getStato().equals(stato.ASSEGNATO)) {
            found.setStato(stato.DISMESSO);
            found.setDipendente(null);
            dispositivoDAO.save(found);
            return found;
        } else {
            throw new BadRequestException("Dispositivo non assegnato");
        }
    }

    public Dispositivo findAndManitence(int idDispositivo) {
        Dispositivo found = this.getDispositivoById(idDispositivo);

        if (found.getStato().equals(stato.ASSEGNATO)) {
            found.setStato(stato.IN_MANUTENZIONE);
            found.setDipendente(null);
            dispositivoDAO.save(found);
            return found;
        } else {
            throw new BadRequestException("Dispositivo non assegnato");
        }
    }

    private tipologie tipologie(String tipologia) {
        if (tipologia == null) {
            throw new BadRequestException("tipologia non valida");}
         else {
        return switch (tipologia) {
            case "cellulare" -> tipologie.CELLULARE;
            case "laptop" -> tipologie.LAPTOP;
            case "tablet" -> tipologie.TABLET;
            default -> throw new BadRequestException("tipologia non valida");
        };}
    }
    private stato statodispositivo(String statoD) {
        return switch (statoD) {
            case "disponibile" -> stato.DISPONIBILE;
            case "assegnato" -> stato.ASSEGNATO;
            case "in_manutenzione" -> stato.IN_MANUTENZIONE;
            case "dismesso" -> stato.DISMESSO;
            default -> throw new BadRequestException("stato non valido");
        };
    }

}
