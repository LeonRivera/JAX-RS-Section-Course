package org.aguzman.webapp.jaxws.controllers;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.aguzman.webapp.jaxws.models.Curso;
import org.aguzman.webapp.jaxws.services.CursoService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
public class CursoRestController {

    @Inject
    private CursoService cursoService;

    @GET
    public List<Curso> listar() {
        return cursoService.listar();
    }

    @GET
    @Path("/{id}")
    public Response porId(@PathParam("id") Long id) {
        Optional<Curso> cOptional = cursoService.porId(id);
        if (cOptional.isPresent()) {
            return Response.ok(cOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(Curso curso) {
        try {
            cursoService.guardar(curso);
            return Response.ok(curso).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response porId(@PathParam("id") Long id, Curso curso) {

        Optional<Curso> cOptional = cursoService.porId(id);
        if(cOptional.isPresent()){
            Curso nuevoCurso = cOptional.get();
            nuevoCurso.setNombre(curso.getNombre());
            nuevoCurso.setDuracion(curso.getDuracion());
            nuevoCurso.setDescripcion(curso.getDescripcion());
            curso.setInstructor(curso.getInstructor());

            try{
                cursoService.guardar(nuevoCurso);
                return Response.ok(nuevoCurso).build();
            }catch(Exception e){
                e.printStackTrace();
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id){
        Optional<Curso> cOptional = cursoService.porId(id);
        if(cOptional.isPresent()){
            cursoService.eliminar(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

}
