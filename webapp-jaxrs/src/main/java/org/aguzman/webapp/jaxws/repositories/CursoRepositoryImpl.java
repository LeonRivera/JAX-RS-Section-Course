package org.aguzman.webapp.jaxws.repositories;

import java.util.List;

import org.aguzman.webapp.jaxws.models.Curso;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class CursoRepositoryImpl implements CursoRepository{
   
    @Inject
    private EntityManager em;

    @Override
    public List<Curso> listar() {
        //lazy instructor
        return 
        em.createQuery("SELECT c from Curso c left outer join fetch c.instructor", Curso.class)
        .getResultList();
    }

    @Override
    public Curso guardar(Curso curso) {


       

        if(curso.getId() != null && curso.getId() > 0){
            em.merge(curso);
        }else{
        em.persist(curso);
        }
        return curso;
    }

    @Override
    public Curso porId(Long id) {
        // return em.find(Curso.class, id);
        return 
        em.createQuery("SELECT c from Curso c left outer join fetch c.instructor WHERE c.id=:id", Curso.class)
        .setParameter("id", id)
        .getSingleResult();
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = porId(id);
        em.remove(curso);
    }
}
