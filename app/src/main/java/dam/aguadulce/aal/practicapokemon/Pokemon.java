package dam.aguadulce.aal.practicapokemon;

/**
 * Clase que implementa el concepto abstracto de un Pokemon con su nombre y la url de la que obtener su información
 */
public class Pokemon {
    private String name;
    private String url;


    public String getName() {
        return name;
    }


    public String getUrl() {
        return url;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
