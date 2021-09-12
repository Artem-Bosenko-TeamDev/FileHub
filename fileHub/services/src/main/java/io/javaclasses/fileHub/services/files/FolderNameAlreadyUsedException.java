package io.javaclasses.fileHub.services.files;

import io.javaclasses.fileHub.services.InvalidCommandHandlingException;

public class FolderNameAlreadyUsedException extends InvalidCommandHandlingException {

    private static final long serialVersionUID = 8732485488886894185L;

    public FolderNameAlreadyUsedException(String name) {
        super("Folder name: " + name + " already used.");
    }
}