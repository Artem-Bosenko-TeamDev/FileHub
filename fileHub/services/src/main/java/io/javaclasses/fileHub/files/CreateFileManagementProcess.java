package io.javaclasses.fileHub.files;

import com.google.common.base.Preconditions;
import io.javaclasses.fileHub.DuplicatedIDException;
import io.javaclasses.fileHub.InvalidHandleCommandException;
import io.javaclasses.fileHub.SecuredProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * */
public class CreateFileManagementProcess implements SecuredProcess<CreateFileCommand, CreateFileDTO> {

    private final FileStorageInMemory fileStorage;
    private final Logger logger = LoggerFactory.getLogger(CreateFileManagementProcess.class);

    public CreateFileManagementProcess(FileStorageInMemory fileStorage){
        this.fileStorage = Preconditions.checkNotNull(fileStorage);
    }

    @Override
    public CreateFileDTO handle(CreateFileCommand inputCommand) throws InvalidHandleCommandException {
        if(logger.isInfoEnabled()){
            logger.info("Start create file " + inputCommand.name());
        }

        File file = new File(inputCommand.id());
        file.setUserID(inputCommand.owner());
        file.setMimeType(inputCommand.mimeType());
        file.setName(inputCommand.name());
        file.setSize(0);
        try {
            fileStorage.create(file);

            if(logger.isInfoEnabled()){
                logger.info("Created file was successful. if: " + file.id());
            }

            return new CreateFileDTO(file.id(),file.name(),file.mimeType(), file.owner());
        } catch (DuplicatedIDException e) {
            if(logger.isErrorEnabled()){
                logger.error(e.getMessage());
            }
            throw new InvalidHandleCommandException(e.getMessage());
        }
    }
}
