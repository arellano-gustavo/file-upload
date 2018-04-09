package mx.com.qbits.upload.controller;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.com.qbits.upload.model.Book;
import mx.com.qbits.upload.service.BookService;

@Component
@Path("/books")
public class BookController {
    @Value("${ruta-bag}")
    private String ruta;
    
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Produces("application/json")
    public Collection<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GET
    @Produces("application/json")
    @Path("/{oid}")
    public Book getBook(@PathParam("oid") String oid) {
        URL pivot = this.getClass().getClassLoader().getResource(".");
        System.out.println(pivot.getPath());
        return bookService.getBook(oid);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addBook(Book book) {
        bookService.addBook(book);
        return Response.created(URI.create("/" + book.getOid())).build();
    }
    
    // aqui poner lo necesario para agregar una imagen
    

    @PUT
    @Consumes("application/json")
    @Path("/{oid}")
    public Response updateBook(@PathParam("oid") String oid, Book book) {
        bookService.updateBook(oid, book);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{oid}")
    public Response deleteBook(@PathParam("oid") String oid) {
        bookService.deleteBook(oid);
        return Response.ok().build();
    }
    
    
    
    @POST
    @Path("/fileupload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
            @DefaultValue("") @FormDataParam("tags") String tags, 
            @FormDataParam("file") InputStream file,
            @FormDataParam("file") FormDataContentDisposition fileDisposition) {
        String fileName = fileDisposition.getFileName();
        saveFile(file, ruta + fileName);
        
        // Pinta el resultado:
        Map<String, String> mapa = new HashMap<>();
        mapa.put("location", ruta);
        mapa.put("name", fileName);
        mapa.put("time", System.currentTimeMillis()+"");
        mapa.put("tags", tags);
        mapa.put("type", fileDisposition.getType());
        mapa.put("size", fileDisposition.getSize()+"");
        return Response.ok(mapa).build();
    }
    
    private void saveFile(InputStream file, String fullName) {
        try {
            java.nio.file.Path path = FileSystems.getDefault().getPath(fullName); 
            Files.copy(file, path);
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
    }    
    
}
