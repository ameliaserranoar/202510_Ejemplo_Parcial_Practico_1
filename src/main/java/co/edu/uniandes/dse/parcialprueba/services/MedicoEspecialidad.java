package co.edu.uniandes.dse.parcialprueba.services;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRespository;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidad {

    @Autowired
    private EspecialidadRepository especialidadRepository;

	@Autowired
	private MedicoRespository medicoRepository;

    @Transactional
	public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una especialidad al medico con id = {0}", medicoId);
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro medico con este id");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro especialidad con este id");

		especialidadEntity.get().getMedicos().add(medicoEntity.get());
		log.info("Termina proceso de asociarle un libro al autor con id = {0}", medicoId);
		return especialidadEntity.get();
	}
    
}
