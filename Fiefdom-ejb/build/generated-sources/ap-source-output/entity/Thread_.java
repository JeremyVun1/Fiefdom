package entity;

import entity.Fiefdom;
import entity.Message;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Thread.class)
public class Thread_ { 

    public static volatile SingularAttribute<Thread, Fiefdom> toid;
    public static volatile CollectionAttribute<Thread, Message> messageCollection;
    public static volatile SingularAttribute<Thread, String> subject;
    public static volatile SingularAttribute<Thread, String> threadtype;
    public static volatile SingularAttribute<Thread, Boolean> activeto;
    public static volatile SingularAttribute<Thread, Boolean> activefrom;
    public static volatile SingularAttribute<Thread, Fiefdom> fromid;
    public static volatile SingularAttribute<Thread, Integer> tid;
    public static volatile SingularAttribute<Thread, Date> posted;
    public static volatile SingularAttribute<Thread, Boolean> responded;

}