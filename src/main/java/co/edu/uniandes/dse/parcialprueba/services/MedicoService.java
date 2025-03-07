package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRespository;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRespository medicoRepository; 

    public MedicoEntity createMedico(MedicoEntity medico) throws IllegalOperationException{
        log.info("Inicia proceso de creación del medico");
        String [] registro= medico.getRegistro_Medico().split("");
        if (registro[0] != "R" || registro[1] != "M"){
            throw new IllegalOperationException("Registro medico is not valid");
        }
        log.info("Termina proceso de creación del medico");
        return medicoRepository.save(medico);


    }
    
}
