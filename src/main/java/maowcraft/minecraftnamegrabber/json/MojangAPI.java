package maowcraft.minecraftnamegrabber.json;

/**
 * POJOs for Yggdrasil/Mojang's APIs.
 *
 * Used to store data for the conversion of Username to UUID, and UUID to Name History.
 */
public class MojangAPI {
    /**
     * Stores Universally Unique Identifiers (UUIDs).
     */
    public static class UUID {
        private String name;
        private String id;
        private boolean legacy;
        private boolean demo;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public boolean isLegacy() {
            return legacy;
        }

        public boolean isDemo() {
            return demo;
        }

        @Override
        public String toString() {
            return "UUID{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", legacy=" + legacy +
                    ", demo=" + demo +
                    '}';
        }
    }

    /**
     * Stores a single username, also has the timestamp of when the username was set.
     */
    public static class Username {
        private String name;
        private long changedToAt;

        public String getName() {
            return name;
        }

        public long getChangedToAt() {
            return changedToAt;
        }

        @Override
        public String toString() {
            return "Username{" +
                    "name='" + name + '\'' +
                    ", changedToAt=" + changedToAt +
                    '}';
        }
    }
}
