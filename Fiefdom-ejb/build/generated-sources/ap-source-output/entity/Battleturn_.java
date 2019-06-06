package entity;

import entity.Battlereport;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Battleturn.class)
public class Battleturn_ { 

    public static volatile SingularAttribute<Battleturn, Integer> wizardloss;
    public static volatile SingularAttribute<Battleturn, Integer> knightloss;
    public static volatile SingularAttribute<Battleturn, Battlereport> brid;
    public static volatile SingularAttribute<Battleturn, Boolean> isattacker;
    public static volatile SingularAttribute<Battleturn, Integer> attackingsize;
    public static volatile SingularAttribute<Battleturn, Integer> archerloss;
    public static volatile SingularAttribute<Battleturn, Integer> spearloss;
    public static volatile SingularAttribute<Battleturn, Integer> id;
    public static volatile SingularAttribute<Battleturn, Integer> turncount;
    public static volatile SingularAttribute<Battleturn, String> attackingunit;

}