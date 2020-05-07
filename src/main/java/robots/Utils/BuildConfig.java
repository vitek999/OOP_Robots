package robots.Utils;

public class BuildConfig {
    public enum BuildType {
        RELEASE,
        TEST
    }

    public static final BuildType buildType = BuildType.TEST;
}
