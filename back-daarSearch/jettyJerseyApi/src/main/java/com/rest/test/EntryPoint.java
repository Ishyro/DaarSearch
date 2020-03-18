package com.rest.test;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/book-library")
public class EntryPoint {

    /*
    * for every response we need to stort the books with some critera as "centrality, betweenness .."
    * and try some suggestions included within the list .. for example return the book list + some suggestions ..
    **/


    @GET
    @Path("books-normal/{nameBook}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksWithoutRegex(@PathParam("nameBook") String nameBook){
        List<Book> ret = new ArrayList<Book>();

        // some examples
        ret.add(new Book("book 1", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));
        ret.add(new Book("book 2", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));
        ret.add(new Book("book 3", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));
        ret.add(new Book("book 4", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));

        return Response.status(Response.Status.OK)
                .entity(ret)
                .build();
    }


    @GET
    @Path("books-regex/{nameBook}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksWithRegex(@PathParam("nameBook") String nameBook) {
        List<Book> ret = new ArrayList<Book>();
        ret.add(new Book("book 1 _regex", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));
        ret.add(new Book("book 2 _regex", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));
        ret.add(new Book("book 3 _regex", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));
        ret.add(new Book("book 4 _regex", "Surety blame bond Dimrill song necks landlord drove deadliest trail mouth? Leechcraft distant form Gamgee nerve horrid wave? Fangorn warts just fail sacrifice crunchable? I will take the Ring to Mordor. Unfriendly made sautéed threads treasures golden Iáve traveled help listen high dicky. Bargeman load happening purpose alliance Gentlemen fight she's wizards. Dol darkness half-wits hours. Killing watchtower cowards forgot Lórien madness stirring masters."));

        return Response.status(Response.Status.OK)
                .entity(ret)
                .build();
    }
}