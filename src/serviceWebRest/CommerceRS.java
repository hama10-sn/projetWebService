package serviceWebRest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import metier.Produit;

@Path( "/commerce" )
public class CommerceRS {

    private Connection connexion;

    Statement          statement = null;
    ResultSet          resultSet = null;

    private void loadDatabase() {

        // Chargement du driver et connection à la base de données
        try {
            Class.forName( "org.postgresql.Driver" );

            String url = "jdbc:postgresql://localhost:5432/commerce";
            String userName = "postgres";
            String passWord = "hama1005!";

            connexion = DriverManager.getConnection( url, userName, passWord );

        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();

        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    @GET
    @Path( "/produits" )
    @Produces( javax.ws.rs.core.MediaType.APPLICATION_JSON )
    public List<Produit> listeProduits() {

        List<Produit> listeProduits = new ArrayList<Produit>();

        loadDatabase();

        try {

            statement = connexion.createStatement();

            resultSet = statement.executeQuery( "SELECT * FROM produit ;" );

            while ( resultSet.next() ) {

                int id = resultSet.getInt( "id" );
                String nom = resultSet.getString( "nom" );
                int prixUnit = resultSet.getInt( "prix" );
                int quantite = resultSet.getInt( "quantite" );

                Produit produit = new Produit();
                produit.setId( id );
                produit.setNom( nom );
                produit.setPrixUnit( prixUnit );
                produit.setQuantite( quantite );

                listeProduits.add( produit );
            }

        } catch ( SQLException e ) {

        }

        finally {
            // Fermeture de la conexion
            try {
                if ( resultSet != null )
                    resultSet.close();
                if ( statement != null )
                    statement.close();
                if ( connexion != null )
                    connexion.close();
            } catch ( SQLException ignore ) {

            }
        }

        return listeProduits;
    }

    @GET
    @Path( "/produits/{id}" )
    @Produces( javax.ws.rs.core.MediaType.APPLICATION_JSON )
    public Produit getproduit( @PathParam( "id" ) int id ) {

        Produit produit = null;

        loadDatabase();

        try {

            statement = connexion.createStatement();

            resultSet = statement.executeQuery( "SELECT * FROM produit WHERE id = " + id + " ;" );

            while ( resultSet.next() ) {

                int id1 = resultSet.getInt( "id" );
                String nom = resultSet.getString( "nom" );
                int prixUnit = resultSet.getInt( "prix" );
                int quantite = resultSet.getInt( "quantite" );

                produit = new Produit( id1, nom, prixUnit, quantite );
            }

        } catch ( SQLException e ) {

        }

        finally {
            // Fermeture de la conexion
            try {
                if ( resultSet != null )
                    resultSet.close();
                if ( statement != null )
                    statement.close();
                if ( connexion != null )
                    connexion.close();
            } catch ( SQLException ignore ) {

            }
        }

        return produit;
    }

    @POST
    @Path( "/produits" )
    @Produces( { javax.ws.rs.core.MediaType.APPLICATION_JSON } )
    @Consumes( javax.ws.rs.core.MediaType.APPLICATION_JSON )
    public Produit save( Produit prod ) {

        loadDatabase();

        try {

            PreparedStatement preparedStatement = connexion
                    .prepareStatement( "INSERT INTO produit VALUES(?, ?, ?, ?) ;" );

            preparedStatement.setInt( 1, prod.getId() );
            preparedStatement.setString( 2, prod.getNom() );
            preparedStatement.setInt( 3, prod.getPrixUnit() );
            preparedStatement.setInt( 4, prod.getQuantite() );

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connexion.close();

        } catch ( SQLException e ) {

            e.printStackTrace();
        }

        return prod;
    }

    @PUT
    @Path( "/produits/{id}" )
    @Produces( javax.ws.rs.core.MediaType.APPLICATION_JSON )
    public Produit update( Produit prod, @PathParam( "id" ) int id ) {

        loadDatabase();

        try {

            PreparedStatement preparedStatement = connexion
                    .prepareStatement( "UPDATE produit SET id = ?, nom = ?, prix = ?, quantite = ? WHERE id = ? ;" );

            preparedStatement.setInt( 1, prod.getId() );
            preparedStatement.setString( 2, prod.getNom() );
            preparedStatement.setInt( 3, prod.getPrixUnit() );
            preparedStatement.setInt( 4, prod.getQuantite() );
            preparedStatement.setInt( 5, id );

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connexion.close();

        } catch ( SQLException e ) {

            e.printStackTrace();
        }

        return prod;
    }

    @DELETE
    @Path( "/produits/{id}" )
    @Produces( javax.ws.rs.core.MediaType.APPLICATION_JSON )
    public boolean delete( @PathParam( "id" ) int id ) {

        loadDatabase();

        try {

            PreparedStatement preparedStatement = connexion
                    .prepareStatement( "DELETE FROM produit WHERE id = ? ;" );

            preparedStatement.setInt( 1, id );

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connexion.close();

        } catch ( SQLException e ) {

            e.printStackTrace();
        }

        return true;
    }

}
