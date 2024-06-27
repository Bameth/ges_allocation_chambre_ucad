    package exam.glrsa.data.entity;

    import java.util.ArrayList;
    import java.util.List;

    import lombok.Getter;
    import lombok.Setter;
    import lombok.ToString;

    @Getter
    @Setter
    @ToString
    public class Pavillon {
        private static int nbre = 0;

        private int id;
        private String numPavillon;
        private int nbreEtage;
        private List<Chambre> chambres;

        public Pavillon() {
            this.id = ++nbre;
            this.numPavillon = generateNumero(this.id, "PAV");
            this.chambres = new ArrayList<>();
        }

        public String generateNumero(int nbre, String format) {
            int size = String.valueOf(nbre).length();
            return format + "0".repeat((4 - size) < 0 ? 0 : 4 - size) + nbre;
        }
    }
