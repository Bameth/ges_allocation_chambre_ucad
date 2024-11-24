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
import exam.glrsa.data.entity.Etudiant;
import exam.glrsa.data.enums.TypeBourse;
import exam.glrsa.data.enums.TypeChambre;

public class EtudiantBdImpl implements Repository<Etudiant> {

    @Override
    public boolean insert(Etudiant etudiant) {
        int nbre = 0;
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            statement = conn.createStatement();

            String matricule = generateUniqueMatricule(statement);

            String typeChambre = etudiant.getTypeChambre() != null ? etudiant.getTypeChambre().name() : "";
            String adresse = etudiant.getNonBoursier() != null ? etudiant.getNonBoursier().getAdresse() : "";
            String dateNaissance = etudiant.getDateNaissance() != null ? etudiant.getDateNaissance().toString() : "";
            String chambreId = etudiant.getChambre() != null ? String.valueOf(etudiant.getChambre().getNumeroChambre())
                    : "NULL";

            String sql = String.format(
                    "INSERT INTO `etudiant` (`matricule`, `nom`, `prenom`, `telephone`, `date_naissance`, `type_bourse`, `type_chambre`, `chambre_id`, `adresse`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, '%s')",
                    matricule,
                    etudiant.getNom(),
                    etudiant.getPrenom(),
                    etudiant.getTelephone(),
                    dateNaissance,
                    etudiant.getTypeBourse().name(),
                    typeChambre,
                    chambreId,
                    adresse);

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

    private String generateUniqueMatricule(Statement statement) throws SQLException {
        String lastMatricule = "";
        ResultSet rs = statement.executeQuery("SELECT matricule FROM etudiant ORDER BY matricule DESC LIMIT 1");

        if (rs.next()) {
            lastMatricule = rs.getString("matricule");
        }

        rs.close();

        if (lastMatricule.isEmpty()) {
            return "ET0001";
        } else {
            int lastNumber = Integer.parseInt(lastMatricule.substring(2));
            return String.format("ET%04d", lastNumber + 1);
        }
    }

    @Override
    public boolean update(Etudiant etudiant) {
        int nbre = 0;
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            String typeChambre = etudiant.getTypeChambre() != null ? etudiant.getTypeChambre().name() : "";
            String adresse = etudiant.getNonBoursier() != null ? etudiant.getNonBoursier().getAdresse() : "";
            String dateNaissance = etudiant.getDateNaissance() != null ? etudiant.getDateNaissance().toString() : "";
            String chambreId = etudiant.getChambre() != null ? String.valueOf(etudiant.getChambre().getId()) : "NULL";

            String sql = String.format(
                    "UPDATE `etudiant` SET `nom` = '%s', `prenom` = '%s', `telephone` = '%s', `date_naissance` = '%s', `type_bourse` = '%s', `type_chambre` = '%s', `chambre_id` = %s, `adresse` = '%s' WHERE `matricule` = '%s'",
                    etudiant.getNom(),
                    etudiant.getPrenom(),
                    etudiant.getTelephone(),
                    dateNaissance,
                    etudiant.getTypeBourse().name(),
                    typeChambre,
                    chambreId,
                    adresse,
                    etudiant.getMatricule());

            statement = conn.createStatement();
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

            String sql = String.format("DELETE FROM `etudiant` WHERE `id` = %d", id);

            statement = conn.createStatement();
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
    public List<Etudiant> selectAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM `etudiant`");

            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id"));
                etudiant.setMatricule(rs.getString("matricule"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setTelephone(rs.getString("telephone"));
                etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                etudiant.setTypeBourse(TypeBourse.getValue(rs.getString("type_bourse")));
                etudiant.setChambre(new ChambreBdImpl().selectByNumero(rs.getString("chambre_id")));
                etudiants.add(etudiant);
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
        return etudiants;
    }

    @Override
    public List<Etudiant> selectBy(String numero) {
        List<Etudiant> etudiants = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");

            String sql = String.format("SELECT * FROM `etudiant` WHERE `matricule` = '%s'", numero);

            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id"));
                etudiant.setMatricule(rs.getString("matricule"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setTelephone(rs.getString("telephone"));
                etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                etudiant.setTypeBourse(TypeBourse.getValue(rs.getString("type_bourse")));
                etudiant.setTypeChambre(TypeChambre.getValue(rs.getString("type_chambre")));

                etudiants.add(etudiant);
            }

            System.out.println("Recherche par matricule réussie");

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
        return etudiants;
    }

    @Override
    public Etudiant selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Etudiant selectByNumero(String numero) {
        Etudiant etudiant = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ges_etudiant", "root", "root");
            String sql = "SELECT * FROM etudiant WHERE matricule = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, numero);
            rs = statement.executeQuery();

            if (rs.next()) {
                etudiant = new Etudiant();
                etudiant.setId(rs.getInt("id"));
                etudiant.setMatricule(rs.getString("matricule"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setTelephone(rs.getString("telephone"));
                etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                etudiant.setTypeBourse(TypeBourse.valueOf(rs.getString("type_bourse")));
            }

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

        return etudiant;
    }

}
