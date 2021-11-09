package content.businnes.generator.factory;

import content.domain.metadata.InputMetaData;
import content.io.validator.InputDataUtil;


public enum MetaDataFactory {
    MetaDataFactory() {
    };

    public static InputMetaData createInputMetaDataVo(String[] inputValues) {
        InputDataUtil.isValidNumberOfParameters(inputValues);
        return new InputMetaData(InputDataUtil.getDestinationDirectory(inputValues), InputDataUtil.getProtoLocation(inputValues));
    }
}

