package dam.aguadulce.aal.practicapokemon;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que implementa el objeto Pokemon con los detalles necesarios para el recyclerview. También se usa para interacturar
 * con retrofit
 */
public class PokemonDetails implements Serializable {

    private String name;
    private String id;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("types")
    private List<TypeWrapper> types;

    private String weight;
    private String height;

    // Constructor personalizado para simplificar
    public PokemonDetails(String name, String id, String sprite, String type, String weight, String height) {
        this.name = name;
        this.id = id;
        this.sprites = new Sprites(sprite);
        this.types = new ArrayList<>();
        this.types.add(new TypeWrapper(new Type(type)));
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSprite() {
        return sprites.getFrontDefault();
    }

    public String getType() {
        return types.get(0).getType().getName();
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        public Sprites(String frontDefault) {
            this.frontDefault = frontDefault;
        }

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    static class TypeWrapper {
        private Type type;

        public TypeWrapper(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }
    }

    static class Type {
        private String name;

        public Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
