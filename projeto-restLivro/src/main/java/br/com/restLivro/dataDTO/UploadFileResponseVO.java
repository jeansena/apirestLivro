package br.com.restLivro.dataDTO;

import java.io.Serializable;
//#upload e dawnload
public class UploadFileResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName; //nome do arquivo
    private String fileDownloadUri; //o endereco aonde vai abaixa o arquivo
    private String fileType; //o tipo de arquivo
    private long size; // o tamanho do arquivo

    //construtor vacio
    public UploadFileResponseVO() {
    }

    public UploadFileResponseVO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

}