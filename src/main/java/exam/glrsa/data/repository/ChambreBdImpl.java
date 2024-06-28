package exam.glrsa.data.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exam.glrsa.core.Repository;
import exam.glrsa.data.entity.Chambre;
import exam.glrsa.data.enums.TypeChambre;

public class ChambreBdImpl implements Repository<Chambre> {

    @Override
    public boolean insert(Chambre chambre) {
        if (chambre == null) {
            System.out.println("Erreur : la chambre à insérer est null.");
            return false;
        }

        int nbre = 0;
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            statement = conn.createStatement();
            String uniqueNumero = generateUniqueNumeroChambre(statement);
            chambre.setNumeroChambre(uniqueNumero);
            String etudiantId = chambre.getEtudiant() != null
                    ? chambre.getEtudiant().getMatricule() 
                    : null;
                    String sql = String.format(
                        "INSERT INTO `chambre` (`numero_chambre`, `numero_etage`, `type_chambre`, `pavillon_id`, `etudiant_id`) "
                                +
                                "VALUES ('%s', %d, '%s', '%s', %s)",
                        chambre.getNumeroChambre(),
                        chambre.getNumeroEtage(),
                        chambre.getTypecChambre().name(),
                        chambre.getPavillon().getNumPavillon(),
                        etudiantId != null ? "'" + etudiantId + "'" : "NULL");
                

            nbre = statement.executeUpdate(sql);

            System.out.println("Insertion réussie");

        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de Connexion à votre BD ou erreur SQL");
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

    private String generateUniqueNumeroChambre(Statement statement) throws SQLException {
        String lastNumeroChambre = "";
        ResultSet rs = statement
                .executeQuery("SELECT numero_chambre FROM chambre ORDER BY numero_chambre DESC LIMIT 1");

        if (rs.next()) {
            lastNumeroChambre = rs.getString("numero_chambre");
        }

        rs.close();

        if (lastNumeroChambre.isEmpty()) {
            return "CHA0001";
        } else {
            int lastNumber = Integer.parseInt(lastNumeroChambre.substring(3));
            return String.format("CHA%04d", lastNumber + 1);
        }
    }

    @Override
    public boolean update(Chambre chambre) {
        int nbre = 0;
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            statement = conn.createStatement();

            String pavillonId = chambre.getPavillon() != null ? String.valueOf(chambre.getPavillon().getId()) : "NULL";
            String etudiantId = chambre.getEtudiant() != null ? String.valueOf(chambre.getEtudiant().getId()) : "NULL";
            String typeChambre = chambre.getTypecChambre() != null ? chambre.getTypecChambre().name() : "";

            String sql = String.format(
                    "UPDATE `chambre` SET `numero_etage` = %d, `type_chambre` = '%s', `pavillon_id` = %s, `etudiant_id` = %s WHERE `numero_chambre` = '%s'",
                    chambre.getNumeroEtage(),
                    typeChambre,
                    pavillonId,
                    etudiantId,
                    chambre.getNumeroChambre());

            nbre = statement.executeUpdate(sql);

            System.out.println("Mise à jour réussie");

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

    @Override
    public boolean delete(int id) {
        int nbre = 0;
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            statement = conn.createStatement();

            String sql = String.format("DELETE FROM `chambre` WHERE `id` = %d", id);

            nbre = statement.executeUpdate(sql);

            System.out.println("Suppression réussie");

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

    @Override
    public List<Chambre> selectAll() {
        List<Chambre> chambres = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM `chambre`");

            while (rs.next()) {
                Chambre chambre = new Chambre();
                chambre.setId(rs.getInt("id"));
                chambre.setNumeroChambre(rs.getString("numero_chambre"));
                chambre.setNumeroEtage(rs.getInt("numero_etage"));
                chambre.setTypecChambre(TypeChambre.valueOf(rs.getString("type_chambre")));
                chambre.setPavillon(new PavillonBdImpl().selectByNumero(rs.getString("pavillon_id")));
                chambre.setEtudiant(new EtudiantBdImpl().selectByNumero(rs.getString("etudiant_id")));
                chambres.add(chambre);
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
        return chambres;
    }

    @Override
    public List<Chambre> selectBy(String numero) {
        List<Chambre> chambres = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            String sql = String.format("SELECT * FROM `chambre` WHERE `numero_chambre` LIKE '%%%s%%'", numero);
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Chambre chambre = new Chambre();
                chambre.setId(rs.getInt("id"));
                chambre.setNumeroChambre(rs.getString("numero_chambre"));
                chambre.setNumeroEtage(rs.getInt("numero_etage"));
                chambre.setTypecChambre(TypeChambre.getValue(rs.getString("type_chambre")));
                chambre.setPavillon(new PavillonBdImpl().selectById(rs.getInt("pavillon_id")));
                chambre.setEtudiant(new EtudiantBdImpl().selectById(rs.getInt("etudiant_id")));

                chambres.add(chambre);
            }

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
        return chambres;
    }

    @Override
    public Chambre selectById(int id) {
        Chambre chambre = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM `chambre` WHERE id = " + id);

            if (rs.next()) {
                chambre = new Chambre();
                chambre.setId(rs.getInt("id"));
                chambre.setNumeroChambre(rs.getString("numero_chambre"));
                chambre.setNumeroEtage(rs.getInt("numero_etage"));
                chambre.setTypecChambre(TypeChambre.valueOf(rs.getString("type_chambre")));
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
        return chambre;
    }

    @Override
    public int count() {
        int count = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) AS total FROM `chambre`");

            if (rs.next()) {
                count = rs.getInt("total");
            }

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
        return count;
    }

    @Override
    public Chambre selectByNumero(String numero) {
        Chambre chambre = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            String sql = String.format("SELECT * FROM `chambre` WHERE `numero_chambre` = '%s'", numero);
            rs = statement.executeQuery(sql);

            if (rs.next()) {
                chambre = new Chambre();
                chambre.setId(rs.getInt("id"));
                chambre.setNumeroChambre(rs.getString("numero_chambre"));
                chambre.setNumeroEtage(rs.getInt("numero_etage"));
                chambre.setTypecChambre(TypeChambre.getValue(rs.getString("type_chambre")));
                chambre.setPavillon(new PavillonBdImpl().selectByNumero(rs.getString("pavillon_id")));
                chambre.setEtudiant(new EtudiantBdImpl().selectByNumero(rs.getString("etudiant_id")));
            }

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
        return chambre;
    }
}
