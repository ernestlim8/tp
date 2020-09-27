package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private static final String EMPTY_DIRECTORY_MESSAGE = "Directory provided is empty!";

    private Path filePath;
    private Path menuFolderPath;

    /**
     * Constructor for JsonAddressBookStorage class
     */
    public JsonAddressBookStorage(Path filePath, Path menuFolderPath) {
        this.filePath = filePath;
        this.menuFolderPath = menuFolderPath;
    }


    public Path getAddressBookFilePath() {
        return filePath;
    }

    public Path getMenuFolderPath() {
        return menuFolderPath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public List<Optional<ReadOnlyAddressBook>> readMenus() throws DataConversionException, IOException {
        return readMenus(menuFolderPath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public List<Optional<ReadOnlyAddressBook>> readMenus(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        List<Optional<ReadOnlyAddressBook>> menus = new ArrayList<>();
        File[] listOfFiles = new File(String.valueOf(filePath)).listFiles();
        if (listOfFiles == null) {
            throw new DataConversionException(new IllegalValueException(EMPTY_DIRECTORY_MESSAGE));
        }

        Arrays.sort(listOfFiles);

        for (File file : listOfFiles) {
            if (file.isFile()) {
                menus.add(readAddressBook(Paths.get(file.getPath())));
            }
        }
        return menus;
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

}
