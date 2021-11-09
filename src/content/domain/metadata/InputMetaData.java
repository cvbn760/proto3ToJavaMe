package content.domain.metadata;

public class InputMetaData {
    private final String destinationDirectory;
    private final String protoLocation;

    public InputMetaData(String destinationDirectory, String protoLocation) {
        this.destinationDirectory = destinationDirectory;
        this.protoLocation = protoLocation;
    }

    public String getDestinationDirectory() {
        return this.destinationDirectory;
    }

    public String getProtoLocation() {
        return this.protoLocation;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InputMetaData");
        sb.append("{destinationDirectory='").append(this.destinationDirectory).append('\'');
        sb.append(", protoLocation='").append(this.protoLocation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
