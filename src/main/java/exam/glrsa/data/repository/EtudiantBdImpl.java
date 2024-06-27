package exam.glrsa.data.repository;

import java.sql.Connection;
import java.sql.DriverManager;
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

            // Generate unique matricule
            String matricule = generateUniqueMatricule(statement);

            // Check for null values and format date
            String typeChambre = etudiant.getTypeChambre() != null ? etudiant.getTypeChambre().name() : "";
            String adresse = etudiant.getNonBoursier() != null ? etudiant.getNonBoursier().getAdresse() : "";
            String dateNaissance = etudiant.getDateNaissance() != null ? etudiant.getDateNaissance().toString() : "";
            String chambreId = etudiant.getChambre() != null ? String.valueOf(etudiant.getChambre().getId()) : "NULL";

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
            // Close resources in finally block
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
    public boolean update(Etudiant objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
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
                etudiant.setTypeChambre(TypeChambre.getValue(rs.getString("type_chambre")));

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
            // Close resources in finally block
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBy'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByNumero'");
    }
}
