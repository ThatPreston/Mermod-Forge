package thatpreston.mermod;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();
        final Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }
    public static class Server {
        public ForgeConfigSpec.BooleanValue swimSpeed;
        public ForgeConfigSpec.DoubleValue swimSpeedMultiplier;
        public ForgeConfigSpec.BooleanValue waterBreathing;
        public ForgeConfigSpec.BooleanValue nightVision;
        Server(ForgeConfigSpec.Builder builder) {
            builder.push("Server");
            swimSpeed = builder.comment("Swim speed").define("swimSpeed", true);
            swimSpeedMultiplier = builder.comment("Swim speed multiplier").defineInRange("swimSpeedMultiplier", 2.0D, 1.0D, 10.0D);
            waterBreathing = builder.comment("Water breathing").define("waterBreathing", true);
            nightVision = builder.comment("Night vision").define("nightVision", true);
            builder.pop();
        }
    }
    public static class Client {
        public ForgeConfigSpec.BooleanValue nightVisionFlashingFix;
        Client(ForgeConfigSpec.Builder builder) {
            builder.push("Client");
            nightVisionFlashingFix = builder.comment("Night vision flashing fix").define("nightVisionFlashingFix", true);
            builder.pop();
        }
    }
}