package entity;

import entity.Army;
import entity.Battlereport;
import entity.Buildings;
import entity.Message;
import entity.Relation;
import entity.Thread;
import entity.Userinfo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Fiefdom.class)
public class Fiefdom_ { 

    public static volatile CollectionAttribute<Fiefdom, Relation> relationCollection;
    public static volatile SingularAttribute<Fiefdom, Integer> race;
    public static volatile CollectionAttribute<Fiefdom, Battlereport> battlereportCollection;
    public static volatile CollectionAttribute<Fiefdom, Thread> threadCollection1;
    public static volatile SingularAttribute<Fiefdom, Integer> food;
    public static volatile SingularAttribute<Fiefdom, Integer> gold;
    public static volatile SingularAttribute<Fiefdom, Userinfo> uid;
    public static volatile SingularAttribute<Fiefdom, Integer> peasants;
    public static volatile CollectionAttribute<Fiefdom, Message> messageCollection;
    public static volatile CollectionAttribute<Fiefdom, Thread> threadCollection;
    public static volatile SingularAttribute<Fiefdom, String> name;
    public static volatile CollectionAttribute<Fiefdom, Relation> relationCollection1;
    public static volatile SingularAttribute<Fiefdom, Integer> land;
    public static volatile SingularAttribute<Fiefdom, Integer> id;
    public static volatile SingularAttribute<Fiefdom, Buildings> bid;
    public static volatile SingularAttribute<Fiefdom, Army> aid;
    public static volatile CollectionAttribute<Fiefdom, Battlereport> battlereportCollection1;

}