/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import java.sql.ResultSet;
import java.sql.Statement;
import beans.Pelicula;
import connection.DBConnection;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PeliculaController implements IPeliculaController {

    @Override
    public String listar(boolean ordenar, String orden) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();
        String sql = "Select * from peliculas LIMIT 100";

        if (ordenar == true) {
            sql += " order by genero " + orden;
        }

        List<String> peliculas = new ArrayList<String>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                String autor = rs.getString("autor");
                int copias = rs.getInt("copias");
                boolean novedad = rs.getBoolean("novedad");

                Pelicula pelicula = new Pelicula(id, titulo, genero, autor, copias, novedad);

                peliculas.add(gson.toJson(pelicula));

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return gson.toJson(peliculas);

    }
    
    
    
}
