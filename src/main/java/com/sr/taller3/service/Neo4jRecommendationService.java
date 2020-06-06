package com.sr.taller3.service;

import com.sr.taller3.model.Movie;
import com.sr.taller3.model.Rating;
import org.neo4j.driver.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

@Service
@Scope("singleton")
public class Neo4jRecommendationService {

    private final Driver driver;

    public Neo4jRecommendationService()
    {
        driver = GraphDatabase.driver( "neo4j://localhost:7687", AuthTokens.basic( "neo4j", "labsisrec" ) );
    }

    public void close() throws Exception
    {
        driver.close();
    }

    public boolean addUser(String user)
    {
        try ( Session session = driver.session() )
        {
            String userId = session.writeTransaction(tx -> {
                Result result = tx.run( "CREATE (u300000:User) SET u300000.userId = $userId RETURN u300000.userId",
                        parameters( "userId", user ) );
                return result.single().get( 0 ).asString();
            });
            return user.equals(userId);
        }
    }

    public boolean addRating(String user, String movie, double rating)
    {
        try ( Session session = driver.session() )
        {
            String type = session.writeTransaction(tx -> {
                Result result = tx.run( "MATCH (a:User),(b:Movie) WHERE a.userId = $userId AND b.movieId = $movieId CREATE (a)-[r:Saw{rating:$rating}]->(b) RETURN type(r)",
                        parameters( "userId", user, "movieId", movie, "rating", rating ) );
                return result.single().get( 0 ).asString();
            });
            return type.equals("Saw");
        }
    }

    public boolean existsUser(String user)
    {
        try ( Session session = driver.session() )
        {
            boolean exists = session.writeTransaction(tx -> {
                Result result = tx.run( "MATCH (a:User) WHERE a.userId = $userId RETURN a.userId",
                        parameters( "userId", user ) );
                return result.hasNext();
            });
            return exists;
        }
    }

    public List<Movie> recommend(String user){
        try ( Session session = driver.session() )
        {
            ArrayList<Movie> movies= session.writeTransaction(tx -> {
                Result result = tx.run( "MATCH (p:User {userId: $userId})\n" +
                                "CALL gds.alpha.randomWalk.stream({\n" +
                                "  nodeProjection: '*',\n" +
                                "  relationshipProjection: {\n" +
                                "    LINKS: {\n" +
                                "      type: 'Saw',\n" +
                                "      orientation: 'UNDIRECTED'\n" +
                                "    }\n" +
                                "  },\n" +
                                "  start: id(p),\n" +
                                "  steps: 3,\n" +
                                "  walks: 10\n" +
                                "})\n" +
                                "YIELD nodeIds\n" +
                                "UNWIND nodeIds AS nodeId\n" +
                                "MATCH (n:Movie) WHERE id(n) = nodeId\n" +
                                "RETURN n.movieId,n.title, \n" +
                                "       n.genres, \n" +
                                "       n.tags, \n" +
                                "       n.directores, \n" +
                                "       n.actores,\n" +
                                "[nodeId in nodeIds | coalesce(gds.util.asNode(nodeId).movieId,gds.util.asNode(nodeId).userId)] as result",
                        parameters( "userId", user ) );
                ArrayList<Movie> recMovies = new ArrayList<>();
                while(result.hasNext()){
                    Record mov = result.next();
                    Value val = mov.get(6);
                    if(val.get(3).asString().equals(mov.get(0).asString())){
                        Movie movie = new Movie();
                        movie.setId(mov.get(0).asString());
                        movie.setTitle(mov.get(1).asString());
                        movie.setGenres(mov.get(2).asString());
                        movie.setTags(mov.get(3).asString());
                        movie.setDirectors(mov.get(4).asString());
                        movie.setActors(mov.get(5).asString());
                        recMovies.add(movie);
                    }
                }
                return recMovies;
            });
            return movies;
        }
    }

    public List<Rating> getRatings(String user) {
        try ( Session session = driver.session() )
        {
            List<Rating> ratings = session.writeTransaction(tx -> {
                Result result = tx.run( "MATCH (p:User {userId: $userId})-[r:Saw]->(b:Movie) " +
                                "RETURN p.userId, b.movieId, b.title, r.rating",
                        parameters( "userId", user ) );
                ArrayList<Rating> userRatings = new ArrayList<>();
                while(result.hasNext()){
                    Record rec = result.next();
                    Rating rating = new Rating();
                    rating.setUserId(rec.get(0).asString());
                    rating.setMovieId(rec.get(1).asString());
                    rating.setTitle(rec.get(2).asString());
                    rating.setRating(rec.get(3).asDouble());
                    userRatings.add(rating);
                }
                return userRatings;
            });
            return ratings;
        }
    }
}
