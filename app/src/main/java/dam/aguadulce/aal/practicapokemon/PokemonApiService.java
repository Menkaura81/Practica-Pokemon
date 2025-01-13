package dam.aguadulce.aal.practicapokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemons(
            @Query("offset") int offset,
            @Query("limit") int limit
    );
}