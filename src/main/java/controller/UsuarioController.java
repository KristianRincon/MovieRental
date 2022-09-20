
package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import beans.Usuario;
import connection.DBConnection;
import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class UsuarioController implements IUsuarioController{
    
    @Override
    public String login(String username, String contrasena){
        Gson gson = new Gson();
        DBConnection con = new DBConnection();
        
        String sql = "SELECT * FROM usuarios WHERE username = '" +  username + "' and contrasena = '" + contrasena + "'";
        
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");
                Usuario usuario = new Usuario(username, contrasena, nombre, apellido, email, saldo, premium);
                return gson.toJson(usuario);
            }
        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            
        }finally{
            con.desconectar();
        }
        
        return "false";
    }

    @Override
    public String register(String username, String contrasena, String nombre, String apellido, String email, double saldo, boolean premium) {
        Gson gson = new Gson();
        DBConnection con = new DBConnection();
        
        //Configurar formato de decimales para separador decimal con punto
//        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//        dfs.setGroupingSeparator(',');
//        dfs.setDecimalSeparator('.');
//        DecimalFormat df = new DecimalFormat("###.##");
//        df.setDecimalFormatSymbols(dfs);
        
//        String sql = String.format("INSERT INTO usuarios VALUES ('%s', '%s', '%s', '%s', '%s', %s, %b)", username, contrasena, nombre, apellido, email, df.format(saldo), premium);
        
        String sql = "Insert into usuarios values('" + username + "', '" + contrasena + "', '" + nombre
                + "', '" + apellido + "', '" + email + "', " + saldo + ", " + premium + ")";
        
        
        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);
            Usuario usuario = new Usuario(username, contrasena, nombre, apellido, email, saldo, premium);
            st.close();
            return gson.toJson(usuario);
            
        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            
        }finally{
            con.desconectar();
        }
        
        return "false";
        
        
    }

    @Override
    public String pedir(String username) {
        
        Gson gson = new Gson();
        DBConnection con = new DBConnection();
        
        String sql = "SELECT * FROM usuarios WHERE username = '" +  username + "'";
        
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                String contrasena = rs.getString("contrasena");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");
                Usuario usuario = new Usuario(username, contrasena, nombre, apellido, email, saldo, premium);
                return gson.toJson(usuario);
            }
        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            
        }finally{
            con.desconectar();
        }
        
        return "false";
        
        
    }
    
}
