package co.edu.uniandes.dse.parcialprueba.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.services.MedicoService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class MedicoEspecialidadServiceTest {
    @Autowired
	private MedicoespecialidadService medicoEspecialidadService;

	@Autowired
	private TestEntityManager entityManager;

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity");
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
	}
    private void insertData() {
        medico = factory.manufacturePojo(MedicoEntity.class);
		entityManager.persist(medico);
        
        for (int i = 0; i < 3; i++) {
			EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
			entity.getMedicos().add(medico);
			entityManager.persist(entity);
			especialidadList.add(entity);
			medico.getEspecialidades().add(entity);
		}

    }

    @Test
    void testAddEspecialidad() throws EntityNotFoundException, IllegalOperationException {
		EspecialidadEntity newEspecialidad = factory.manufacturePojo(EspecialidadEntity.class);
		especialidadService.createEspecialidad(newEspecialidad);

        MedicoEntity newMedico = factory.manufacturePojo(MedicoEntity.class);
		medicoService.createMedico(newMedico);

		EspecialidadEntity result = medicoEspecialidadService.getEspecialidad(medico.getId(), newEspecialidad.getId());

        assertNotNull(result);
		assertEquals(newEspecialidad.getNombre(), result.getNombre());
		assertEquals(newEspecialidad.getDescripcion(), result.getDescripcion());
		assertEquals(newEspecialidad.getId(), result.getId());

	}

    @Test
    void testAddEspecialidadMedicoInvalido(){
        assertThrows(EntityNotFoundException.class, ()-> {
            EspecialidadEntity newEspecialidad= factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(newEspecialidad);
            medicoEspecialidadService.addEspecialidad(0L, newEspecialidad.getId());
        } );
    }


    @Test
    void testAddEspecialidadEspecialidadInvalido(){
        assertThrows(EntityNotFoundException.class, ()-> {
            MedicoEntity newMedico= factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(newMedico);
            medicoEspecialidadService.addEspecialidad(newMedico.getId(),OL);
        } );}
    

}
