package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {DESCRIPTION}
 *
 * @author Frank Sprich
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Test the Pokemon repository")
class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository repository;

    @Nested
    @DisplayName("Deletion Tests")
    class GroupForDeleteTests {

        @Test
        public void shouldDeletePokemon() {
            //Arrange
            Pokemon pokemon = Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build();

            //Act
            repository.save(pokemon);
            repository.deleteById(pokemon.getId());
            Optional<Pokemon> shouldBeNull = repository.findById(pokemon.getId());

            //Assert
            assertThat(shouldBeNull).isEmpty();
        }

    }

    @Nested
    @DisplayName("Update Tests")
    class GroupForUpdateTests {

        @Test
        public void shouldUpdatePokemon() {
            //Given
            Pokemon initial = repository.save(Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build());

            //When
            Pokemon retrieved = repository.findById(initial.getId()).orElse(null);
            assert retrieved != null;
            retrieved.setName("AnotherName");
            Pokemon updated = repository.save(retrieved);

            //Then
            assertThat(updated).isNotNull();
            assertThat(updated.getName()).isEqualTo("AnotherName");
        }
    }

    @Nested
    @DisplayName("Retrieval Tests")
    class GroupForRetrievalTests {

        @Test
        public void shouldFindByTypePokemon() {
            //Given
            repository.save(Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build());

            //When
            List<Pokemon> pokemons = repository.findByType("electric").orElse(null);

            //Then
            assertThat(pokemons).isNotNull();
            assertThat(pokemons).isNotEmpty();
            pokemons.forEach(pokemon -> {
                assertThat(pokemon.getType()).isEqualTo("electric");
            });
        }

        @Test
        public void shouldFindByIdPokemon() {
            //Arrange/Given
            Integer pokemonId = repository.save(Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build()).getId();

            //Act/When
            Pokemon pokemon = repository.findById(pokemonId).orElse(null);

            //Assert/Then
            assertThat(pokemon).isNotNull();
            assertThat(pokemon.getId()).isEqualTo(pokemonId);
        }

        @Test
        public void shouldFindAllPokemon() {
            //Arrange (Given)
            repository.save(Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build());
            repository.save(Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build());

            //Act (When)
            List<Pokemon> pokemons = repository.findAll();

            //Assert (Then)
            assertThat(pokemons).isNotNull();
            assertThat(pokemons.size()).isEqualTo(2);
        }

    }

    @Nested
    @DisplayName("Inserting Tests")
    class GroupForInsertTests {

        @Test
        public void shouldSaveAPokemon() {
            //Arrange
            Pokemon pokemon = Pokemon.builder()
                    .name("Pikachu")
                    .type("electric").build();

            //Act
            Pokemon saved = repository.save(pokemon);

            //Assert
            assertThat(saved).isNotNull();
            assertThat(saved.getId()).isGreaterThan(0);
        }

    }

}
