package content.domain.proto;

public enum ListImplementation {
    VECTOR("java.util.Vector");

    private final String listImpl;

    ListImplementation(String listImpl) {
        this.listImpl = listImpl;
    }

    public String getImplementation() {
        return this.listImpl;
    }
}
