package dam.aguadulce.aal.practicapokemon;

public class PokemonDetails {
    private Sprites sprites;

    public Sprites getSprites() {
        return sprites;
    }

    public static class Sprites {
        private Versions versions;

        public Versions getVersions() {
            return versions;
        }

        public static class Versions {
            private GenerationIV generation_iv;

            public GenerationIV getGeneration_iv() {
                return generation_iv;
            }

            public static class GenerationIV {
                private Platinum platinum;

                public Platinum getPlatinum() {
                    return platinum;
                }

                public static class Platinum {
                    private String front_default;

                    public String getFront_default() {
                        return front_default;
                    }
                }
            }
        }
    }
}

