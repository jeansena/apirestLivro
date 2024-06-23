package br.com.restLivro.Service;

import br.com.restLivro.config.FileStorageConfig;
import br.com.restLivro.exceptions.FileStorageException;
import br.com.restLivro.exceptions.MyFileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//FileStorageService ---Serviço de armazenamento de arquivos
@Service
public class FileStorageService {


    private final Path fileStorageLocation;

     ///caminho do arquivo
    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        //caminho feliz -- try e tentar
        try {
            Files.createDirectories(this.fileStorageLocation);
        }
        //caminho infeliz catch e pega
        catch (Exception e) {
            throw new FileStorageException(
                    "Não foi possível criar o diretório onde os arquivos enviados serão armazenados", e);
        }
    }

    //metodo responsavel para grava o arquivo
    // storeFile  -- armazenar arquivo
    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Filename..txt por exemplo vai da error no if
            //nome do arquivo pode esta escrito errado no caso trata no if
            //pode criar outars regras
            if (filename.contains("..")) {
                throw new FileStorageException(
                        "Desculpe! O nome do arquivo contém uma sequência de caminho inválida " + filename);
            }
            //grava o aquivo
            //se ja existi o arquivo vai substituir
            //obs -- se quiser salvar na nuvem ou no banco de dado -- muda esses duas linhas de baixo
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException(
                    "Não foi possível armazenar o arquivo" + filename + ". Por favor, tente novamente!", e);
        }
    }
    //Responsavel por download do arquivo
    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) return resource;
            else throw new MyFileNotFoundException("Arquivo não encontrado");
        } catch (Exception e) {
            throw new MyFileNotFoundException("Arquivo não encontrado" + filename, e);
        }
    }

}