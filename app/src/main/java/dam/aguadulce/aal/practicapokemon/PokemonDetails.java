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


    /**
     * Constructor personalizado para simplificar
     * @param name Nombre del Pokemon
     * @param id Id del Pokemon
     * @param sprite Sprite del Pokemon
     * @param type Tipo del Pokemon
     * @param weight Peso del Pokemon
     * @param height Altura del Pokemon
     */
    public PokemonDetails(String name, String id, String sprite, String type, String weight, String height) {
        this.name = name;
        this.id = id;
        this.sprites = new Sprites(sprite);
        this.types = new ArrayList<>();
        this.types.add(new TypeWrapper(new Type(type)));
        this.weight = weight;
        this.height = height;
    }


    /**
     * Getter nombre del Pokemon
     * @return String nombre del Pokemon
     */
    public String getName() {
        return name;
    }


    /**
     * Getter id del Pokemon
     * @return String id del Pokemon
     */
    public String getId() {
        return id;
    }


    /**
     * Getter sprite del Pokemon
     * @return String sprite del Pokemon
     */
    public String getSprite() {
        return sprites.getFrontDefault();
    }


    /**
     * Getter tipo del Pokemon
     * @return String tipo del Pokemon
     */
    public String getType() {
        return types.get(0).getType().getName();
    }


    /**
     * Getter peso del Pokemon
     * @return String peso del Pokemon
     */
    public String getWeight() {
        return weight;
    }


    /**
     * Getter altura del Pokemon
     * @return String altura del Pokemon
     */
    public String getHeight() {
        return height;
    }


    /**
     * Clase que implementa el objeto Sprites con el sprite del Pokemon
     */
    static class Sprites {

        @SerializedName("front_default")
        private String frontDefault;


        /**
         * Constructor
         * @param frontDefault Sprite del Pokemon
         */
        public Sprites(String frontDefault) {
            this.frontDefault = frontDefault;
        }


        /**
         * Getter sprite del Pokemon
         * @return String sprite del Pokemon
         */
        public String getFrontDefault() {
            return frontDefault;
        }
    }


    /**
     * Clase que implementa el objeto TypeWrapper con el tipo del Pokemon
     */
    static class TypeWrapper {

        private Type type;


        /**
         * Constructor
         * @param type Tipo del Pokemon
         */
        public TypeWrapper(Type type) {
            this.type = type;
        }


        /**
         * Getter tipo del Pokemon
         * @return Type Tipo del Pokemon
         */
        public Type getType() {
            return type;
        }
    }


    /**
     * Clase que implementa el objeto Type con el tipo del Pokemon
     */
    static class Type {

        private String name;


        /**
         * Constructor
         * @param name Nombre del Pokemon
         */
        public Type(String name) {
            this.name = name;
        }


        /**
         * Getter nombre del Pokemon
         * @return String nombre del Pokemon
         */
        public String getName() {
            return name;
        }
    }
}
