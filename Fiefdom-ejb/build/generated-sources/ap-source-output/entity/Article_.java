package entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-06T07:07:01")
@StaticMetamodel(Article.class)
public class Article_ { 

    public static volatile SingularAttribute<Article, Integer> id;
    public static volatile SingularAttribute<Article, String> title;
    public static volatile SingularAttribute<Article, String> body;
    public static volatile SingularAttribute<Article, Date> posted;

}