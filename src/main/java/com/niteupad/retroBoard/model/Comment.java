package com.niteupad.retroBoard.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



import javax.persistence.*;
import java.sql.Timestamp;

/*
In the preceding code, the @Entity annotation is used to mark the Comment class as a JPA
entity so that it will be eligible to be used in JPA persistence environment.
The @Table annotation is used to mention the table name to which the Comment class
needs to be mapped.
The @EntityListeners annotation is used with the AuditingEntityListener implementation
to dynamically populate the createdDate and createdBy properties annotated with @CreatedDate and
@CreatedBy in the Comment domain model when persisting the comment entry into the
table.
The @Data annotation is from the Lombok library and used to mark a POJO as a class
that will hold data. This means getters, setters, the equals' method, the hashCode
method, and the toString method will be generated for that class.
The @Id annotation marks the ID property as the identity field of the entity, whereas
@GeneratedValue marks it as an auto-generated value. The @Enumerated annotation with
value EnumType.STRING on the type property is used to notify JPA that the value of the
enum CommentType needs to be persisted as a String type in the database.
 */

@Entity
@Table(name = "rb_comment")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    @Enumerated(EnumType.STRING)
    private CommentType type;

    @CreatedDate
    private Timestamp createdDate;
    @CreatedBy
    private String createdBy;



}
