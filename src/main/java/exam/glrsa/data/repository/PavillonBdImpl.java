package exam.glrsa.data.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.data.entity.Pavillon;

public class PavillonBdImpl implements Repository<Pavillon> {

    @Override
    public boolean insert(Pavillon pavillon) {
        int nbre = 0;
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            statement = conn.createStatement();

            // Generate unique numPavillon
            String numPavillon = generateUniqueNumPavillon(statement);

            // Check for null values
            int nbreEtage = pavillon.getNbreEtage();
            String chambreId = pavillon.getChambres() != null && !pavillon.getChambres().isEmpty()
                    ? String.valueOf(pavillon.getChambres().get(0).getNumeroChambre())
                    : "NULL";

            String sql = String.format(
                    "INSERT INTO `pavillon` (`num_pavillon`, `nbre_etage`, `chambre_id`) VALUES ('%s', %d, %s)",
                    numPavillon,
                    nbreEtage,
                    chambreId);

            nbre = statement.executeUpdate(sql);

            System.out.println("Insertion réussie");

        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de Connexion à votre BD");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion");
                e.printStackTrace();
            }
        }
        return nbre > 0;
    }

    private String generateUniqueNumPavillon(Statement statement) throws SQLException {
        String lastNumPavillon = "";
        ResultSet rs = statement.executeQuery("SELECT num_pavillon FROM pavillon ORDER BY num_pavillon DESC LIMIT 1");

        if (rs.next()) {
            lastNumPavillon = rs.getString("num_pavillon");
        }

        rs.close();

        if (lastNumPavillon.isEmpty()) {
            return "PAV0001";
        } else {
            int lastNumber = Integer.parseInt(lastNumPavillon.substring(3));
            return String.format("PAV%04d", lastNumber + 1);
        }
    }

    @Override
    public boolean update(Pavillon pavillon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Pavillon> selectAll() {
        List<Pavillon> pavillons = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM `pavillon`");

            while (rs.next()) {
                Pavillon pavillon = new Pavillon();
                pavillon.setId(rs.getInt("id"));
                pavillon.setNumPavillon(rs.getString("num_pavillon"));
                pavillon.setNbreEtage(rs.getInt("nbre_etage"));
                pavillons.add(pavillon);
            }

            System.out.println("Connexion Bd établie");

        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de Connexion à votre BD");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion");
                e.printStackTrace();
            }
        }
        return pavillons;
    }

    @Override
    public List<Pavillon> selectBy(String numero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBy'");
    }

    @Override
    public Pavillon selectById(int id) {
        Pavillon pavillon = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM `pavillon` WHERE id = " + id);

            if (rs.next()) {
                pavillon = new Pavillon();
                pavillon.setId(rs.getInt("id"));
                pavillon.setNumPavillon(rs.getString("numPavillon"));
                pavillon.setNbreEtage(rs.getInt("nbre_etage"));
                // Récupération des chambres si nécessaire
            }

            System.out.println("Connexion Bd établie");

        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de Connexion à votre BD");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion");
                e.printStackTrace();
            }
        }
        return pavillon;
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Pavillon selectByNumero(String numero) {
        Pavillon pavillon = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            String query = "SELECT * FROM pavillon WHERE num_pavillon = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, numero);
            rs = stmt.executeQuery();

            if (rs.next()) {
                pavillon = new Pavillon();
                pavillon.setId(rs.getInt("id"));
                pavillon.setNumPavillon(rs.getString("num_pavillon"));
                pavillon.setNbreEtage(rs.getInt("nbre_etage"));
            }

            System.out.println("Connexion à la base de données réussie.");

        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion");
                e.printStackTrace();
            }
        }

        return pavillon;
    }
}
