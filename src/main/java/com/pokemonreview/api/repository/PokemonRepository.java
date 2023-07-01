package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<List<Pokemon>> findByType(String type);
}
