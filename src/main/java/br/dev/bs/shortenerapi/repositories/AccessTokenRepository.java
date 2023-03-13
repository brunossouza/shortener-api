package br.dev.bs.shortenerapi.repositories;

import br.dev.bs.shortenerapi.models.AccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessTokenRepository extends MongoRepository<AccessToken, String> {

    boolean existsByToken(String token);

    @Query("{ 'token' : ?0 }")
    Optional<AccessToken> findByToken(String token);

    @Query("{ 'user.id' : ?0 }")
    Optional<List<AccessToken>> findAllByUserId(String userId);
}
