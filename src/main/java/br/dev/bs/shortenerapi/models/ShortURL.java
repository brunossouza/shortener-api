package br.dev.bs.shortenerapi.models;

import br.dev.bs.shortenerapi.enums.CreatedBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("short_url")
public class ShortURL {

    @Id
    private String id;

    private String url;

    @Indexed(unique = true)
    private String hashCode;

    @Builder.Default
    private Long hits = 0L;

    private Boolean active;

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date updated;

    private Date expirationDate;

    @DBRef
    private User user;

    private CreatedBy createdBy;
}
