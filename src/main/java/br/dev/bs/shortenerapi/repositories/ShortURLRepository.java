package br.dev.bs.shortenerapi.repositories;

import br.dev.bs.shortenerapi.models.ShortURL;
import br.dev.bs.shortenerapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortURLRepository extends MongoRepository<ShortURL, String> {

    Optional<ShortURL> findByHashCode(String hashCode);

    @Query("{ 'user.id' : ?0 }")
    Optional<List<ShortURL>> findByUserId(String userId);
}
