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


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest {
    @Autowired
	private MedicoService medicoService;

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
        for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(medicoEntity.class);
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}

    }

    @Test
	void testCreateMedico() throws IllegalOperationException {
		MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
		MedicoEntity result = medicoService.createMedico(newEntity);
        newEntity.setRegistro_Medico("RM1745");
		assertNotNull(result);

		MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());

		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getApellido(), entity.getApellido());
		assertEquals(newEntity.getRegistro_Medico(), entity.getRegistro_Medico());
		assertEquals(newEntity.getId(), entity.getId());
	}

    @Test
	void testCreateMedicoInvalido() throws IllegalOperationException {
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			newEntity.setRegistro_Medico("RH2342");
			medicoService.createMedico(newEntity);
		});
	}
   
    
}
