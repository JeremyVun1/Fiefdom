package entity;

import entity.Fiefdom;
import entity.Thread;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Fiefdom> author;
    public static volatile SingularAttribute<Message, Integer> mid;
    public static volatile SingularAttribute<Message, String> message;
    public static volatile SingularAttribute<Message, Thread> tid;
    public static volatile SingularAttribute<Message, Date> posted;

}