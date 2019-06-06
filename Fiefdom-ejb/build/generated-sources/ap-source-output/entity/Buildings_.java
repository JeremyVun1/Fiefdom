package entity;

import entity.Fiefdom;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Buildings.class)
public class Buildings_ { 

    public static volatile SingularAttribute<Buildings, Integer> mines;
    public static volatile SingularAttribute<Buildings, Integer> markets;
    public static volatile CollectionAttribute<Buildings, Fiefdom> fiefdomCollection;
    public static volatile SingularAttribute<Buildings, Integer> farms;
    public static volatile SingularAttribute<Buildings, Integer> bid;
    public static volatile SingularAttribute<Buildings, Integer> towers;

}