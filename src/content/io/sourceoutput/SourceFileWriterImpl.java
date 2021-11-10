package content.io.sourceoutput;


import content.domain.java.JavaFileOutput;
import content.domain.metadata.InputMetaData;
import content.io.exception.ProtoFileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public final class SourceFileWriterImpl implements SourceFileWriter {
    public SourceFileWriterImpl() {
    }

    public void writeJavaSourceFile(InputMetaData metaData, List<JavaFileOutput> javaOutputList) {
        Iterator i$ = javaOutputList.iterator();

        while (i$.hasNext()) {
            JavaFileOutput javaOutput = (JavaFileOutput) i$.next();
            BufferedWriter output = null;

            try {
                output = new BufferedWriter(new FileWriter(SourceWriterUtil.createDestinationFile(metaData, javaOutput), false));
                output.write(javaOutput.getCompleteSource());
            } catch (IOException var14) {
                throw new ProtoFileException("Error when trying to write file, filename: " + javaOutput.getClassName() + ".java directory: " + metaData.getDestinationDirectory(), var14);
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException var13) {
                        System.out.println("Unable to close the BufferedWriter, " + var13.getMessage());
                    }
                }

            }
        }

    }
}