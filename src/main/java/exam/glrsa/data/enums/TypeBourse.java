package exam.glrsa.data.enums;

public enum TypeBourse {
    DEMI,ENTIERE;


public  static TypeBourse getValue(String value){
    for (TypeBourse t: TypeBourse.values()) {
         if (t.name().compareToIgnoreCase(value)==0) {
               return t; 
         }
    }
    return null;
}
}