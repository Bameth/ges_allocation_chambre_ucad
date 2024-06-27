package exam.glrsa.data.enums;

public enum TypeChambre {
    INDIVIDUELLE,DOUBLE;
    public  static TypeChambre getValue(String value){
        for (TypeChambre t: TypeChambre.values()) {
             if (t.name().compareToIgnoreCase(value)==0) {
                   return t; 
             }
        }
        return null;
}
}
