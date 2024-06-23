package br.com.restLivro.controllers;

import br.com.restLivro.Service.FileStorageService;
import br.com.restLivro.dataDTO.UploadFileResponseVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "Ponto final do arquivo")//swagger
@RestController
@RequestMapping("/api/file/v1") // caminho na url
public class FileController {
    //info no log
    private Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService service;

    //salvar arquivo
    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {

        //info no log
        logger.info("Armazenando arquivo no disco");

        var filename = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(filename)
                .toUriString();

        return
                new UploadFileResponseVO(
                        filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    //UPLOAD DO ARQUIVO
    //salva varios arquivos pelo array
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseVO> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files) {
        //info no log
        logger.info("Armazenando arquivo no disco");

        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    // DOWNLOAD DE ARQUIVO
    //{filename:.+} -- para intepletar a extensao do arquivo
    //MY_file.txt
    @GetMapping("/downloadFile/{filename:.+}")//CAMINHO NA URL
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename, HttpServletRequest request) {
        //info no log
        logger.info("Lendo um arquivo no disco");

        Resource resource = service.loadFileAsResource(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        //caminho de error
        catch (Exception e) {

            logger.info("Não foi possível determinar o tipo de arquivo!");
        }
        //verificação
        if (contentType.isBlank()) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
