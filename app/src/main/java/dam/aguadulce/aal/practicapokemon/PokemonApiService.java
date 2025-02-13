package dam.aguadulce.aal.practicapokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Interfaz que implementa la llamada a la API con Retrofit
 */
public interface PokemonApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemons(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET
    Call<PokemonDetails> getPokemonDetails(@Url String url);
}