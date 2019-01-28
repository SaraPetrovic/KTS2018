package ftn.kts.transport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ftn.kts.transport.exception.DAOException;
import ftn.kts.transport.exception.StorageFileNotFoundException;
import ftn.kts.transport.model.User;
import ftn.kts.transport.services.JwtService;
import ftn.kts.transport.services.StorageService;
import ftn.kts.transport.services.UserService;

@RestController
@RequestMapping(value = "/files")
public class FileUploadController {

    private final StorageService storageService;
    
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping("/upload")
    @CrossOrigin( origins = "http://localhost:4200")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, @RequestHeader("Authorization") final String token) {
    	

        storageService.store(file);

    	User credentials = jwtService.validate(token.substring(7));
        User found = userService.findByUsername(credentials.getUsername());
        found.setDocument(file.getOriginalFilename());
        userService.save(found);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
