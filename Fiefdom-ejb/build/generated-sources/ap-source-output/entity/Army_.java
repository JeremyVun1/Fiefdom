package entity;

import entity.Fiefdom;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Army.class)
public class Army_ { 

    public static volatile SingularAttribute<Army, Integer> knights;
    public static volatile CollectionAttribute<Army, Fiefdom> fiefdomCollection;
    public static volatile SingularAttribute<Army, Integer> archers;
    public static volatile SingularAttribute<Army, Integer> spears;
    public static volatile SingularAttribute<Army, Integer> aid;
    public static volatile SingularAttribute<Army, Integer> wizards;

}