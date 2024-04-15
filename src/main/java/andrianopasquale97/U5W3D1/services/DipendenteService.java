package andrianopasquale97.U5W3D1.services;


import andrianopasquale97.U5W3D1.entities.Dipendente;
import andrianopasquale97.U5W3D1.exceptions.BadRequestException;
import andrianopasquale97.U5W3D1.exceptions.CorrectDelete;
import andrianopasquale97.U5W3D1.exceptions.NotFoundException;
import andrianopasquale97.U5W3D1.payloads.DipendenteDTO;
import andrianopasquale97.U5W3D1.payloads.DipendenteRespDTO;
import andrianopasquale97.U5W3D1.repositories.DipendentiDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DipendenteService {
    @Autowired
    private DipendentiDAO dipendentiDAO;
    @Autowired
    private Cloudinary cloudinaryService;

    public DipendenteRespDTO save(DipendenteDTO newDipendente) {
        this.dipendentiDAO.findByEmail(newDipendente.email()).ifPresent(
                author -> {
                    throw new BadRequestException("L'email " + newDipendente.email() + " è già in uso!");
                }
        );

        Dipendente dipendente = new Dipendente(newDipendente.username(), newDipendente.nome(), newDipendente.cognome(), newDipendente.email(),newDipendente.password(), newDipendente.profileImage());

        dipendente.setProfileImage("https://ui-avatars.com/api/?name="+ newDipendente.nome() + "+" + newDipendente.cognome());
        this.dipendentiDAO.save(dipendente);
        return new DipendenteRespDTO(dipendente.getEmail());
    }

    public Page<Dipendente> getDipendenti(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendentiDAO.findAll(pageable);
    }


    public Dipendente getDipendenteById(int id) {
        return this.dipendentiDAO.findById(id).orElseThrow(() -> new NotFoundException("Dipendente non trovato"));
    }


    public void findByIdAndDelete(int id) {
        Dipendente found = this.getDipendenteById(id);
        this.dipendentiDAO.delete(found);
        throw new CorrectDelete("Dipendente correttamente eliminato");
    }

    public DipendenteDTO findByIdAndUpdate(int id, DipendenteDTO modifiedAuthor) {
        Dipendente found = this.getDipendenteById(id);
        found.setUsername(modifiedAuthor.username());
        found.setNome(modifiedAuthor.nome());
        found.setCognome(modifiedAuthor.cognome());
        found.setEmail(modifiedAuthor.email());
        found.setProfileImage("https://ui-avatars.com/api/?name="+ modifiedAuthor.nome() + "+" + modifiedAuthor.cognome());
        this.dipendentiDAO.save(found);
        return modifiedAuthor;
    }

    public Dipendente uploadAuthorImage (MultipartFile image, int dipendenteId) throws IOException {
        Dipendente found = this.getDipendenteById(dipendenteId);
        String url = (String) cloudinaryService.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setProfileImage(url);
        this.dipendentiDAO.save(found);
        return found;
    }

    public Dipendente findByEmail(String email) {
        return this.dipendentiDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Dipendente non trovato"));
    }
}
