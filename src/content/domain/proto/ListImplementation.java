package content.domain.proto;

public enum ListImplementation {
    VECTOR("Vector");

    private final String listImpl;

    ListImplementation(String listImpl) {
        this.listImpl = listImpl;
    }

    public String getImplementation() {
        return this.listImpl;
    }
}
