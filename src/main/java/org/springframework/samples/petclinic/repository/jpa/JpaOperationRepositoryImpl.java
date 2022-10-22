package org.springframework.samples.petclinic.repository.jpa;

import org.springframework.samples.petclinic.model.Operation;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.repository.OperationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class JpaOperationRepositoryImpl implements OperationRepository {
    @PersistenceContext
    private EntityManager em;


    /**
     * Save a <code>Operation</code> to the data store, either inserting or updating it. * * @param operation the <code>Operation</code> to save * @see BaseEntity#isNew
     *
     * @param operation
     */
    @Override
    public void save(Operation operation) throws DataAccessException {
        if(operation.getId() == null){
            this.em.persist(operation);
        }
        else {
            this.em.merge(operation);
        }
    }

    /**
     * Retrieves a list of <code>Operation</code> associated to a pet * * @param petId the <code>Pet</code> identifier * @return List of  <code>Operation</code>
     *
     * @param petId
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Operation> findByPetId(Integer petId) {
        Query query = this.em.createQuery("SELECT o FROM Operation o where o.pet.id= :id");
        query.setParameter("id", petId);
        return query.getResultList();
    }

    /**
     * Retrieves a list of <code>Operation</code> associated to a vet * * @param vetId the <code>Vet</code> identifier * @return List of  <code>Operation</code>
     *
     * @param vetId
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Operation> findByVetId(Integer vetId) {
        Query query = this.em.createQuery("SELECT o FROM Operation o where o.vet.id= :id");
        query.setParameter("id", vetId);
        return query.getResultList();
    }
}
