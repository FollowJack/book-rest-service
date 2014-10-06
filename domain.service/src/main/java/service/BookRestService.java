package service;

import entities.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by Deniel on 06.10.2014.
 */
@Path("/book")
@Produces({"application/xml","application/json"})
@Consumes({"application/xml","application/json"})
@Stateless
public class BookRestService {

    @PersistenceContext(unitName = "chapter15PU")
    private EntityManager entityManager;

    @Context
    private UriInfo uriInfo;

    @POST
    public Response createBook(Book book) {
        if (book == null)
            throw new BadRequestException();

        entityManager.persist(book);
        URI bookUri = uriInfo.getAbsolutePathBuilder().path(book.getId()).build();
        return Response.created(bookUri).build();
    }

    @PUT
    public Response updateBook(Book book) {
        if (book == null)
            throw new BadRequestException();

        entityManager.merge(book);
        return Response.ok().build();
    }

    /**
     * JSON : curl -X GET -H "Accept: application/json" http://localhost:8080/chapter15-service-1.0/rs/book/1 -v
     * XML  : curl -X GET -H "Accept: application/xml" http://localhost:8080/chapter15-service-1.0/rs/book/1 -v
     */
    @GET
    @Path("{id}")
    public Response getBook(@PathParam("id") String id) {
        Book book = entityManager.find(Book.class, id);

        if (book == null)
            throw new NotFoundException();

        return Response.ok(book).build();
    }

    /**
     * curl -X DELETE http://localhost:8080/chapter15-service-1.0/rs/book/1 -v
     */
    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") String id) {
        Book book = entityManager.find(Book.class, id);

        if (book == null)
            throw new NotFoundException();

        entityManager.remove(book);

        return Response.noContent().build();
    }

    /**
     * JSON : curl -X GET -H "Accept: application/json" http://localhost:8080/chapter15-service-1.0/rs/book -v
     * XML  : curl -X GET -H "Accept: application/xml" http://localhost:8080/chapter15-service-1.0/rs/book -v
     */
    @GET
    public Response getAllBooks() {
        TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_ALL, Book.class);
        Books books = new Books(query.getResultList());
        return Response.ok(books).build();
    }
}
