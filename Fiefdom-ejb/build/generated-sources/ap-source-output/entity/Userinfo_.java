package entity;

import entity.Fiefdom;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Userinfo.class)
public class Userinfo_ { 

    public static volatile SingularAttribute<Userinfo, String> password;
    public static volatile CollectionAttribute<Userinfo, Fiefdom> fiefdomCollection;
    public static volatile SingularAttribute<Userinfo, Boolean> loggedin;
    public static volatile SingularAttribute<Userinfo, Integer> id;
    public static volatile SingularAttribute<Userinfo, String> rolegroup;
    public static volatile SingularAttribute<Userinfo, String> email;
    public static volatile SingularAttribute<Userinfo, String> username;

}