package mx.com.leonrv.cliente.jaxrs;

import java.util.List;

import javax.print.attribute.standard.Media;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import mx.com.leonrv.cliente.jaxrs.models.Curso;
import mx.com.leonrv.cliente.jaxrs.models.Instructor;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget rootUri = client.target("http://localhost:8080/webapp-jaxrs/api").path("/cursos");

        System.out.println("==============por id===============");
        Curso curso = rootUri.path("/2")
                .request(MediaType.APPLICATION_JSON) //accept mediatype
                .get(Curso.class);
        // Dif impl |>
        // Response response = rootUri.path("/2")
        //     .request(MediaType.APPLICATION_JSON)
        //     .get();
        // Curso curso2 = response.readEntity(Curso.class);
        // System.out.println(response.getStatus());
        System.out.println(curso);
        // Diff impl |> 
        // Response response = rootUri.request(MediaType.APPLICATION_JSON)
        //                    .get(Response.class);
        //List<Curso> cursos = response.readEntity(new GenericType<List<Curso>>(){});

        
        System.out.println("===============creando==============");
        Curso cursoNuevo = new Curso();
        cursoNuevo.setNombre("React");
        cursoNuevo.setDescripcion("spring reactivo");
        cursoNuevo.setDuracion(25D);

        Instructor instructor = new Instructor();
        // instructor.setId(2L);
        instructor.setNombre("Leon");
        cursoNuevo.setInstructor(instructor);

        Entity<Curso> entityHeader = Entity.entity(cursoNuevo, MediaType.APPLICATION_JSON);
        curso = rootUri.request(MediaType.APPLICATION_JSON)
                .post(entityHeader, Curso.class);
        System.out.println(curso);


        listar(rootUri);

        System.out.println("===============editando==============");
        Curso cursoEditado = curso;
        cursoEditado.setNombre("microservicios con spring cloud eureka");
        entityHeader = Entity.entity(cursoEditado, MediaType.APPLICATION_JSON);

        curso = rootUri.path("/"+curso.getId()).request(MediaType.APPLICATION_JSON)
                .put(entityHeader, Curso.class);
        System.out.println(curso);

        listar(rootUri);

//        System.out.println("===============eliminando==============");
//        rootUri.path("/"+curso.getId()).request()
//                 .delete();
        

        listar(rootUri);

        System.out.println("testing new branch");

        
    }

    private static void listar(WebTarget rootUri) {
        System.out.println("===============listando==============");
        List<Curso> cursos = rootUri.request(MediaType.APPLICATION_JSON)
                            .get(Response.class)
                            .readEntity(new GenericType<List<Curso>>(){});
        cursos.forEach(System.out::println);
    }
}
