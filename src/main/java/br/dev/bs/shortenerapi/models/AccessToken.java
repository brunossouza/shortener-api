package br.dev.bs.shortenerapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "access_tokens")
public class AccessToken {

    @Id
    private String id;

    @Indexed(unique = true)
    private String token;

    @CreatedDate
    private Date created;

    private Date expirationDate;

    @DBRef
    private User user;
}
