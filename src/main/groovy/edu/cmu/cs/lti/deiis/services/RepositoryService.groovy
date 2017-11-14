package edu.cmu.cs.lti.deiis.services

import edu.cmu.cs.lti.deiis.json.Data
import groovy.util.logging.Slf4j
import org.lappsgrid.serialization.Serializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

/**
 *
 */
@Slf4j('logger')
@Service
class RepositoryService {

    @Value('${repository}')
    String path

    File repository

    @PostConstruct
    void setup() {
        if (path == null) {
            logger.error("The repository path was not injected. Please check the application.properties")
            path = '/usr/local/share/deiis/repository'
        }
        repository = new File(path)
        logger.info("Using {} as the repository directory.", repository.path)
        if (!repository.exists()) {
            if (repository.mkdirs()) {
                logger.info("Created repository directory {}", repository.path)
            }
            else {
                logger.error("Unable to create the repository directory")
            }
        }
    }

    boolean save(String name, String content) {
        if (repository == null) {
            logger.warn("The repository is null")
            return false
        }
        File file = new File(repository, name)
        if (file.exists()) {
            logger.warn("Attempt to upload existing dataset {}", name)
            return false
        }
        file.text = content
        logger.info("Wrote {} to the repository", name)
        return true
    }

    String delete(String name) {
        File file = new File(repository, name)
        if (!file.exists()) {
            return "No such file."
        }
        if (!file.delete()) {
            file.deleteOnExit()
            logger.warn("Unable to delete {} from the repository", name)
            return "Unable to delete the file."
        }
        logger.info("{} was removed from the repository", name)
        return "The file $name was removed from the repository."
    }

    String[] list() {
        if (repository == null) {
            logger.warn("The repository is null")
            return null
        }
        return repository.list()
    }

    Data load(String name) {
        if (repository == null) {
            logger.warn("The repository is null")
            return null
        }
        File file = new File(repository, name)
        if (!file.exists()) {
            logger.warn("No suche file {}", name)
            return null
        }
        logger.debug("Loaded {}", name)
        return Serializer.parse(file.text, Data)
    }
}
